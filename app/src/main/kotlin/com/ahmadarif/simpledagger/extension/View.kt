package com.ahmadarif.simpledagger.extension

import android.app.Activity
import android.app.ProgressDialog
import com.ahmadarif.simpledagger.R

/**
 * Created by ARIF on 13-Jun-17.
 */
fun Activity.progressDialog(message: String): ProgressDialog {
    val dialog = ProgressDialog(this)
    dialog.setProgressStyle(R.style.AlertDialogStyle)
    dialog.setMessage(message)
    dialog.isIndeterminate = true
    dialog.setCanceledOnTouchOutside(false)
    dialog.cancel()
    return dialog
}