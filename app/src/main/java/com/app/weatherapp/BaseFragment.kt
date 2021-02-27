package com.app.weatherapp


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.app.weatherapp.R
import com.app.weatherapp.util.CustomAVDialog
import com.app.weatherapp.util.NetworkUtility
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment : Fragment() {

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

        if (activity != null) {
            isNetworkConnected = NetworkUtility.isConnected(requireActivity())
        }

        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        if (activity != null) {
            requireActivity().registerReceiver(connectivityReceiver, filter)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return TextView(activity)
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
            avDialog = CustomAVDialog(requireActivity())
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

    override fun onDestroyView() {
        super.onDestroyView()
        dismissProgress()
        try {
            requireActivity().unregisterReceiver(connectivityReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
