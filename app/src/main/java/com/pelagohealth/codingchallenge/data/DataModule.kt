package com.pelagohealth.codingchallenge.data

import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Hilt module that provides dependencies for the data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // FIX: Added @Singleton to avoid creating new instances on every injection
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    // FIX: Added @Singleton to avoid creating new instances on every injection
    @Provides
    @Singleton
    fun provideFactsApi(okHttpClient: OkHttpClient): FactsRestApi =
        Retrofit.Builder()
            .baseUrl("https://uselessfacts.jsph.pl/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(FactsRestApi::class.java)
}