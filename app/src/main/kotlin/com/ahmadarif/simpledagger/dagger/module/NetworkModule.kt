package com.ahmadarif.simpledagger.dagger.module

import android.app.Application
import com.ahmadarif.simpledagger.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by ARIF on 13-Jun-17.
 */
@Module
class NetworkModule(val baseUrl: String) {

    @Provides
    @Singleton
    fun cache(app: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(app.cacheDir, cacheSize)
        return cache
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun httpClient(logger: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(logger)
        builder.cache(cache)
        return builder.build()
    }

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    @Named("Authorized")
    fun httpClientAuth(logger: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor {
            chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder().header("Authorization", "Bearer tokeninirahasia")

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        builder.addInterceptor(logger)
        builder.cache(cache)
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("Authorized")
    fun retrofitAuth(@Named("Authorized") client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}