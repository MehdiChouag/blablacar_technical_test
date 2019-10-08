package com.blablacar.technicaltest.common.network.auth

import com.blablacar.technicaltest.common.network.token.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Interceptor handling authentication by delegating each request to [TokenRepository].
 */
class AuthenticatorInterceptor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenRepository.getToken(chain)
        val request = originalRequest.newBuilder().addHeaders(token.accessToken).build()
        val response = chain.proceed(request)

        return if (response.code == 401) {
            intercept(chain)
        } else {
            response
        }
    }

    private fun Request.Builder.addHeaders(token: String) =
        apply {
            header("Accept", "application/json")
            header("Accept-Encoding", "identity")
            header("Accept-Language", "fr")
            header("Application-Client", "Android")
            header("Application-Version", "5.20.0-debug-33fbb08d3")
            header("Authorization", "Bearer $token")
            header("Connection", "Keep-Alive")
            header("X-Client", "ANDROID|5.25.0")
            header("X-Currency", "EUR")
            header("X-Locale", "fr_FR")
        }
}