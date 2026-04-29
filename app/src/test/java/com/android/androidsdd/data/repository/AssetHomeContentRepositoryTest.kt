package com.android.androidsdd.data.repository

import com.android.androidsdd.data.datasource.AssetReader
import com.android.androidsdd.domain.model.home.HomeContentError
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class AssetHomeContentRepositoryTest {

    private lateinit var assetReader: AssetReader
    private lateinit var repository: AssetHomeContentRepository
    private val testDispatcher = StandardTestDispatcher()
    private val json = Json { ignoreUnknownKeys = true }

    private val validJson = """
        {
            "hero": { "headline": "H", "subheadline": "S", "body": "B" },
            "findAClub": { "title": "Find", "clubs": [] },
            "membershipTypes": { "title": "Plans", "plans": [] },
            "awards": { "title": "Awards", "items": [] }
        }
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        assetReader = mockk()
        repository = AssetHomeContentRepository(assetReader, json, testDispatcher)
    }

    @Test
    fun `returns mapped content on success`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } returns validJson
        val content = repository.getHomeContent()
        assertEquals("H", content.hero.headline)
        assertEquals("S", content.hero.subheadline)
    }

    @Test
    fun `returns content with empty lists`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } returns validJson
        val content = repository.getHomeContent()
        assertEquals(0, content.findAClub.clubs.size)
        assertEquals(0, content.membershipTypes.plans.size)
        assertEquals(0, content.awards.items.size)
    }

    @Test
    fun `throws MissingData when asset not found`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } throws FileNotFoundException("not found")
        try {
            repository.getHomeContent()
            assert(false) { "Expected HomeContentError.MissingData" }
        } catch (e: HomeContentError.MissingData) {
            // expected
        }
    }

    @Test
    fun `throws InvalidData on malformed JSON`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } returns "{ invalid json }"
        try {
            repository.getHomeContent()
            assert(false) { "Expected HomeContentError.InvalidData" }
        } catch (e: HomeContentError.InvalidData) {
            // expected
        }
    }
}

