package com.ahmadarif.simpledagger.activity.main

import com.ahmadarif.simpledagger.dagger.scope.MainActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by ARIF on 13-Jun-17.
 */
@Module
class MainActivityModule(val view: MainActivityView) {

    @Provides
    @MainActivityScope
    fun mainActivityView() : MainActivityView = view

}