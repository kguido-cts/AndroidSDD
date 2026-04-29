package com.android.androidsdd.domain.model.membership

/**
 * Domain model for the Membership tab content.
 *
 * This feature is content-driven: UI renders from [MembershipContent] loaded via a repository.
 */
data class MembershipContent(
    val header: MembershipHeader,
    val sections: List<MembershipSection>,
)

/** Header section shown at the top of the Membership screen. */
data class MembershipHeader(
    val title: String,
    val body: String?,
    val tagline: String? = null,
)

/** A logical section rendered with a section header (e.g., Classic, Black Card). */
data class MembershipSection(
    val id: String,
    val title: String,
    val plans: List<MembershipPlan>,
)

/** A membership plan displayed as a card. */
data class MembershipPlan(
    val id: String,
    val name: String,
    val tagline: String?,
    val benefits: List<MembershipBenefit>,
    /**
     * Optional key that can map to a plan icon in the future.
     *
     * When null, UI must show a neutral placeholder icon.
     */
    val iconKey: String?,
)

/** A single benefit row displayed inside a membership plan card. */
data class MembershipBenefit(
    val id: String,
    val text: String,
)
