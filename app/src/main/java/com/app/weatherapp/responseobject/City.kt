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
@Entity(tableName = DbConstants.TABLE_CITY)
data class City(
    @Json(name = "country")
    var country: String?,
    @Json(name = "geonameid")
    @PrimaryKey
    var geonameid: Int?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "subcountry")
    var subcountry: String?
) : Parcelable