package com.priyesh.newsappmvvm.di

import com.priyesh.newsappmvvm.network.KtorHttpClientBuilder
import com.priyesh.newsappmvvm.network.NetworkConstants
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return KtorHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(NetworkConstants.BASE_URL)
            .build()
    }
}