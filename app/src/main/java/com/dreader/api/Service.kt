package com.dreader.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {

    companion object {
        const val END_POINT = "http://demotywatory.pl"
    }

    lateinit var endpoints: Endpoints
        private set

    fun setup() {
        val client = buildClient()
        val retrofit = buildRetrofit(client)
        endpoints = retrofit.create(Endpoints::class.java)
    }

    private fun buildRetrofit(client: OkHttpClient) =
            Retrofit.Builder()
                    .baseUrl(END_POINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    private fun buildClient(): OkHttpClient {
        val httpLoggingInterceptor = createLoggingInterceptor()
        return OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    private fun createLoggingInterceptor() =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
}