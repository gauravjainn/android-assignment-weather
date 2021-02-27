package com.app.weatherapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.weatherapp.R
import com.app.weatherapp.util.CustomAVDialog
import com.app.weatherapp.util.NetworkUtility
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    var isNetworkConnected = false
    private var avDialog: CustomAVDialog? = null

    var connectivityReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (!isNetworkConnected) {
                onNetworkChange(
                    activeNetwork != null
                            && activeNetwork.isConnectedOrConnecting
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        isNetworkConnected = NetworkUtility.isConnected(this)


        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(connectivityReceiver, filter)

    }


    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    open fun onNetworkChange(isConnected: Boolean) {
        isNetworkConnected = isConnected
    }


    open fun showSnackBar(view: View, message: String?) {
        Snackbar.make(view, message!!, Snackbar.LENGTH_LONG).show()
    }

    open fun showNoInternet(view: View) {
        Snackbar.make(view, getString(R.string.no_internet), Snackbar.LENGTH_LONG).show()
    }


    open fun showProgress() {
        if (avDialog == null) {
            avDialog = CustomAVDialog(this)
        }
        if (!avDialog!!.isShowing) {
            avDialog!!.show()
        }
    }

    open fun dismissProgress() {
        if (avDialog != null) {
            if (avDialog!!.isShowing) {
                avDialog!!.dismiss()
            }
            avDialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgress()
        try {
            unregisterReceiver(connectivityReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
