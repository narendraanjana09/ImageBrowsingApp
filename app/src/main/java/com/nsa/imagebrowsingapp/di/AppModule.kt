package com.nsa.imagebrowsingapp.di

import com.nsa.imagebrowsingapp.api.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetofit():Retrofit=
        Retrofit.Builder()
            .baseUrl(ImageApi.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit):ImageApi =
        retrofit.create(ImageApi::class.java)
}