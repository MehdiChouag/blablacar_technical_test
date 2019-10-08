package com.blablacar.technicaltest.common.network.token.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Model used to retrieve the token from server.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Token(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("issued_at") val issuedAt: Long,
    @JsonProperty("expires_in") val expiresIn: Long,
    @JsonProperty("client_id") val clientId: String
) {
    companion object {
        fun buildEmptyToken() = Token("", 0L, 0L, "")
    }
}