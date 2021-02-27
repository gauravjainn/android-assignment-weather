package com.app.weatherapp.responseobject
import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

import androidx.annotation.Keep

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@SuppressLint("ParcelCreator")
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "cod")
    var cod: String?,
    @Json(name = "message")
    var message: String?
) : Parcelable