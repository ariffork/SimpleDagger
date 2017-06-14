package com.ahmadarif.simpledagger

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by ARIF on 14-Jun-17.
 */
class AuthenticationInterceptor(val authToken: String?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()

        if (authToken != null) builder.header("Authorization", "Bearer $authToken")

        val request = builder.build()
        return chain.proceed(request)
    }
}