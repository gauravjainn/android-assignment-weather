package com.app.weatherapp.util

import android.content.Context
import android.content.SharedPreferences


object Prefs {

    private const val DEFAULT_PREFERENCE_NAME = "takcarepreferences"

    private const val IS_LOGIN = "IS_LOGIN"
    private const val ROLE = "ROLE"
    private const val USER_ID = "USER_ID"
    private const val NAME = "NAME"
    private const val DEVICE_TOKEN = "DEVICE_TOKEN"
    private const val EMAIL = "EMAIL"
    private const val PHONE = "PHONE"
    private const val REQUEST_DISTANCE = "REQUEST_DISTANCE"

    fun preference(context: Context): SharedPreferences =
        context.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    fun clearPrefs(){

    }

    var SharedPreferences.clearValues
        get() = false
        set(value) {
            editMe {
                if (value){
                    val tempDeviceToken = deviceToken
                    it.clear()
                    it.commit()
                    deviceToken = tempDeviceToken

                }

            }
        }

    var SharedPreferences.isLogin
        get() = getBoolean(IS_LOGIN, false)
        set(value) {
            editMe {
                it.putBoolean(IS_LOGIN, value)
            }
        }

    var SharedPreferences.role
        get() = getString(ROLE, Constants.ERROR)
        set(value) {
            editMe {
                it.putString(ROLE, value)
            }
        }

    var SharedPreferences.userId
        get() = getInt(USER_ID, -1)
        set(value) {
            editMe {
                it.putInt(USER_ID, value)
            }
        }

    var SharedPreferences.name
        get() = getString(NAME, "")
        set(value) {
            editMe {
                it.putString(NAME, value)
            }
        }

    var SharedPreferences.email
        get() = getString(EMAIL, "")
        set(value) {
            editMe {
                it.putString(EMAIL, value)
            }
        }

    var SharedPreferences.phone
        get() = getString(PHONE, "")
        set(value) {
            editMe {
                it.putString(PHONE, value)
            }
        }

    var SharedPreferences.deviceToken
        get() = getString(DEVICE_TOKEN, "")
        set(value) {
            editMe {
                it.putString(DEVICE_TOKEN, value)
            }
        }

    var SharedPreferences.requestDistance
        get() = getInt(REQUEST_DISTANCE, 25)
        set(value) {
            editMe {
                it.putInt(REQUEST_DISTANCE, value)
            }
        }




}
