package com.ahmadarif.simpledagger.dagger.module

import com.ahmadarif.simpledagger.service.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by ARIF on 13-Jun-17.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

}