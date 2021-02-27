package com.app.weatherapp.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class EmptyTextWatcher(private val textInputLayout: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
        if (p0!!.toString().trim().isEmpty()) {
        } else {
            textInputLayout.error = null
        }

    override fun afterTextChanged(p0: Editable?) {
    }
}