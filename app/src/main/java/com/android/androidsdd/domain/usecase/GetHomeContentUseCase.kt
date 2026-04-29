package com.android.androidsdd.domain.usecase

import com.android.androidsdd.domain.model.home.HomeContent
import com.android.androidsdd.domain.repository.HomeContentRepository
import javax.inject.Inject

/**
 * Use case for loading Home screen content.
 *
 * Delegates to [HomeContentRepository]; exceptions propagate to the caller.
 */
class GetHomeContentUseCase @Inject constructor(
    private val repository: HomeContentRepository,
) {
    /** Invokes the use case and returns [HomeContent]. */
    suspend operator fun invoke(): HomeContent = repository.getHomeContent()
}

