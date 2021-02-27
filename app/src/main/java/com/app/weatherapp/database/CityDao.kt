package com.app.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.app.weatherapp.responseobject.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    // allowing the insert of the same weather data multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityList(cityList: List<City>)

    @Query("DELETE FROM city_table WHERE geonameid = :id")
    fun deleteCity(id: Int)

    @Query("DELETE FROM city_table WHERE geonameid IN (:ids)")
    fun deleteMultipleCityList(ids: List<Int>)

    @Query("DELETE FROM city_table")
    fun deleteAllCityList()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCity(city: City)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCityList(cityList: List<City>)

    @Query("SELECT * FROM city_table ORDER BY name COLLATE NOCASE ASC")
    fun getAllCityList(): LiveData<List<City>>

    @Query("SELECT * FROM city_table WHERE name LIKE :name ORDER BY name COLLATE NOCASE ASC")
    fun getCityListByCityName(name: String?): LiveData<List<City>>

    @Query("SELECT * FROM city_table WHERE name LIKE :name ORDER BY name COLLATE NOCASE ASC")
    fun getCityListByCityNameFlow(name: String?): Flow<List<City>>

    @Query("SELECT * FROM city_table WHERE geonameid = :id")
    fun getCityById(id: Int): LiveData<City>

    @Query("SELECT * FROM city_table LIMIT 1")
    fun getAnyCity(): LiveData<City>

    @RawQuery(observedEntities = [City::class])
    fun getAllCityList(query: SupportSQLiteQuery?): Flow<List<City>>

}