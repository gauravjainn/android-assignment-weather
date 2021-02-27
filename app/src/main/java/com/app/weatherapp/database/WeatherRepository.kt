package com.app.weatherapp.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.weatherapp.BuildConfig
import com.app.weatherapp.networking.Resource
import com.app.weatherapp.networking.api.Api
import com.app.weatherapp.networking.networkBoundResource
import com.app.weatherapp.responseobject.WeatherData
import com.app.weatherapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepository(application: Application) {

    private val db: Db = Db.getDatabase(application)
    private var weatherDataDao: WeatherDataDao = db.weatherDataDao()!!

    private var cityId: MutableLiveData<Int> = MutableLiveData(-1)


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.


    fun setCityId(cityId: Int) {
        this.cityId.value = cityId
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    fun insertWeatherData(weatherData: WeatherData) {
        Db.databaseWriteExecutor.execute(Runnable { weatherDataDao.insertWeatherData(weatherData) })
    }

    fun insertWeatherDataList(weatherDataList: List<WeatherData>) {
        Db.databaseWriteExecutor.execute(Runnable {
            weatherDataDao.insertWeatherDataList(
                weatherDataList
            )
        })
    }

    fun updateWeatherData(weatherData: WeatherData) {
        Db.databaseWriteExecutor.execute(Runnable { weatherDataDao.updateWeatherData(weatherData) })
    }

    fun updateWeatherDataList(weatherDataList: List<WeatherData>) {
        Db.databaseWriteExecutor.execute(Runnable {
            weatherDataDao.updateWeatherDataList(
                weatherDataList
            )
        })
    }

    fun deleteWeatherData(id: Int) {
        Db.databaseWriteExecutor.execute(Runnable { weatherDataDao.deleteWeatherData(id) })
    }

    fun deleteMultipleWeatherDataList(ids: List<Int>) {
        Db.databaseWriteExecutor.execute(Runnable { weatherDataDao.deleteMultipleWeatherDataList(ids) })
    }

    fun deleteAllWeatherDataList() {
        Db.databaseWriteExecutor.execute(Runnable { weatherDataDao.deleteAllWeatherDataList() })
    }



    private val _weatherDataResponse = MutableLiveData<WeatherData>()

    val weatherDataResponse: LiveData<WeatherData>
        get() = _weatherDataResponse


    /* APIs */


    fun getWeatherData(cityId: Int): Flow<Resource<WeatherData>> {
        return networkBoundResource(
            fetchFromLocal = { weatherDataDao.getWeatherDataByIdFlow(cityId) },
            shouldFetchFromRemote = { cityId != -1 && weatherDataDao.count(cityId) <= 0 },
            fetchFromRemote = {

                Api.retrofitService().getWeatherDataByCityId(
                    cityId,
                    BuildConfig.API_KEY, Constants.UNITS_TYPE
                )

            },
            processRemoteResponse = { },
            saveRemoteData = { insertWeatherData(it) },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }


}