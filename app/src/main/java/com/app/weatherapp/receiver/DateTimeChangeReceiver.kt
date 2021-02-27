package com.app.weatherapp.receiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.app.weatherapp.database.WeatherRepository

class DateTimeChangeReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {


            val application = p0?.applicationContext as Application

            val weatherRepository = WeatherRepository(application)
            weatherRepository.deleteAllWeatherDataList()

//            val myIntent = Intent(p0, ClearDbService::class.java)
//            val pendingIntent = PendingIntent.getService(p0, 0, myIntent, 0)
//            val alarmManager = p0?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
//            val calendar: Calendar = Calendar.getInstance()
//            calendar.add(Calendar.SECOND, 5)
//            alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = pendingIntent
            Log.d(
                DateTimeChangeReceiver::class.simpleName,
                "DateTimeChangeReceiver Called" + p1?.action
            )

    }
}