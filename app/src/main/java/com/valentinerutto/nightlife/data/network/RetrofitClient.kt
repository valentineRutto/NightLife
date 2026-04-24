package com.valentinerutto.nightlife.data.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    const val BASE_URL = "https://fake.api/"

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {

        "application/json".toMediaType()
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun createOkClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(createLoggingInterceptor()).addInterceptor(FakeInterceptor())
            .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY

        }
    }


    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }




}
class FakeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val fakeJson = """
            [
              {
                "id": "1",
                "title": "Afrobeats Night",
                "description": "Best vibes in Nairobi",
                "imageUrl": "",
                "location": "Westlands",
                "dateTime": ${System.currentTimeMillis()},
                "price": 1000.0
              }
            ]
        """.trimIndent()

        return Response.Builder()
            .code(200)
            .message(fakeJson)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                fakeJson.toByteArray().toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}