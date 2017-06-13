package com.ahmadarif.simpledagger.activity.main

import android.app.Application
import android.content.SharedPreferences
import com.ahmadarif.simpledagger.mvp.Presenter
import com.ahmadarif.simpledagger.service.ApiService
import com.gambitechno.sidoi.extension.debug
import com.gambitechno.sidoi.extension.get
import com.gambitechno.sidoi.extension.save
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ARIF on 13-Jun-17.
 */
class MainActivityPresenter @Inject constructor(
        val app: Application,
        val api: ApiService,
        val pref: SharedPreferences
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
                    pref.save("message", res.message)
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
                    pref.save("message", res.message)
                    view?.onLoadMessageSuccess(res)
                }, {
                    err -> view?.onLoadMessageError(err.localizedMessage)
                })
    }

    fun logPref() {
        app.debug("message = ${pref.get("message")}")
    }

}