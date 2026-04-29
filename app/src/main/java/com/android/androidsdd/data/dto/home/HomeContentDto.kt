package com.android.androidsdd.data.dto.home

import kotlinx.serialization.Serializable

/** DTO for [com.android.androidsdd.domain.model.home.HomeContent]. */
@Serializable
data class HomeContentDto(
    val hero: HeroSectionDto,
    val findAClub: FindAClubSectionDto,
    val membershipTypes: MembershipTypesSectionDto,
    val awards: AwardsSectionDto,
)

@Serializable
data class HeroSectionDto(
    val headline: String,
    val subheadline: String,
    val body: String,
)

@Serializable
data class FindAClubSectionDto(
    val title: String,
    val description: String? = null,
    val ctaLabel: String? = null,
    val clubs: List<ClubSummaryDto> = emptyList(),
)

@Serializable
data class ClubSummaryDto(
    val id: String,
    val name: String,
    val area: String,
    val shortDescription: String,
    val amenities: List<String> = emptyList(),
)

@Serializable
data class MembershipTypesSectionDto(
    val title: String,
    val description: String? = null,
    val plans: List<MembershipPlanSummaryDto> = emptyList(),
)

@Serializable
data class MembershipPlanSummaryDto(
    val id: String,
    val name: String,
    val priceFrom: String? = null,
    val bullets: List<String> = emptyList(),
)

@Serializable
data class AwardsSectionDto(
    val title: String,
    val items: List<AwardItemDto> = emptyList(),
)

@Serializable
data class AwardItemDto(
    val id: String,
    val title: String,
    val description: String,
)

