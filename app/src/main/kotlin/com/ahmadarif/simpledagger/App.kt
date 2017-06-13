package com.ahmadarif.simpledagger

import android.app.Application
import com.ahmadarif.simpledagger.dagger.component.AppComponent
import com.ahmadarif.simpledagger.dagger.component.DaggerAppComponent
import com.ahmadarif.simpledagger.dagger.module.ApiModule
import com.ahmadarif.simpledagger.dagger.module.AppModule
import com.ahmadarif.simpledagger.dagger.module.NetworkModule

/**
 * Created by ARIF on 13-Jun-17.
 */
class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule("https://adonis.daily-event.com/"))
                .apiModule(ApiModule())
                .build()
    }

}