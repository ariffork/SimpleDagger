package com.gambitechno.sidoi.extension

import android.content.SharedPreferences

/**
 * Created by ARIF on 13-Jun-17.
 */
fun SharedPreferences.get(name: String) : String? {
    return this.getString(name, null)
}

fun <T> SharedPreferences.save(name: String, data: T) {
    this.edit().putString(name, data.toString()).apply()
}

fun SharedPreferences.remove(name: String) {
    this.edit().putString(name, null).apply()
}

fun SharedPreferences.clear() {
    this.edit().clear().apply()
}