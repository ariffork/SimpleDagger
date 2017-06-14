package com.ahmadarif.simpledagger.dagger.component

import android.app.Application
import android.content.SharedPreferences
import com.ahmadarif.simpledagger.dagger.module.ApiModule
import com.ahmadarif.simpledagger.dagger.module.AppModule
import com.ahmadarif.simpledagger.dagger.module.NetworkModule
import com.ahmadarif.simpledagger.dagger.qualifier.Authorized
import com.ahmadarif.simpledagger.service.ApiService
import dagger.Component
import javax.inject.Named
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

    fun application(): Application
    fun sharedPreferences(): SharedPreferences
    fun apiService() : ApiService
    @Authorized fun apiServiceAuth() : ApiService

}