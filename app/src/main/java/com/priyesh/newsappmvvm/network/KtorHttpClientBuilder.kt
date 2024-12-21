package com.priyesh.newsappmvvm.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorHttpClientBuilder {

    private lateinit var protocol: URLProtocol
    private lateinit var host: String
    private var port: Int? = null

    fun protocol(protocol: URLProtocol) = apply { this.protocol = protocol }
    fun host(host: String) = apply { this.host = host }
    fun port(port: Int) = apply { this.port = port }
    fun build(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true
            engine {
                endpoint {
                    keepAliveTime = NetworkConstants.KEEP_ALIVE_TIME
                    connectTimeout = NetworkConstants.CONNECT_TIMEOUT
                    connectAttempts = NetworkConstants.CONNECT_ATTEMPTS
                }
            }

            defaultRequest {
                url {
                    protocol = this@KtorHttpClientBuilder.protocol
                    host = this@KtorHttpClientBuilder.host
                    this@KtorHttpClientBuilder.port?.let { port = it }
                }
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("API Log: $message")
                    }
                }
                level = LogLevel.ALL
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = NetworkConstants.REQUEST_TIMEOUT
                connectTimeoutMillis = NetworkConstants.CONNECTION_TIMEOUT
                socketTimeoutMillis = NetworkConstants.SOCKET_TIMEOUT
            }
        }
    }
}