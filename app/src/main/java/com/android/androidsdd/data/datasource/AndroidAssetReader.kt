package com.android.androidsdd.data.datasource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Production implementation of [AssetReader] backed by Android's [android.content.res.AssetManager].
 */
class AndroidAssetReader @Inject constructor(
    @ApplicationContext private val context: Context,
) : AssetReader {

    override fun readText(path: String): String =
        context.assets.open(path).bufferedReader().use { it.readText() }
}

