package com.ahmadarif.simpledagger.activity.main

import com.ahmadarif.simpledagger.dagger.component.AppComponent
import com.ahmadarif.simpledagger.dagger.scope.MainActivityScope
import dagger.Component

/**
 * Created by ARIF on 13-Jun-17.
 */
@MainActivityScope
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(MainActivityModule::class)
)
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

}