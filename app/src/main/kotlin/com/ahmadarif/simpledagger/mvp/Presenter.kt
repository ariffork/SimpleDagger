package com.ahmadarif.simpledagger.mvp

/**
 * Created by ARIF on 13-Jun-17.
 */
interface Presenter<in T : View> {

    fun onAttach(view: T)
    fun onDetach()

}