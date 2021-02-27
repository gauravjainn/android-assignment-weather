package com.app.weatherapp.networking.api

import com.app.weatherapp.networking.ApiResponse
import com.app.weatherapp.responseobject.City
import com.app.weatherapp.responseobject.WeatherData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes methods
 */
interface ApiService {


    /*Login APIs*/

//    @GET("weather")
//    suspend fun getWeatherDataByCityId(@Query(value = "id") cityId: Int,
//                                @Query(value = "appid") apiKey: String):
//            NetworkResponse<WeatherData, ErrorResponse>

    @GET("weather")
    fun getWeatherDataByCityId(
        @Query(value = "id") cityId: Int,
        @Query(value = "appid") apiKey: String,
        @Query(value = "units") units: String
    ):
            Flow<ApiResponse<WeatherData>>

    @GET("city")
    fun getAllCities(
        @Query(value = "appid") apiKey: String,
    ):
            Flow<ApiResponse<List<City>>>


//    /* Settings APIs */
//
//    @POST("update_request_distance")
//    suspend fun updateRequestDistance(@Body updateRequestDistancePost: UpdateRequestDistancePost):
//            NetworkResponse<CommonResponse, CommonResponse>
//
//
//    /* Logout API */
//
//    @POST("logout")
//    suspend fun logout(@Body logoutPost: LogoutPost):
//            NetworkResponse<CommonResponse, CommonResponse>
}

