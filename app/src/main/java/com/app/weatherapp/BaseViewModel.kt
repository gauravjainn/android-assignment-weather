package com.app.weatherapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val someErrorOccurred = application.getString(R.string.some_error_occurred)
    val networkError = application.getString(R.string.network_error)

    protected val _apiError = MutableLiveData<String?>()

    val apiError: LiveData<String?>
        get() = _apiError

    fun noApiError() {
        _apiError.value = null
    }

    fun setApiError(error: String?) {
        _apiError.value = error
    }

}