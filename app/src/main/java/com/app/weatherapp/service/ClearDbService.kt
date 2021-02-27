package com.app.weatherapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.app.weatherapp.database.WeatherRepository


class ClearDbService : Service() {

    override fun onCreate() {
        val weatherRepository = WeatherRepository(application)
        weatherRepository.deleteAllWeatherDataList()

        Log.d(ClearDbService::class.simpleName, "Weather Data Cleared")
    }

    override fun onBind(p0: Intent?): IBinder? {

        return null
    }
}