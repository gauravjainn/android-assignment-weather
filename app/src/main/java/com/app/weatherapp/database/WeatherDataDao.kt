package com.app.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.weatherapp.responseobject.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDataDao {
    // allowing the insert of the same weather data multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData: WeatherData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherDataList(weatherDataList: List<WeatherData>)

    @Query("DELETE FROM weather_data_table WHERE id = :id")
    fun deleteWeatherData(id: Int)

    @Query("DELETE FROM weather_data_table WHERE id IN (:ids)")
    fun deleteMultipleWeatherDataList(ids: List<Int>)

    @Query("DELETE FROM weather_data_table")
    fun deleteAllWeatherDataList()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeatherData(weatherData: WeatherData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeatherDataList(weatherDataList: List<WeatherData>)

    @Query("SELECT * FROM weather_data_table ORDER BY id COLLATE NOCASE ASC")
    fun getAllWeatherDataList(): LiveData<List<WeatherData>>

    @Query("SELECT * FROM weather_data_table WHERE name LIKE :name ORDER BY name COLLATE NOCASE ASC")
    fun getWeatherDataListByCityName(name: String?): LiveData<List<WeatherData>>

    @Query("SELECT * FROM weather_data_table WHERE id = :id")
    fun getWeatherDataById(id: Int): LiveData<WeatherData>

    @Query("SELECT * FROM weather_data_table WHERE id = :id")
    fun getWeatherDataByIdFlow(id: Int): Flow<WeatherData>

    @Query("SELECT COUNT() FROM weather_data_table WHERE id = :id")
    fun count(id: Int): Int

}