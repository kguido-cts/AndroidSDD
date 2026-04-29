package com.android.androidsdd.domain.repository

import com.android.androidsdd.domain.model.membership.MembershipContent

/**
 * Repository contract for loading Membership content.
 *
 * Implementations should throw a typed `MembershipContentError` on failure.
 */
interface MembershipContentRepository {
    /** Loads the membership content model used by the Membership tab UI. */
    suspend fun getMembershipContent(): MembershipContent
}
