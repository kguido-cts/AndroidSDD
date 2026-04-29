package com.android.androidsdd.domain.model.home

/** Typed error model for Home content loading failures. */
sealed class HomeContentError : Exception() {

    /** The JSON asset file could not be found. */
    data object MissingData : HomeContentError() {
        override val message: String = "Home content asset is missing."
    }

    /** The JSON asset was found but could not be parsed. */
    data class InvalidData(override val cause: Throwable) : HomeContentError() {
        override val message: String = "Home content asset is malformed: ${cause.message}"
    }
}

