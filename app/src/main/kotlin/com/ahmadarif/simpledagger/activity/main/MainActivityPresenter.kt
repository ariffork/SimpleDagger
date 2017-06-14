package com.ahmadarif.simpledagger.activity.main

import com.ahmadarif.simpledagger.dagger.qualifier.Authorized
import com.ahmadarif.simpledagger.mvp.Presenter
import com.ahmadarif.simpledagger.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ARIF on 13-Jun-17.
 */
class MainActivityPresenter @Inject constructor(
        @Authorized val api: ApiService
) : Presenter<MainActivityView> {

    var view: MainActivityView? = null

    override fun onAttach(view: MainActivityView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun loadHello() {
        api.hello()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->
                    view?.onLoadHelloSuccess(res)
                }, {
                    err -> view?.onLoadHelloError(err.localizedMessage)
                })
    }

    fun loadMessage() {
        api.message()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->
                    view?.onLoadMessageSuccess(res)
                }, {
                    err -> view?.onLoadMessageError(err.localizedMessage)
                })
    }

}