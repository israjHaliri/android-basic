package com.gen.duo.app.util

import android.content.Context
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


/**
 * Created by israjhaliri on 3/19/18.
 */
class RetrofitClient {

    companion object {
        val baseUrl = "http://192.168.1.141:5000/duo-gen/"

        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }

        fun getClientInterceptor(context: Context): Retrofit? {

            val pref = Preferences(context)

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + pref.getToken()).build()
                    return chain.proceed(request)
                }
            })

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build()
            }
            return retrofit
        }
    }
}