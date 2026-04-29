package com.android.androidsdd.ui.screens.membership

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.androidsdd.domain.model.membership.MembershipContentError
import com.android.androidsdd.domain.usecase.GetMembershipContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Membership screen.
 *
 * Loads content on init via [GetMembershipContentUseCase] and exposes [MembershipUiState].
 * Callers may trigger [retry] to reload after an error.
 */
@HiltViewModel
class MembershipViewModel @Inject constructor(
    private val getMembershipContent: GetMembershipContentUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MembershipUiState>(MembershipUiState.Loading)
    val uiState: StateFlow<MembershipUiState> = _uiState.asStateFlow()

    init {
        loadContent()
    }

    /** Retry loading content after a failure. */
    fun retry() {
        _uiState.value = MembershipUiState.Loading
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            _uiState.value = try {
                MembershipUiState.Content(getMembershipContent())
            } catch (e: MembershipContentError.MissingData) {
                MembershipUiState.Error(e.message ?: "Content not found.")
            } catch (e: MembershipContentError.InvalidData) {
                MembershipUiState.Error(e.message ?: "Content could not be loaded.")
            } catch (e: Exception) {
                MembershipUiState.Error(e.message ?: "An unexpected error occurred.")
            }
        }
    }
}
