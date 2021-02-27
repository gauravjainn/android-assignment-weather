package com.app.weatherapp.util


import android.app.Dialog
import android.content.Context
import android.view.Window
import com.app.weatherapp.R


class CustomAVDialog(context: Context) :
    Dialog(context, android.R.style.Theme_Holo_Dialog_NoActionBar) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.av_loader)
        setCancelable(false)
        window!!.setBackgroundDrawableResource(R.color.transparent)
    }
}