package com.android.androidsdd.data.mapper.membership

import com.android.androidsdd.data.dto.membership.MembershipBenefitDto
import com.android.androidsdd.data.dto.membership.MembershipContentDto
import com.android.androidsdd.data.dto.membership.MembershipHeaderDto
import com.android.androidsdd.data.dto.membership.MembershipPlanDto
import com.android.androidsdd.data.dto.membership.MembershipSectionDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MembershipContentMapperTest {

    private val fullDto = MembershipContentDto(
        header = MembershipHeaderDto(
            title = "Membership",
            body = "Compare plans",
        ),
        sections = listOf(
            MembershipSectionDto(
                id = "classic_section",
                title = "Classic",
                plans = listOf(
                    MembershipPlanDto(
                        id = "classic",
                        name = "Classic",
                        tagline = "Everyday",
                        iconKey = null,
                        benefits = listOf(
                            MembershipBenefitDto(id = "b1", text = "Unlimited access"),
                        ),
                    ),
                ),
            ),
            MembershipSectionDto(
                id = "black_card_section",
                title = "Black Card",
                plans = listOf(
                    MembershipPlanDto(
                        id = "black_card",
                        name = "Black Card",
                        tagline = "Committed",
                        iconKey = "ignored",
                        benefits = listOf(
                            MembershipBenefitDto(id = "b2", text = "All-club access"),
                        ),
                    ),
                ),
            ),
        ),
    )

    @Test
    fun `maps full DTO to domain correctly`() {
        val domain = MembershipContentMapper.toDomain(fullDto)
        assertEquals("Membership", domain.header.title)
        assertEquals("Compare plans", domain.header.body)

        assertEquals(2, domain.sections.size)
        assertEquals("classic_section", domain.sections[0].id)
        assertEquals("Classic", domain.sections[0].title)
        assertEquals("classic", domain.sections[0].plans[0].id)
        assertEquals("Classic", domain.sections[0].plans[0].name)
        assertEquals("Everyday", domain.sections[0].plans[0].tagline)
        assertNull(domain.sections[0].plans[0].iconKey)
        assertEquals("Unlimited access", domain.sections[0].plans[0].benefits[0].text)

        assertEquals("black_card_section", domain.sections[1].id)
        assertEquals("Black Card", domain.sections[1].title)
        assertEquals("black_card", domain.sections[1].plans[0].id)
        assertEquals("Black Card", domain.sections[1].plans[0].name)
        assertEquals("Committed", domain.sections[1].plans[0].tagline)
        assertEquals("ignored", domain.sections[1].plans[0].iconKey)
    }

    @Test
    fun `optional fields omitted in DTO map to null in domain`() {
        val dto = fullDto.copy(
            header = fullDto.header.copy(body = null),
            sections = listOf(
                fullDto.sections[0].copy(
                    plans = listOf(fullDto.sections[0].plans[0].copy(tagline = null, iconKey = null)),
                ),
            ),
        )

        val domain = MembershipContentMapper.toDomain(dto)
        assertNull(domain.header.body)
        assertNull(domain.sections[0].plans[0].tagline)
        assertNull(domain.sections[0].plans[0].iconKey)
    }

    @Test
    fun `empty lists in DTO map to empty lists in domain`() {
        val dto = fullDto.copy(
            sections = listOf(
                fullDto.sections[0].copy(plans = emptyList()),
                fullDto.sections[1].copy(plans = listOf(fullDto.sections[1].plans[0].copy(benefits = emptyList()))),
            ),
        )

        val domain = MembershipContentMapper.toDomain(dto)
        assertTrue(domain.sections[0].plans.isEmpty())
        assertTrue(domain.sections[1].plans[0].benefits.isEmpty())
    }
}
