package com.blablacar.technicaltest.common.network.token.repository

import com.blablacar.technicaltest.common.network.token.datasource.TokenLocalDataSource
import com.blablacar.technicaltest.common.network.token.datasource.TokenRemoteDataSource
import com.blablacar.technicaltest.common.network.token.model.Token
import okhttp3.Interceptor

/**
 * Retrieve a token either from local or remote source.
 */
class TokenRepository(
    private val remoteDataSource: TokenRemoteDataSource,
    private val localDataSource: TokenLocalDataSource
) {

    fun getToken(chain: Interceptor.Chain): Token {
        return if (hasValidToken()) {
            localDataSource.getToken()
        } else {
            localDataSource.removeToken()
            val refreshToken = remoteDataSource.getToken(chain)
            localDataSource.saveToken(refreshToken)
            refreshToken
        }
    }

    private fun hasValidToken(): Boolean {
        val localToken = localDataSource.getToken()
        val currentTime = System.currentTimeMillis() / 1000
        return localToken.issuedAt + localToken.expiresIn >= currentTime
    }
}