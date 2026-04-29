package com.android.androidsdd.data.repository

import com.android.androidsdd.data.datasource.AssetReader
import com.android.androidsdd.data.dto.home.HomeContentDto
import com.android.androidsdd.data.mapper.home.HomeContentMapper
import com.android.androidsdd.di.IoDispatcher
import com.android.androidsdd.domain.model.home.HomeContent
import com.android.androidsdd.domain.model.home.HomeContentError
import com.android.androidsdd.domain.repository.HomeContentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import javax.inject.Inject

private const val HOME_CONTENT_PATH = "mock/home_content.json"

/**
 * [HomeContentRepository] that reads bundled JSON from the Android assets folder.
 *
 * Asset reading is performed on the IO dispatcher.
 */
class AssetHomeContentRepository @Inject constructor(
    private val assetReader: AssetReader,
    private val json: Json,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : HomeContentRepository {

    override suspend fun getHomeContent(): HomeContent = withContext(ioDispatcher) {
        val raw = try {
            assetReader.readText(HOME_CONTENT_PATH)
        } catch (e: FileNotFoundException) {
            throw HomeContentError.MissingData
        } catch (e: Exception) {
            throw HomeContentError.InvalidData(e)
        }

        val dto = try {
            json.decodeFromString<HomeContentDto>(raw)
        } catch (e: SerializationException) {
            throw HomeContentError.InvalidData(e)
        } catch (e: IllegalArgumentException) {
            throw HomeContentError.InvalidData(e)
        }

        HomeContentMapper.toDomain(dto)
    }
}

