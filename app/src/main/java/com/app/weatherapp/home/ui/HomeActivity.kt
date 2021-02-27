package com.app.weatherapp.home.ui;

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.weatherapp.BaseActivity
import com.app.weatherapp.R
import com.app.weatherapp.databinding.ActivityHomeBinding
import com.app.weatherapp.receiver.DateTimeChangeReceiver
import com.app.weatherapp.service.ClearDbService
import java.util.*


class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, ClearDbService::class.java).let { intent ->
            PendingIntent.getService(this, 0, intent, 0)
        }

// Set the alarm to start at 12:01 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 1)
        }

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 DAY.
        alarmMgr.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }
}
