package com.android.androidsdd.domain.usecase

import com.android.androidsdd.domain.model.membership.MembershipContent
import com.android.androidsdd.domain.repository.MembershipContentRepository
import javax.inject.Inject

/** Use case that loads the Membership screen content from a [MembershipContentRepository]. */
class GetMembershipContentUseCase @Inject constructor(
    private val repository: MembershipContentRepository,
) {
    suspend operator fun invoke(): MembershipContent = repository.getMembershipContent()
}
