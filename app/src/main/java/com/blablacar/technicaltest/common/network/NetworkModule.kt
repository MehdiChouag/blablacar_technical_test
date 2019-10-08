package com.blablacar.technicaltest.common.network

import android.content.Context
import com.blablacar.technicaltest.BuildConfig
import com.blablacar.technicaltest.common.network.auth.AuthenticatorInterceptor
import com.blablacar.technicaltest.common.network.token.datasource.TokenLocalDataSource
import com.blablacar.technicaltest.common.network.token.datasource.TokenRemoteDataSource
import com.blablacar.technicaltest.common.network.token.repository.TokenRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Providing network related dependencies
 */
@Module
object NetworkModule {

    private const val BASE_URL = "BASE_URL"
    private const val CLIENT_ID = "CLIENT_ID"
    private const val CLIENT_SECRET = "CLIENT_SECRET"

    @Provides
    @JvmStatic
    @Named(BASE_URL)
    fun provideBaseUrl(): String = "https://edge.blablacar.com"

    @Provides
    @JvmStatic
    @Named(CLIENT_ID)
    fun provideClientId(): String = BuildConfig.clientId

    @Provides
    @JvmStatic
    @Named(CLIENT_SECRET)
    fun provideClientSecret(): String = BuildConfig.clientSecret

    @Provides
    @JvmStatic
    fun provideTokenRemoteDataSource(
        @Named(BASE_URL) baseUrl: String,
        @Named(CLIENT_ID) clientId: String,
        @Named(CLIENT_SECRET) clientSecret: String,
        objectMapper: ObjectMapper
    ): TokenRemoteDataSource {
        return TokenRemoteDataSource("$baseUrl/token", clientId, clientSecret, objectMapper)
    }

    @Provides
    @JvmStatic
    fun provideTokenDataSource(appContext: Context): TokenLocalDataSource {
        return TokenLocalDataSource(appContext)
    }

    @Provides
    @JvmStatic
    fun provideTokenRepository(
        remoteDataSource: TokenRemoteDataSource,
        localDataSource: TokenLocalDataSource
    ): TokenRepository {
        return TokenRepository(remoteDataSource, localDataSource)
    }

    @Provides
    @JvmStatic
    fun provideAuthenticatorInterceptor(tokenRepository: TokenRepository): AuthenticatorInterceptor {
        return AuthenticatorInterceptor(tokenRepository)
    }

    @Provides
    @JvmStatic
    fun provideOkHttp(authenticatorInterceptor: AuthenticatorInterceptor): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(authenticatorInterceptor)
            .readTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return okHttpBuilder
    }

    @Provides
    @JvmStatic
    fun provideJacksonObjectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String,
        okHttpBuilder: OkHttpClient.Builder,
        objectMapper: ObjectMapper
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$baseUrl/api/v2/")
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }
}