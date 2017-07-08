package com.ahmadarif.simpledagger.mvp

import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable

/**
 * Created by ARIF on 13-Jun-17.
 */
interface View {

    fun onAttach()
    fun onDetach()

    // RxLifecycle
    fun lifecycle(): Observable<ActivityEvent>
    fun <T> bindToLifecycle(): LifecycleTransformer<T>

}