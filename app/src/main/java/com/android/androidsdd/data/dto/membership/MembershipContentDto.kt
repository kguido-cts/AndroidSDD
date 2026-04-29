package com.android.androidsdd.data.dto.membership

import kotlinx.serialization.Serializable

/** DTO for [com.android.androidsdd.domain.model.membership.MembershipContent]. */
@Serializable
data class MembershipContentDto(
    val header: MembershipHeaderDto,
    val sections: List<MembershipSectionDto> = emptyList(),
)

@Serializable
data class MembershipHeaderDto(
    val title: String,
    val body: String? = null,
)

@Serializable
data class MembershipSectionDto(
    val id: String,
    val title: String,
    val plans: List<MembershipPlanDto> = emptyList(),
)

@Serializable
data class MembershipPlanDto(
    val id: String,
    val name: String,
    val tagline: String? = null,
    val price: String? = null,
    val iconKey: String? = null,
    val benefits: List<MembershipBenefitDto> = emptyList(),
)

@Serializable
data class MembershipBenefitDto(
    val id: String,
    val text: String,
)
