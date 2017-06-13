package com.ahmadarif.simpledagger.model

/**
 * Created by ARIF on 13-Jun-17.
 */
data class Response<out T>(
    val message: String = "",
    val data: T?
)