package com.android.androidsdd.data.repository

import com.android.androidsdd.data.datasource.AssetReader
import com.android.androidsdd.domain.model.membership.MembershipContentError
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.FileNotFoundException

class AssetMembershipContentRepositoryTest {

    private lateinit var assetReader: AssetReader
    private lateinit var repository: AssetMembershipContentRepository
    private val testDispatcher = StandardTestDispatcher()
    private val json = Json { ignoreUnknownKeys = true }

    private val validJson = """
        {
          "header": { "title": "Membership" },
          "sections": []
        }
    """.trimIndent()

    @BeforeEach
    fun setUp() {
        assetReader = mockk()
        repository = AssetMembershipContentRepository(assetReader, json, testDispatcher)
    }

    @Test
    fun `returns mapped content on success`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } returns validJson
        val content = repository.getMembershipContent()
        assertEquals("Membership", content.header.title)
        assertEquals(0, content.sections.size)
    }

    @Test
    fun `throws MissingData when asset not found`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } throws FileNotFoundException("not found")
        try {
            repository.getMembershipContent()
            assert(false) { "Expected MembershipContentError.MissingData" }
        } catch (e: MembershipContentError.MissingData) {
            // expected
        }
    }

    @Test
    fun `throws InvalidData on malformed JSON`() = runTest(testDispatcher) {
        every { assetReader.readText(any()) } returns "{ invalid json }"
        try {
            repository.getMembershipContent()
            assert(false) { "Expected MembershipContentError.InvalidData" }
        } catch (e: MembershipContentError.InvalidData) {
            // expected
        }
    }
}

