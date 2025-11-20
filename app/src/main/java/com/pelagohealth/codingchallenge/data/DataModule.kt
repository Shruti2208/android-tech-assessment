package com.pelagohealth.codingchallenge.data

import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Hilt module that provides dependencies for the data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
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