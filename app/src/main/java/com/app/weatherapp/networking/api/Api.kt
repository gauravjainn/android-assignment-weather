package com.app.weatherapp.networking.api

import android.content.Context
import com.app.weatherapp.BuildConfig

class Api {

    companion object {
        fun retrofitService(context: Context): ApiService {
            return RetroFitClient.getClient(BuildConfig.BASE_URL + BuildConfig.SUB_URL, context)!!
                .create(ApiService::class.java)
        }

        fun retrofitService(): ApiService {
            return RetroFitClient.getClient(BuildConfig.BASE_URL + BuildConfig.SUB_URL)!!
                .create(ApiService::class.java)
        }
    }


}