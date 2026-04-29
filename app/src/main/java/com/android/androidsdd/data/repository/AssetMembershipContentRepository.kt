package com.android.androidsdd.data.repository

import com.android.androidsdd.data.datasource.AssetReader
import com.android.androidsdd.data.dto.membership.MembershipContentDto
import com.android.androidsdd.data.mapper.membership.MembershipContentMapper
import com.android.androidsdd.di.IoDispatcher
import com.android.androidsdd.domain.model.membership.MembershipContent
import com.android.androidsdd.domain.model.membership.MembershipContentError
import com.android.androidsdd.domain.repository.MembershipContentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import javax.inject.Inject

private const val MEMBERSHIP_CONTENT_PATH = "mock/membership_content.json"

/**
 * [MembershipContentRepository] that reads bundled JSON from the Android assets folder.
 *
 * Asset reading is performed on the IO dispatcher.
 */
class AssetMembershipContentRepository @Inject constructor(
    private val assetReader: AssetReader,
    private val json: Json,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MembershipContentRepository {

    override suspend fun getMembershipContent(): MembershipContent = withContext(ioDispatcher) {
        val raw = try {
            assetReader.readText(MEMBERSHIP_CONTENT_PATH)
        } catch (e: FileNotFoundException) {
            throw MembershipContentError.MissingData
        } catch (e: Exception) {
            throw MembershipContentError.InvalidData(e)
        }

        val dto = try {
            json.decodeFromString<MembershipContentDto>(raw)
        } catch (e: SerializationException) {
            throw MembershipContentError.InvalidData(e)
        } catch (e: IllegalArgumentException) {
            throw MembershipContentError.InvalidData(e)
        }

        MembershipContentMapper.toDomain(dto)
    }
}
