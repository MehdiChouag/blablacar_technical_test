package com.blablacar.technicaltest.common.network.token.datasource

import android.util.Log
import com.blablacar.technicaltest.common.network.token.model.RequestToken
import com.blablacar.technicaltest.common.network.token.model.Token
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Retrieve a fresh token from the server.
 */
class TokenRemoteDataSource(
    private val authUrl: String,
    private val clientId: String,
    private val clientSecret: String,
    private val objectMapper: ObjectMapper
) {

    private val refreshTokenBody: RequestBody
        get() = objectMapper
            .writeValueAsString(RequestToken(clientId, clientSecret))
            .toRequestBody("application/json".toMediaType())

    fun getToken(chain: Interceptor.Chain): Token {
        return try {
            val refreshTokenRequest = chain.request().newBuilder()
                .post(refreshTokenBody)
                .url(authUrl)
                .build()

            val refreshResponse = chain.proceed(refreshTokenRequest)
            objectMapper.readValue(refreshResponse.body?.string(), Token::class.java)
        } catch (e: Exception) {
            Log.e(TokenRemoteDataSource::class.simpleName, "An exception was thrown", e)
            Token.buildEmptyToken()
        }
    }
}