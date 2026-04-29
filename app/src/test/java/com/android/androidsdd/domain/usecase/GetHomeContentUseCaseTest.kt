package com.android.androidsdd.domain.usecase

import com.android.androidsdd.domain.model.home.AwardsSection
import com.android.androidsdd.domain.model.home.FindAClubSection
import com.android.androidsdd.domain.model.home.HeroSection
import com.android.androidsdd.domain.model.home.HomeContent
import com.android.androidsdd.domain.model.home.HomeContentError
import com.android.androidsdd.domain.model.home.MembershipTypesSection
import com.android.androidsdd.domain.repository.HomeContentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetHomeContentUseCaseTest {

    private lateinit var repository: HomeContentRepository
    private lateinit var useCase: GetHomeContentUseCase

    private val fakeContent = HomeContent(
        hero = HeroSection("H", "S", "B"),
        findAClub = FindAClubSection("Find", null, null, emptyList()),
        membershipTypes = MembershipTypesSection("Types", null, emptyList()),
        awards = AwardsSection("Awards", emptyList()),
    )

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetHomeContentUseCase(repository)
    }

    @Test
    fun `invoke returns content from repository`() = runTest {
        coEvery { repository.getHomeContent() } returns fakeContent
        val result = useCase()
        assertEquals(fakeContent, result)
        coVerify(exactly = 1) { repository.getHomeContent() }
    }

    @Test
    fun `invoke propagates MissingData error`() = runTest {
        coEvery { repository.getHomeContent() } throws HomeContentError.MissingData
        assertThrows(HomeContentError.MissingData::class.java) { runTest { useCase() } }
    }

    @Test
    fun `invoke propagates InvalidData error`() = runTest {
        val cause = RuntimeException("bad json")
        coEvery { repository.getHomeContent() } throws HomeContentError.InvalidData(cause)
        assertThrows(HomeContentError.InvalidData::class.java) { runTest { useCase() } }
    }
}

