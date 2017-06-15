package com.ahmadarif.simpledagger.extension

import android.app.Activity
import android.app.ProgressDialog
import android.widget.Toast

/**
 * Created by ARIF on 13-Jun-17.
 */
fun Activity.progressDialog(message: String): ProgressDialog {
    val dialog = ProgressDialog(this)
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    dialog.setMessage(message)
    dialog.isIndeterminate = true
    dialog.setCanceledOnTouchOutside(false)
    dialog.cancel()
    return dialog
}

fun Activity.toast(message: String, isLong: Boolean = false): Unit {
    when (isLong) {
        true -> Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}