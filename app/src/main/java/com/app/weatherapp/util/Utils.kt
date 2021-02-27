package com.app.weatherapp.util

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.text.HtmlCompat
import java.util.regex.Pattern

object Utils {

    fun fromHtml(html: String?): Spanned {
        return if (html == null) {
            // return an empty spannable if the html is null
            SpannableString("")
        } else {
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    fun isValidEmail(email: String): Boolean {
        return if (email.equals("", ignoreCase = true)) {
            false
        } else {
            val EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            matcher.matches()
        }
    }

    fun isValidPhone(phone: String): Boolean {
        return if (phone.equals("", ignoreCase = true)) {
            false
        } else {
            phone.length == 10
        }
    }



    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}