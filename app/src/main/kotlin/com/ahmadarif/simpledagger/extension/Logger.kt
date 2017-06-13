package com.gambitechno.sidoi.extension

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.util.Log

/**
 * Created by ARIF on 12-Jun-17.
 */
fun Activity.debug(message: String) : Unit {
    Log.d(this.javaClass.simpleName, message)
}

fun Fragment.debug(message: String) : Unit {
    Log.d(this.javaClass.simpleName, message)
}

fun Application.debug(message: String): Unit {
    Log.d(this.javaClass.simpleName, message)
}