package com.priyesh.newsappmvvm.di

import com.priyesh.newsappmvvm.network.KtorHttpClientBuilder
import com.priyesh.newsappmvvm.network.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return KtorHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(NetworkConstants.BASE_URL)
            .build()
    }
}