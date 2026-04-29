package com.android.androidsdd.ui.screens.home

import com.android.androidsdd.domain.model.home.HomeContent

/** UI state for the Home screen. */
sealed interface HomeUiState {

    /** Content is being loaded. */
    data object Loading : HomeUiState

    /** Content loaded successfully. */
    data class Content(val homeContent: HomeContent) : HomeUiState

    /** An error occurred loading content. */
    data class Error(val message: String) : HomeUiState
}

