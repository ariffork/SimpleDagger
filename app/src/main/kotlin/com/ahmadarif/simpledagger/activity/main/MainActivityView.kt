package com.ahmadarif.simpledagger.activity.main

import com.ahmadarif.simpledagger.model.Response
import com.ahmadarif.simpledagger.mvp.View

/**
 * Created by ARIF on 13-Jun-17.
 */
interface MainActivityView : View {

    fun onLoadHelloSuccess(data: Response)
    fun onLoadHelloError(data: String)

    fun onLoadMessageSuccess(data: Response)
    fun onLoadMessageError(data: String)

}