package com.ahmadarif.simpledagger.dagger.component

import com.ahmadarif.simpledagger.activity.main.MainActivity
import com.ahmadarif.simpledagger.dagger.module.ApiModule
import com.ahmadarif.simpledagger.dagger.module.AppModule
import com.ahmadarif.simpledagger.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton


/**
 * Created by ARIF on 13-Jun-17.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        ApiModule::class
))
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}