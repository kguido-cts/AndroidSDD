package com.android.androidsdd.data.mapper.home

import com.android.androidsdd.data.dto.home.AwardItemDto
import com.android.androidsdd.data.dto.home.AwardsSectionDto
import com.android.androidsdd.data.dto.home.ClubSummaryDto
import com.android.androidsdd.data.dto.home.FindAClubSectionDto
import com.android.androidsdd.data.dto.home.HeroSectionDto
import com.android.androidsdd.data.dto.home.HomeContentDto
import com.android.androidsdd.data.dto.home.MembershipPlanSummaryDto
import com.android.androidsdd.data.dto.home.MembershipTypesSectionDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HomeContentMapperTest {

    private val fullDto = HomeContentDto(
        hero = HeroSectionDto(
            headline = "Train",
            subheadline = "Harder",
            body = "Join us",
        ),
        findAClub = FindAClubSectionDto(
            title = "Find a club",
            description = "Near you",
            ctaLabel = "Go",
            clubs = listOf(
                ClubSummaryDto(id = "c1", name = "Downtown", area = "Central", shortDescription = "Gym"),
            ),
        ),
        membershipTypes = MembershipTypesSectionDto(
            title = "Plans",
            description = "Choose",
            plans = listOf(
                MembershipPlanSummaryDto(id = "p1", name = "Basic", priceFrom = "$19", bullets = listOf("Access")),
            ),
        ),
        awards = AwardsSectionDto(
            title = "Awards",
            items = listOf(
                AwardItemDto(id = "a1", title = "Best Gym", description = "Top pick"),
            ),
        ),
    )

    @Test
    fun `maps full DTO to domain correctly`() {
        val domain = HomeContentMapper.toDomain(fullDto)
        assertEquals("Train", domain.hero.headline)
        assertEquals("Harder", domain.hero.subheadline)
        assertEquals("Join us", domain.hero.body)

        assertEquals("Find a club", domain.findAClub.title)
        assertEquals("Near you", domain.findAClub.description)
        assertEquals(1, domain.findAClub.clubs.size)
        assertEquals("Downtown", domain.findAClub.clubs[0].name)

        assertEquals("Plans", domain.membershipTypes.title)
        assertEquals(1, domain.membershipTypes.plans.size)
        assertEquals("Basic", domain.membershipTypes.plans[0].name)
        assertEquals("$19", domain.membershipTypes.plans[0].priceFrom)
        assertEquals(listOf("Access"), domain.membershipTypes.plans[0].bullets)

        assertEquals("Awards", domain.awards.title)
        assertEquals(1, domain.awards.items.size)
        assertEquals("Best Gym", domain.awards.items[0].title)
    }

    @Test
    fun `optional fields omitted in DTO map to null in domain`() {
        val dto = fullDto.copy(
            findAClub = fullDto.findAClub.copy(description = null, ctaLabel = null),
            membershipTypes = fullDto.membershipTypes.copy(
                description = null,
                plans = listOf(fullDto.membershipTypes.plans[0].copy(priceFrom = null)),
            ),
        )
        val domain = HomeContentMapper.toDomain(dto)
        assertNull(domain.findAClub.description)
        assertNull(domain.findAClub.ctaLabel)
        assertNull(domain.membershipTypes.description)
        assertNull(domain.membershipTypes.plans[0].priceFrom)
    }

    @Test
    fun `empty lists in DTO map to empty lists in domain`() {
        val dto = fullDto.copy(
            findAClub = fullDto.findAClub.copy(clubs = emptyList()),
            membershipTypes = fullDto.membershipTypes.copy(plans = emptyList()),
            awards = fullDto.awards.copy(items = emptyList()),
        )
        val domain = HomeContentMapper.toDomain(dto)
        assertTrue(domain.findAClub.clubs.isEmpty())
        assertTrue(domain.membershipTypes.plans.isEmpty())
        assertTrue(domain.awards.items.isEmpty())
    }
}

