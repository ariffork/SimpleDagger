package com.ahmadarif.simpledagger.dagger.component

import com.ahmadarif.simpledagger.activity.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by ARIF on 13-Jun-17.
 */
@Singleton
@Component(modules = arrayOf())
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}