package com.blablacar.technicaltest.common.network.token

import com.blablacar.technicaltest.common.network.token.datasource.TokenLocalDataSource
import com.blablacar.technicaltest.common.network.token.datasource.TokenRemoteDataSource
import com.blablacar.technicaltest.common.network.token.model.Token
import com.blablacar.technicaltest.common.network.token.repository.TokenRepository
import okhttp3.Interceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.verification.Times
import org.mockito.junit.MockitoJUnit

class TokenRepositoryTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    lateinit var remoteDataSource: TokenRemoteDataSource

    @Mock
    lateinit var localDataSource: TokenLocalDataSource

    @Mock
    lateinit var chain: Interceptor.Chain

    lateinit var tokenRepository: TokenRepository

    private val validToken: Token
        get() = Token("token", System.currentTimeMillis() / 1000, 600, "id")

    private val invalidToken
        get() = Token.buildEmptyToken()

    @Before
    fun setup() {
        tokenRepository = TokenRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `retrieve a valid token from disk`() {
        Mockito.`when`(localDataSource.getToken()).thenReturn(validToken)

        val token = tokenRepository.getToken(chain)

        Mockito.verify(localDataSource, Times(2)).getToken()
        Assert.assertEquals(validToken, token)
    }

    @Test
    fun `retrieve a valid token from network and save it`() {
        Mockito.`when`(localDataSource.getToken()).thenReturn(invalidToken)
        Mockito.`when`(remoteDataSource.getToken(chain)).thenReturn(validToken)

        val token = tokenRepository.getToken(chain)

        Mockito.verify(localDataSource).getToken()
        Mockito.verify(localDataSource).removeToken()
        Mockito.verify(localDataSource).saveToken(validToken)
        Mockito.verify(remoteDataSource).getToken(chain)
        Assert.assertEquals(validToken, token)
    }
}