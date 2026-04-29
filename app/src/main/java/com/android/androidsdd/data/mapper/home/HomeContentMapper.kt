package com.android.androidsdd.data.mapper.home

import com.android.androidsdd.data.dto.home.AwardItemDto
import com.android.androidsdd.data.dto.home.AwardsSectionDto
import com.android.androidsdd.data.dto.home.ClubSummaryDto
import com.android.androidsdd.data.dto.home.FindAClubSectionDto
import com.android.androidsdd.data.dto.home.HeroSectionDto
import com.android.androidsdd.data.dto.home.HomeContentDto
import com.android.androidsdd.data.dto.home.MembershipPlanSummaryDto
import com.android.androidsdd.data.dto.home.MembershipTypesSectionDto
import com.android.androidsdd.domain.model.home.AwardItem
import com.android.androidsdd.domain.model.home.AwardsSection
import com.android.androidsdd.domain.model.home.ClubSummary
import com.android.androidsdd.domain.model.home.FindAClubSection
import com.android.androidsdd.domain.model.home.HeroSection
import com.android.androidsdd.domain.model.home.HomeContent
import com.android.androidsdd.domain.model.home.MembershipPlanSummary
import com.android.androidsdd.domain.model.home.MembershipTypesSection

/** Maps [HomeContentDto] (Data layer) to [HomeContent] (Domain layer). */
object HomeContentMapper {

    fun toDomain(dto: HomeContentDto): HomeContent = HomeContent(
        hero = dto.hero.toDomain(),
        findAClub = dto.findAClub.toDomain(),
        membershipTypes = dto.membershipTypes.toDomain(),
        awards = dto.awards.toDomain(),
    )

    private fun HeroSectionDto.toDomain() = HeroSection(
        headline = headline,
        subheadline = subheadline,
        body = body,
    )

    private fun FindAClubSectionDto.toDomain() = FindAClubSection(
        title = title,
        description = description,
        ctaLabel = ctaLabel,
        clubs = clubs.map { it.toDomain() },
    )

    private fun ClubSummaryDto.toDomain() = ClubSummary(
        id = id,
        name = name,
        area = area,
        shortDescription = shortDescription,
        amenities = amenities,
    )

    private fun MembershipTypesSectionDto.toDomain() = MembershipTypesSection(
        title = title,
        description = description,
        plans = plans.map { it.toDomain() },
    )

    private fun MembershipPlanSummaryDto.toDomain() = MembershipPlanSummary(
        id = id,
        name = name,
        priceFrom = priceFrom,
        tagline = tagline,
        bullets = bullets,
    )

    private fun AwardsSectionDto.toDomain() = AwardsSection(
        title = title,
        items = items.map { it.toDomain() },
    )

    private fun AwardItemDto.toDomain() = AwardItem(
        id = id,
        title = title,
        description = description,
        stat = stat,
    )
}

