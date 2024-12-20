package com.amk.photogenerator.model.net

import com.amk.photogenerator.model.data.PhotoGeneratorDallEResponse
import com.amk.photogenerator.util.API_KEY
import com.amk.photogenerator.util.BASE_URL
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiService {

    @POST("images/generations")
    suspend fun photoGeneratorDallE(
        @Body jsonObject: JsonObject
    ): PhotoGeneratorDallEResponse

}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}

fun createApiService(): ApiService {
    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}