package com.priyesh.newsappmvvm.network

import com.priyesh.newsappmvvm.utils.DispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHandler @Inject constructor(
    val httpClient: HttpClient,
    val dispatcher: DispatcherProvider
) {

    suspend inline fun <reified B, reified R> executeRequest(
        httpMethod: HttpMethod,
        urlPathSegments: List<Any>,
        requestBody: B? = null,
        queryParams: Map<String, Any>? = null
    ): NetworkResult<R> {
        return withContext(dispatcher.io) {
            try {
                val response = httpClient.prepareRequest {
                    method = httpMethod
                    url { appendPathSegments(urlPathSegments.map { it.toString() }) }
                    requestBody?.let { setBody(it) }
                    queryParams?.let {
                        it.forEach { (key, value) ->
                            parameter(key, value)
                        }
                    }
                }.execute().body<R>()
                NetworkResult.SUCCESS(response, "")
            } catch (e: Exception) {
                val networkResult = if (e is ResponseException) {
                    val errorBody = e.response.body<DefaultError>()
                    when (e.response.status) {
                        HttpStatusCode.Unauthorized -> NetworkException.UnauthorizedException(
                            errorBody.message,
                            e
                        )

                        HttpStatusCode.RequestTimeout -> NetworkException.SocketTimeoutException(
                            errorBody.message,
                            e
                        )

                        else -> NetworkException.NotFoundException(errorBody.message, e)
                    }
                } else {
                    NetworkException.UnknownException(e.message ?: "Unknown Error Exception", e)
                }
                NetworkResult.FAILURE(networkResult.message, networkResult)
            }
        }
    }

    suspend inline fun <reified R> get(
        urlPathSegments: List<Any>,
        queryParams: Map<String, Any>? = null,
    ): NetworkResult<R> = executeRequest<Any, R>(
        httpMethod = HttpMethod.Get,
        urlPathSegments = urlPathSegments,
        queryParams = queryParams,
    )

    suspend inline fun <reified B, reified R> post(
        urlPathSegments: List<Any>,
        body: B? = null,
    ): NetworkResult<R> = executeRequest(
        httpMethod = HttpMethod.Post,
        urlPathSegments = urlPathSegments,
        requestBody = body,
    )
}