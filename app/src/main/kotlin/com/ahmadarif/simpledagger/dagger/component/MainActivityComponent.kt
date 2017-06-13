package com.ahmadarif.simpledagger.dagger.component

import com.ahmadarif.simpledagger.activity.main.MainActivity
import com.ahmadarif.simpledagger.dagger.scope.MainActivityScope
import dagger.Component

/**
 * Created by ARIF on 13-Jun-17.
 */
@MainActivityScope
@Component(dependencies = arrayOf(AppComponent::class))
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

}