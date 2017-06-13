package com.ahmadarif.simpledagger

import android.app.Application
import com.ahmadarif.simpledagger.dagger.component.AppComponent

/**
 * Created by ARIF on 13-Jun-17.
 */
class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
    }

}