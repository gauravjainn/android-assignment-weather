package com.app.weatherapp.home.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.app.weatherapp.BaseViewModel
import com.app.weatherapp.database.CityRepository
import com.app.weatherapp.database.WeatherRepository
import com.app.weatherapp.networking.Resource
import com.app.weatherapp.responseobject.City
import com.app.weatherapp.responseobject.WeatherData
import kotlinx.coroutines.flow.map

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val weatherRepository: WeatherRepository = WeatherRepository(application)
    private val cityRepository: CityRepository = CityRepository(application)

    val isCityTableEmpty : LiveData<City> = cityRepository.isCityTableEmpty()
    private var cityName: MutableLiveData<String> = MutableLiveData("")
    private var cityId: MutableLiveData<Int> = MutableLiveData(-1)


    var cityList: LiveData<Resource<List<City>>> = Transformations.switchMap(cityName){
        cityRepository.getCityList(cityName.value!!).map {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Resource.loading(null)
                }
                Resource.Status.SUCCESS -> {
                    val cityList = it.data
                    Resource.success(cityList)
                }
                Resource.Status.ERROR -> {
                    Resource.error(it.message!!, null)
                }
            }
        }.asLiveData(viewModelScope.coroutineContext)
    }

    val weatherData: LiveData<Resource<WeatherData>> = Transformations.switchMap(cityId) {
            weatherRepository.getWeatherData(cityId.value!!).map {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Resource.loading(null)
                    }
                    Resource.Status.SUCCESS -> {
                        val weatherData = it.data
                        Resource.success(weatherData)
                    }
                    Resource.Status.ERROR -> {
                        Resource.error(it.message!!, null)
                    }
                }
            }.asLiveData(viewModelScope.coroutineContext)
        }

    fun setCitySearchQuery(cityName: String) {
        this.cityName.value = cityName
        //cityRepository.setCitySearchQuery(cityName)
    }

    fun setCityId(cityId: Int) {
        this.cityId.value = cityId
        //weatherRepository.setCityId(cityId)
    }


    fun insertCityList(cityList: List<City>) {
        cityRepository.insertCityList(cityList)
    }








}