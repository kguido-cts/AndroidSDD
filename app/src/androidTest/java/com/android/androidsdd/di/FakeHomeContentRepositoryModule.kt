package com.android.androidsdd.di

import com.android.androidsdd.data.datasource.AndroidAssetReader
import com.android.androidsdd.data.datasource.AssetReader
import com.android.androidsdd.domain.model.home.AwardItem
import com.android.androidsdd.domain.model.home.AwardsSection
import com.android.androidsdd.domain.model.home.ClubSummary
import com.android.androidsdd.domain.model.home.FindAClubSection
import com.android.androidsdd.domain.model.home.HeroSection
import com.android.androidsdd.domain.model.home.HomeContent
import com.android.androidsdd.domain.model.home.HomeContentError
import com.android.androidsdd.domain.model.home.MembershipPlanSummary
import com.android.androidsdd.domain.model.home.MembershipTypesSection
import com.android.androidsdd.domain.repository.HomeContentRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import android.content.Context

/** Toggle to simulate repository failure in UI tests. */
object FakeHomeContentRepositoryState {
    var shouldFail: Boolean = false
}

/** Test-only Hilt module replacing [AppModule] with fakes for UI tests. */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class],
)
object FakeHomeContentRepositoryModule {

    val fakeContent = HomeContent(
        hero = HeroSection(
            headline = "Train Harder.",
            subheadline = "Live Better.",
            body = "Join PeakFit today.",
            primaryCtaLabel = "Find a Club",
            secondaryCtaLabel = "View Memberships",
        ),
        findAClub = FindAClubSection(
            title = "Find a club",
            description = "Near you",
            ctaLabel = "Find a club",
            clubs = listOf(ClubSummary("c1", "Downtown", "Central", "Gym and classes")),
        ),
        membershipTypes = MembershipTypesSection(
            title = "Membership types",
            description = "Pick a plan",
            plans = listOf(MembershipPlanSummary("p1", "Basic", "\$19.99/mo", listOf("Gym access"))),
        ),
        awards = AwardsSection(
            title = "Awards",
            items = listOf(AwardItem("a1", "Best Gym 2025", "Top community gym")),
        ),
    )

    @Provides
    @Singleton
    fun provideHomeContentRepository(): HomeContentRepository = object : HomeContentRepository {
        override suspend fun getHomeContent(): HomeContent {
            if (FakeHomeContentRepositoryState.shouldFail) throw HomeContentError.MissingData
            return fakeContent
        }
    }

    @Provides
    @Singleton
    fun provideAssetReader(@ApplicationContext context: Context): AssetReader =
        AndroidAssetReader(context)

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}


