package com.ahmadarif.simpledagger.activity.main

import android.view.View
import com.ahmadarif.simpledagger.dagger.qualifier.Authorized
import com.ahmadarif.simpledagger.extension.errorConverter
import com.ahmadarif.simpledagger.model.PublishSubjectModel
import com.ahmadarif.simpledagger.model.Response
import com.ahmadarif.simpledagger.mvp.Presenter
import com.ahmadarif.simpledagger.service.ApiService
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by ARIF on 13-Jun-17.
 */
class MainActivityPresenter @Inject constructor(
        val retrofit: Retrofit,
        @Authorized val api: ApiService
) : Presenter<MainActivityView> {

    var view: MainActivityView? = null

    var helloDisposable = Disposables.empty()

    val compositeDispose: CompositeDisposable = CompositeDisposable()
    val helloSubject: PublishSubject<Any> = PublishSubject.create()
    val messageSubject: PublishSubject<PublishSubjectModel> = PublishSubject.create()

    override fun onAttach(view: MainActivityView) {
        this.view = view

        compositeDispose.add(
                helloSubject
                        .switchMap {
                            api.hello().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        }
                        .subscribe({
                            res -> view.onLoadHelloSuccess(res)
                        }, {
                            err ->
                            if (err is HttpException) {
                                val body = retrofit.errorConverter<Response>(err)
                                view.onLoadMessageError("Error: ${body.message}")
                            } else {
                                view.onLoadMessageError(err.localizedMessage)
                            }
                        })
        )

        compositeDispose.add(
                messageSubject
                        .switchMap {
                            api.message()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                        }
                        .subscribe({
                            res -> view.onLoadMessageSuccess(res)
                        }, {
                            err ->
                            if (err is HttpException) {
                                val body = retrofit.errorConverter<Response>(err)
                                view.onLoadMessageError("Error: ${body.message}")
                            } else {
                                view.onLoadMessageError(err.localizedMessage)
                            }
                        })
        )
    }

    override fun onDetach() {
        helloDisposable.dispose()
        compositeDispose.clear()
        view = null
    }

    fun loadHello() {
        helloDisposable.dispose()
        helloDisposable = api.hello()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
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

    fun loadHello2() {
        helloSubject.onNext(Any())
    }

    fun loadMessage() {
        messageSubject.onNext(PublishSubjectModel())
    }

    fun subscribeBtnMessage(v: View) {
        RxView.clicks(v)
                .debounce(1, TimeUnit.SECONDS)
                .switchMap { api.message().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
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