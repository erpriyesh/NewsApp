package com.priyesh.newsappmvvm.di

import com.priyesh.newsappmvvm.network.KtorHttpClientBuilder
import com.priyesh.newsappmvvm.network.NetworkConstants
import com.priyesh.newsappmvvm.network.RequestHandler
import com.priyesh.newsappmvvm.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return KtorHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(NetworkConstants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestHandler(httpClient: HttpClient, dispatcher: DispatcherProvider): RequestHandler = RequestHandler(httpClient, dispatcher)
}