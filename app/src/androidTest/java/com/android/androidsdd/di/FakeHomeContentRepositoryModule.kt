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
import com.android.androidsdd.domain.model.membership.MembershipBenefit
import com.android.androidsdd.domain.model.membership.MembershipContent
import com.android.androidsdd.domain.model.membership.MembershipContentError
import com.android.androidsdd.domain.model.membership.MembershipHeader
import com.android.androidsdd.domain.model.membership.MembershipPlan
import com.android.androidsdd.domain.model.membership.MembershipSection
import com.android.androidsdd.domain.repository.MembershipContentRepository
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

/** Toggle to simulate membership repository failure/content in UI tests. */
object FakeMembershipContentRepositoryState {
    var shouldFail: Boolean = false
    var content: MembershipContent = defaultMembershipContent()
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
        ),
        findAClub = FindAClubSection(
            title = "Find a club",
            description = "Near you",
            ctaLabel = "Find a club",
            clubs = listOf(ClubSummary("c1", "Downtown", "Central", "Gym and classes", listOf("Gym", "Pool"))),
        ),
        membershipTypes = MembershipTypesSection(
            title = "Membership types",
            description = "Pick a plan",
            plans = listOf(
                MembershipPlanSummary("classic", "Classic", "\$29/mo", "Best for everyday gym-goers", listOf("Full Gym Access")),
                MembershipPlanSummary("black_card", "Black Card", "\$59/mo", "Best for committed athletes", listOf("All-Club Access")),
            ),
        ),
        awards = AwardsSection(
            title = "Awards",
            items = listOf(AwardItem("a1", "Best Gym Chain of the Year", "Recognised for outstanding facility quality.", "2025 National Fitness Association")),
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

    @Provides
    @Singleton
    fun provideMembershipContentRepository(): MembershipContentRepository =
        object : MembershipContentRepository {
            override suspend fun getMembershipContent(): MembershipContent {
                if (FakeMembershipContentRepositoryState.shouldFail) throw MembershipContentError.MissingData
                return FakeMembershipContentRepositoryState.content
            }
        }
}

private fun defaultMembershipContent(): MembershipContent = MembershipContent(
    header = MembershipHeader(
        title = "Membership Plans",
        body = "Flexible, affordable, and packed with perks. Pick the plan that moves you.",
    ),
    sections = listOf(
        MembershipSection(
            id = "classic_section",
            title = "Classic",
            plans = listOf(
                MembershipPlan(
                    id = "classic",
                    name = "Classic",
                    tagline = "Best for everyday gym-goers",
                    price = "$29/mo",
                    iconKey = null,
                    benefits = listOf(
                        MembershipBenefit("classic_b1", "Full gym access"),
                        MembershipBenefit("classic_b2", "Locker rooms & Showers"),
                        MembershipBenefit("classic_b3", "Fitness App"),
                        MembershipBenefit("classic_b4", "Free Fitness Assessment"),
                        MembershipBenefit("classic_b5", "No Long-Term Contract"),
                    ),
                ),
            ),
        ),
        MembershipSection(
            id = "black_card_section",
            title = "Black Card",
            plans = listOf(
                MembershipPlan(
                    id = "black_card",
                    name = "Black Card",
                    tagline = "Best for committed athletes & wellness enthusiasts",
                    price = "$59/mo",
                    iconKey = null,
                    benefits = listOf(
                        MembershipBenefit("black_b1", "All-Club Access"),
                        MembershipBenefit("black_b2", "24/7 Unlimited Access"),
                        MembershipBenefit("black_b3", "Spa & Massage Credits"),
                        MembershipBenefit("black_b4", "Guest Privileges"),
                        MembershipBenefit("black_b5", "Nutrition Coaching"),
                        MembershipBenefit("black_b6", "Priority Class Booking"),
                    ),
                ),
            ),
        ),
    ),
)


