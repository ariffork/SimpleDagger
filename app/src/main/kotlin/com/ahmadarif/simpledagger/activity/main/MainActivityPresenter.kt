package com.ahmadarif.simpledagger.activity.main

import android.view.View
import com.ahmadarif.simpledagger.dagger.qualifier.Authorized
import com.ahmadarif.simpledagger.extension.errorConverter
import com.ahmadarif.simpledagger.model.PublishSubjectModel
import com.ahmadarif.simpledagger.model.Response
import com.ahmadarif.simpledagger.mvp.Presenter
import com.ahmadarif.simpledagger.service.ApiService
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
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

        // cannot emmit again when error (ex: HttpException)
        compositeDispose.add(
                helloSubject
                        .retry()
                        .switchMap {
                            api.hello()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                        }
                        .doOnError {
                            err ->
                            if (err is HttpException) {
                                val body = retrofit.errorConverter<Response>(err)
                                view.onLoadHelloError("Error: ${body.message}")
                            } else {
                                view.onLoadHelloError(err.localizedMessage)
                            }
                        }
                        .subscribe {
                            res -> view.onLoadHelloSuccess(res)
                        }
        )

        // subject auto dispose using RxLifecycle
        messageSubject
                .compose(view.bindToLifecycle())
                .debounce(2, TimeUnit.SECONDS)
                .retry()
                .switchMap {
                    api.message()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .doOnError {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response>(err)
                        view.onLoadMessageError("Error: ${body.message}")
                    } else {
                        view.onLoadMessageError(err.localizedMessage)
                    }
                }
                .subscribe {
                    res -> view.onLoadMessageSuccess(res)
                }

        // example of using RxLifecycle
        Observable
                .interval(1, TimeUnit.SECONDS)
//                .compose(view.bindToLifecycle()) // ex1
                .compose(RxLifecycle.bindUntilEvent(view.lifecycle(), ActivityEvent.PAUSE)) // ex2
                .subscribe({
                    onNext -> println("onNext -> $onNext")
                }, {
                    onError -> println("onError -> " + onError.message)
                }, {
                    println("onCompleted")
                })
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
                        view?.onLoadHelloError("Error: ${body.message}")
                    } else {
                        view?.onLoadHelloError(err.localizedMessage)
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
                .retry()
                .switchMap { api.message().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
                .doOnError {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response>(err)
                        view?.onLoadMessageError("Error: ${body.message}")
                    } else {
                        view?.onLoadMessageError(err.localizedMessage)
                    }
                }
                .subscribe {
                    res -> view?.onLoadMessageSuccess(res)
                }
    }

}