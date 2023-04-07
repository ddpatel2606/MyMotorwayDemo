package com.dixit.mymotorwaydemo.mymodule.presentation.viewutils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(context: Context, activity: Activity) {
    val token = activity.currentFocus?.windowToken
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(token, 0)
}
