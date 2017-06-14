package com.ahmadarif.simpledagger.service.interceptor

import android.content.SharedPreferences
import com.gambitechno.sidoi.extension.get
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by ARIF on 14-Jun-17.
 */
class AuthInterceptor(val pref: SharedPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (pref.get("token") == null) return chain.proceed(original)

        val builder = original.newBuilder().header("Authorization", "Bearer ${pref.get("token")}")
        val request = builder.build()
        return chain.proceed(request)
    }

}