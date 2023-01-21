package com.udacity.asteroidradar.nasaApi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface NasaApiService {
    @GET("planetary/apod")
    fun getImageOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ) : Call<PictureOfDay>

    @GET("neo/rest/v1/feed")
    fun getAsteroidsAsync(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
    ) : Call<String>

}

object NasaApi {
    val retrofitService : NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}