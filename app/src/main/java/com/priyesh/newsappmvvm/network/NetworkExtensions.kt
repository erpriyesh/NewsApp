package com.priyesh.newsappmvvm.network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.statement.HttpResponsePipeline

fun HttpClient.addRequestInterceptor() {
    requestPipeline.intercept(HttpRequestPipeline.Transform) {
        proceed()
    }
}

fun HttpClient.addResponseInterceptor() {
    responsePipeline.intercept(HttpResponsePipeline.Receive) {
        proceed()
    }
}