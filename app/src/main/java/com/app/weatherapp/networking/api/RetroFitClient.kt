package com.app.weatherapp.networking.api

import android.content.Context
import com.app.weatherapp.BuildConfig
import com.app.weatherapp.networking.calladpater.FlowCallAdapterFactory
import com.app.weatherapp.util.Constants
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetroFitClient {

    companion object {
        private var retrofit: Retrofit? = null
        private const val REQUEST_TIMEOUT = 60L
        private var okHttpClient: OkHttpClient? = null

        private val moshi = Moshi.Builder()
            .add(NullToEmptyStringAdapter())
            //.add(KotlinJsonAdapterFactory())
            .build()


        fun getClient(baseUrl: String, context: Context): Retrofit? {
            if (okHttpClient == null)
                initOkHttp(context)

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(FlowCallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
            }
            return retrofit
        }

        fun getClient(baseUrl: String): Retrofit? {
            if (okHttpClient == null)
                initOkHttp()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(FlowCallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
            }
            return retrofit
        }

        private fun initOkHttp(context: Context) {
            val eventListenerC: EventListener = object : EventListener() {
                override fun callStart(call: Call) {
                    super.callStart(call)
                }

                override fun callEnd(call: Call) {
                    super.callEnd(call)
                }

                override fun callFailed(call: Call, ioe: IOException) {
                    super.callFailed(call, ioe)
                }
            }

            val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

            httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder =
                    original.newBuilder() //.addHeader("Authorization", "Bearer " + Prefs.GetString(Prefs.Preferences.kSessionToken, ""))
                        .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(interceptor)
            }

            httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                if (response.code == Constants.SESSION_EXPIRE) {
                    okHttpClient!!.dispatcher.cancelAll()
                    //Utils.checkSessionExpired(context, response.code)
                }
                response
            })
            okHttpClient = httpClient.build()
        }

        private fun initOkHttp() {
            val eventListenerC: EventListener = object : EventListener() {
                override fun callStart(call: Call) {
                    super.callStart(call)
                }

                override fun callEnd(call: Call) {
                    super.callEnd(call)
                }

                override fun callFailed(call: Call, ioe: IOException) {
                    super.callFailed(call, ioe)
                }
            }

            val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

            httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder =
                    original.newBuilder() //.addHeader("Authorization", "Bearer " + Prefs.GetString(Prefs.Preferences.kSessionToken, ""))
                        .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(interceptor)
            }

            httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                if (response.code == Constants.SESSION_EXPIRE) {
                    okHttpClient!!.dispatcher.cancelAll()
                    //Utils.checkSessionExpired(context, response.code)
                }
                response
            })
            okHttpClient = httpClient.build()
        }
    }


}