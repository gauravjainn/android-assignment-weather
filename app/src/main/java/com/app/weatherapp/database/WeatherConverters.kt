package com.app.weatherapp.database

import androidx.room.TypeConverter
import com.app.weatherapp.networking.api.NullToEmptyStringAdapter
import com.app.weatherapp.responseobject.WeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class WeatherConverters {

    private val moshi = Moshi.Builder()
        .add(NullToEmptyStringAdapter())
        .build()


    private val weatherType = Types.newParameterizedType(List::class.java, WeatherData.Weather::class.java)
    private val weatherAdapter = moshi.adapter<List<WeatherData.Weather>>(weatherType)

    @TypeConverter
    fun stringToWeatherList(string: String): List<WeatherData.Weather> {
        return weatherAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun weatherListToString(members: List<WeatherData.Weather>): String {
        return weatherAdapter.toJson(members)
    }



}