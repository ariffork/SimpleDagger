package com.ahmadarif.simpledagger.activity.main

import com.ahmadarif.simpledagger.dagger.qualifier.Authorized
import com.ahmadarif.simpledagger.extension.errorConverter
import com.ahmadarif.simpledagger.model.Response
import com.ahmadarif.simpledagger.mvp.Presenter
import com.ahmadarif.simpledagger.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * Created by ARIF on 13-Jun-17.
 */
class MainActivityPresenter @Inject constructor(
        val retrofit: Retrofit,
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
                    res -> view?.onLoadHelloSuccess(res)
                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response>(err)
                        view?.onLoadMessageError("Error: ${body.message}")
                    } else {
                        view?.onLoadMessageError(err.localizedMessage)
                    }
                })
    }

    fun loadMessage() {
        api.message()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res -> view?.onLoadMessageSuccess(res)
                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response>(err)
                        view?.onLoadMessageError("Error: ${body.message}")
                    } else {
                        view?.onLoadMessageError(err.localizedMessage)
                    }
                })
    }

}