package com.android.androidsdd.ui.screens.membership

import com.android.androidsdd.domain.model.membership.MembershipContent

/** UI state for the Membership screen. */
sealed interface MembershipUiState {

    /** Content is being loaded. */
    data object Loading : MembershipUiState

    /** Content loaded successfully. */
    data class Content(val membershipContent: MembershipContent) : MembershipUiState

    /** An error occurred loading content. */
    data class Error(val message: String) : MembershipUiState
}
