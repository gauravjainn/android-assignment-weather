package com.app.weatherapp.responseobject
import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.weatherapp.util.DbConstants

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@SuppressLint("ParcelCreator")
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
@Entity(tableName = DbConstants.TABLE_WEATHER_DATA)
data class WeatherData(
    @Json(name = "coord")
    @Embedded(prefix = "coord_")
    var coord: Coord?,
    @Json(name = "weather")
    var weather: List<Weather?>?,
    @Json(name = "base")
    var base: String?,
    @Json(name = "main")
    @Embedded(prefix = "main_")
    var main: Main?,
    @Json(name = "visibility")
    var visibility: Int?,
    @Json(name = "wind")
    @Embedded(prefix = "wind_")
    var wind: Wind?,
    @Json(name = "clouds")
    @Embedded(prefix = "clouds_")
    var clouds: Clouds?,
    @Json(name = "dt")
    var dt: Int?,
    @Json(name = "sys")
    @Embedded(prefix = "sys_")
    var sys: Sys?,
    @Json(name = "timezone")
    var timezone: Int?,
    @Json(name = "id")
    @PrimaryKey
    var id: Int?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "cod")
    var cod: Int?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Coord(
        @Json(name = "lon")
        var lon: Double?,
        @Json(name = "lat")
        var lat: Double?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Weather(
        @Json(name = "id")
        var id: Int?,
        @Json(name = "main")
        var main: String?,
        @Json(name = "description")
        var description: String?,
        @Json(name = "icon")
        var icon: String?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Main(
        @Json(name = "temp")
        var temp: Double?,
        @Json(name = "feels_like")
        var feelsLike: Double?,
        @Json(name = "temp_min")
        var tempMin: Double?,
        @Json(name = "temp_max")
        var tempMax: Double?,
        @Json(name = "pressure")
        var pressure: Int?,
        @Json(name = "humidity")
        var humidity: Int?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Wind(
        @Json(name = "speed")
        var speed: Double?,
        @Json(name = "deg")
        var deg: Int?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Clouds(
        @Json(name = "all")
        var all: Int?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Sys(
        @Json(name = "type")
        var type: Int?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "country")
        var country: String?,
        @Json(name = "sunrise")
        var sunrise: Int?,
        @Json(name = "sunset")
        var sunset: Int?
    ) : Parcelable
}