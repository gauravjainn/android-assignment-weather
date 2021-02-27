package com.app.weatherapp.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.app.weatherapp.BuildConfig
import com.app.weatherapp.networking.Resource
import com.app.weatherapp.networking.api.Api
import com.app.weatherapp.networking.networkBoundResource
import com.app.weatherapp.responseobject.City
import com.app.weatherapp.util.DbConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CityRepository(application: Application) {

    private val db: Db = Db.getDatabase(application)

    private var cityDao: CityDao = db.cityDao()!!


    private var cityName: MutableLiveData<String> = MutableLiveData("")


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    fun setCitySearchQuery(cityName: String) {
        this.cityName.value = cityName
    }

    fun getCityById(cityId: Int): LiveData<City> {
        return cityDao.getCityById(cityId)
    }

    fun isCityTableEmpty(): LiveData<City> {
        return cityDao.getAnyCity()
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    fun insertCity(city: City) {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.insertCity(city) })
    }

    fun insertCityList(cityList: List<City>) {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.insertCityList(cityList) })
    }

    fun updateCity(city: City) {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.updateCity(city) })
    }

    fun updateCityList(cityList: List<City>) {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.updateCityList(cityList) })
    }

    fun deleteCity(id: Int) {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.deleteCity(id) })
    }

    fun deleteMultipleCityList(ids: List<Int>) {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.deleteMultipleCityList(ids) })
    }

    fun deleteAllCityList() {
        Db.databaseWriteExecutor.execute(Runnable { cityDao.deleteAllCityList() })
    }


    fun clearDb() {
        Db.databaseWriteExecutor.execute(Runnable {})
    }


    fun getCityList(cityName: String): Flow<Resource<List<City>>> {
        return networkBoundResource(
            fetchFromLocal = {

                if (cityName.isNotEmpty()) {


                    val query = StringBuilder("SELECT * FROM " + DbConstants.TABLE_CITY + " WHERE ")

                    query.append("name LIKE '%")
                        .append(cityName)
                        .append("%' ")

                    query.append(" ORDER BY name COLLATE NOCASE ASC LIMIT 100")
                    val sqLiteQuery = SimpleSQLiteQuery(query.toString())
                    cityDao.getAllCityList(sqLiteQuery)
                }else {
                    cityDao.getCityListByCityNameFlow("-1")
                }

//                cityDao.getCityListByCityNameFlow(cityName)

            },
            shouldFetchFromRemote = { false },
            fetchFromRemote = {
                Api.retrofitService().getAllCities(
                    BuildConfig.API_KEY
                )
            },
            processRemoteResponse = { },
            saveRemoteData = { insertCityList(it) },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }
}