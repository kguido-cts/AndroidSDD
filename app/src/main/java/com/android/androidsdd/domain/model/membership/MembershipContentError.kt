package com.android.androidsdd.domain.model.membership

/** Typed error model for Membership content loading failures. */
sealed class MembershipContentError : Exception() {

    /** The JSON asset file could not be found. */
    data object MissingData : MembershipContentError() {
        override val message: String = "Membership content asset is missing."
    }

    /** The JSON asset was found but could not be parsed. */
    data class InvalidData(override val cause: Throwable) : MembershipContentError() {
        override val message: String = "Membership content asset is malformed: ${cause.message}"
    }
}
