package com.android.androidsdd.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.androidsdd.domain.model.home.HomeContentError
import com.android.androidsdd.domain.usecase.GetHomeContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen.
 *
 * Loads content on init via [GetHomeContentUseCase] and exposes [HomeUiState]. Callers may
 * trigger [retry] to reload after an error.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeContent: GetHomeContentUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadContent()
    }

    /** Retry loading content after a failure. */
    fun retry() {
        _uiState.value = HomeUiState.Loading
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            _uiState.value = try {
                HomeUiState.Content(getHomeContent())
            } catch (e: HomeContentError.MissingData) {
                HomeUiState.Error(e.message ?: "Content not found.")
            } catch (e: HomeContentError.InvalidData) {
                HomeUiState.Error(e.message ?: "Content could not be loaded.")
            } catch (e: Exception) {
                HomeUiState.Error(e.message ?: "An unexpected error occurred.")
            }
        }
    }
}

