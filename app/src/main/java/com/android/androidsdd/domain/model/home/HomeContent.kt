package com.android.androidsdd.domain.model.home

/**
 * Top-level content for the Home screen.
 *
 * All sections are required; optional fields within sections are nullable.
 */
data class HomeContent(
    val hero: HeroSection,
    val findAClub: FindAClubSection,
    val membershipTypes: MembershipTypesSection,
    val awards: AwardsSection,
)

/** Hero/banner section at the top of the Home screen. */
data class HeroSection(
    val headline: String,
    val subheadline: String,
    val body: String,
    val primaryCtaLabel: String?,
    val secondaryCtaLabel: String?,
)

/** "Find a club" section. */
data class FindAClubSection(
    val title: String,
    val description: String?,
    val ctaLabel: String?,
    val clubs: List<ClubSummary>,
)

/** Summary of a gym club shown in the Find a Club section. */
data class ClubSummary(
    val id: String,
    val name: String,
    val area: String,
    val shortDescription: String,
)

/** Membership types section. */
data class MembershipTypesSection(
    val title: String,
    val description: String?,
    val plans: List<MembershipPlanSummary>,
)

/** Summary of a membership plan. */
data class MembershipPlanSummary(
    val id: String,
    val name: String,
    val priceFrom: String?,
    val bullets: List<String>,
)

/** Awards/highlights section. */
data class AwardsSection(
    val title: String,
    val items: List<AwardItem>,
)

/** An individual award or highlight. */
data class AwardItem(
    val id: String,
    val title: String,
    val description: String,
)

