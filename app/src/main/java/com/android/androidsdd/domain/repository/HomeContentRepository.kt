package com.android.androidsdd.domain.repository

import com.android.androidsdd.domain.model.home.HomeContent

/** Contract for loading Home screen content. */
interface HomeContentRepository {
    /** Returns [HomeContent] or throws a [com.android.androidsdd.domain.model.home.HomeContentError]. */
    suspend fun getHomeContent(): HomeContent
}

