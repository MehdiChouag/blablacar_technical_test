package com.blablacar.technicaltest.common.network.auth

import com.blablacar.technicaltest.common.network.token.model.Token
import com.blablacar.technicaltest.common.network.token.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AuthenticatorInterceptorTest {
    @Mock
    lateinit var chain: Interceptor.Chain

    @Mock
    lateinit var request: Request

    @Mock
    lateinit var response: Response

    @Mock
    lateinit var tokenRepository: TokenRepository

    @Mock
    lateinit var token: Token

    lateinit var authenticatorInterceptor: AuthenticatorInterceptor

    private val requestBuilder: Request.Builder = Request.Builder().url("http://url.com")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        authenticatorInterceptor = AuthenticatorInterceptor(tokenRepository)
        `when`(chain.request()).thenReturn(request)
        `when`(request.newBuilder()).thenReturn(requestBuilder)
    }

    @Test
    fun `successful authenticated request`() {
        `when`(response.code).thenReturn(200)
        `when`(tokenRepository.getToken(chain)).thenReturn(token)
        `when`(chain.proceed(any(Request::class.java))).thenReturn(response)

        val receiveResponse = authenticatorInterceptor.intercept(chain)

        verify(tokenRepository).getToken(chain)
        verify(token).accessToken
        verify(chain).proceed(any(Request::class.java))
        Assert.assertEquals(response, receiveResponse)
    }

    @Test
    fun `retry getting a token after 401 response code`() {
        `when`(response.code).thenReturn(401, 200)
        `when`(tokenRepository.getToken(chain)).thenReturn(token)
        `when`(chain.proceed(any(Request::class.java))).thenReturn(response)

        val receiveResponse = authenticatorInterceptor.intercept(chain)

        verify(tokenRepository, times(2)).getToken(chain)
        verify(token, times(2)).accessToken
        verify(chain, times(2)).proceed(any(Request::class.java))
        Assert.assertEquals(response, receiveResponse)
    }

    //Workaround method for mockito
    private fun <T> any(type: Class<T>): T {
        Mockito.any(type)
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}