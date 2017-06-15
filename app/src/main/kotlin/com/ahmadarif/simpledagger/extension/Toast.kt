package com.ahmadarif.simpledagger.extension

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.widget.Toast

/**
 * Created by ARIF on 15-Jun-17.
 */
fun Activity.toast(message: String, isLong: Boolean = false): Unit {
    when (isLong) {
        true -> Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toast(message: String, isLong: Boolean = false): Unit {
    when (isLong) {
        true -> Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        else -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}

fun toast(context: Context, message: String, isLong: Boolean = false): Unit {
    when (isLong) {
        true -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        else -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}