package com.android.androidsdd.data.datasource

/**
 * Boundary interface that abstracts Android [android.content.res.AssetManager] access.
 *
 * Implementations may use the real AssetManager (production) or in-memory fakes (tests).
 */
interface AssetReader {
    /**
     * Reads the content of the asset at [path] as a UTF-8 string.
     *
     * @throws java.io.FileNotFoundException if [path] does not exist.
     * @throws java.io.IOException for other read errors.
     */
    fun readText(path: String): String
}

