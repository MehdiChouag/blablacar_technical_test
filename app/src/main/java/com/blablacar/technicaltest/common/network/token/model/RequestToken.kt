package com.blablacar.technicaltest.common.network.token.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Model used to request a token from the server.
 */
data class RequestToken(
    @JsonProperty("client_id") val clientId: String,
    @JsonProperty("client_secret") val clientSecret: String,
    @JsonProperty("grant_type") val grantType: String = "client_credentials",
    @JsonProperty("scopes") val scopes: Array<String> = arrayOf(
        "SCOPE_TRIP_DRIVER",
        "SCOPE_USER_MESSAGING",
        "SCOPE_INTERNAL_CLIENT",
        "DEFAULT"
    )
) {
    /**
     * Override equals() & hashCode() due to the usage of an array (recommended by the IDE).
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequestToken

        if (clientId != other.clientId) return false
        if (clientSecret != other.clientSecret) return false
        if (grantType != other.grantType) return false
        if (!scopes.contentEquals(other.scopes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clientId.hashCode()
        result = 31 * result + clientSecret.hashCode()
        result = 31 * result + grantType.hashCode()
        result = 31 * result + scopes.contentHashCode()
        return result
    }
}