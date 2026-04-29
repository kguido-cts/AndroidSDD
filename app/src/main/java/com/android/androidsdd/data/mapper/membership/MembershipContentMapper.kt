package com.android.androidsdd.data.mapper.membership

import com.android.androidsdd.data.dto.membership.MembershipBenefitDto
import com.android.androidsdd.data.dto.membership.MembershipContentDto
import com.android.androidsdd.data.dto.membership.MembershipHeaderDto
import com.android.androidsdd.data.dto.membership.MembershipPlanDto
import com.android.androidsdd.data.dto.membership.MembershipSectionDto
import com.android.androidsdd.domain.model.membership.MembershipBenefit
import com.android.androidsdd.domain.model.membership.MembershipContent
import com.android.androidsdd.domain.model.membership.MembershipHeader
import com.android.androidsdd.domain.model.membership.MembershipPlan
import com.android.androidsdd.domain.model.membership.MembershipSection

/** Maps [MembershipContentDto] (Data layer) to [MembershipContent] (Domain layer). */
object MembershipContentMapper {

    fun toDomain(dto: MembershipContentDto): MembershipContent = MembershipContent(
        header = dto.header.toDomain(),
        sections = dto.sections.map { it.toDomain() },
    )

    private fun MembershipHeaderDto.toDomain() = MembershipHeader(
        title = title,
        body = body,
    )

    private fun MembershipSectionDto.toDomain() = MembershipSection(
        id = id,
        title = title,
        plans = plans.map { it.toDomain() },
    )

    private fun MembershipPlanDto.toDomain() = MembershipPlan(
        id = id,
        name = name,
        tagline = tagline,
        benefits = benefits.map { it.toDomain() },
        iconKey = iconKey,
    )

    private fun MembershipBenefitDto.toDomain() = MembershipBenefit(
        id = id,
        text = text,
    )
}
