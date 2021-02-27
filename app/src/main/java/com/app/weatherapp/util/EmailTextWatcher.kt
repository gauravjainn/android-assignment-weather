package com.app.weatherapp.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class EmailTextWatcher(private val textInputLayout: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        if (charSequence.toString().trim().isEmpty()) {
            //textInputLayout.setError(null);
        } else {
            val isValidEmail: Boolean =
                Utils.isValidEmail(charSequence.toString().trim())
            if (isValidEmail) {
                textInputLayout.error = null
                textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
                //textInputLayout.setEndIconDrawable(R.drawable.ic_icon_awesome_check_circle)
                //textInputLayout.setEndIconMode(END_ICON_NONE);
            } else {
                textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
                //textInputLayout.setEndIconDrawable(R.drawable.ic_icon_awesome_red_cross)
            }
        }
    }

    override fun afterTextChanged(editable: Editable) {}
}