package com.ahmadarif.simpledagger.service

import com.ahmadarif.simpledagger.model.Response
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by ARIF on 13-Jun-17.
 */
interface ApiService {

    @GET("api")
    fun hello() : Observable<Response>

    @GET("api/message")
    fun message() : Observable<Response>

}