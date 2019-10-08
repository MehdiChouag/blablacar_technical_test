package com.blablacar.technicaltest.common.network.token.datasource

import android.content.Context
import android.content.SharedPreferences
import com.blablacar.technicaltest.common.network.token.model.Token

/**
 * Retrieve a token from [SharedPreferences]
 */
class TokenLocalDataSource(appContext: Context) {

    companion object {
        private const val TOKEN_FILENAME = "token_pref"

        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val ISSUED_AT_KEY = "issued_at"
        private const val EXPIRES_IN_KEY = "expires_in"
        private const val CLIENT_ID_KEY = "client_id"

    }

    private val sharedPreferences: SharedPreferences = appContext.getSharedPreferences(
        TOKEN_FILENAME, Context.MODE_PRIVATE
    )

    fun getToken(): Token {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN_KEY, "")!!
        val issuedAt = sharedPreferences.getLong(ISSUED_AT_KEY, 0)
        val expiresIn = sharedPreferences.getLong(EXPIRES_IN_KEY, 0)
        val clientId = sharedPreferences.getString(CLIENT_ID_KEY, "")!!
        return Token(
            accessToken,
            issuedAt,
            expiresIn,
            clientId
        )
    }

    fun removeToken() {
        with(sharedPreferences.edit()) {
            remove(ACCESS_TOKEN_KEY)
            remove(ISSUED_AT_KEY)
            remove(EXPIRES_IN_KEY)
            remove(CLIENT_ID_KEY)
            apply()
        }
    }

    fun saveToken(token: Token) {
        with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN_KEY, token.accessToken)
            putLong(ISSUED_AT_KEY, token.issuedAt)
            putLong(EXPIRES_IN_KEY, token.expiresIn)
            putString(CLIENT_ID_KEY, token.clientId)
            apply()
        }
    }
}