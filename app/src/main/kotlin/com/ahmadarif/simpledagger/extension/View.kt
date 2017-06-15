package com.ahmadarif.simpledagger.extension

import android.app.Activity
import android.app.ProgressDialog

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