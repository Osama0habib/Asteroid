package com.udacity.asteroidradar.database.nasaApi

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface NasaApiService {
    @GET("planetary/apod?api_key=" + BuildConfig.API_KEY)
    fun getImageOfTheDay() : PictureOfDay

    @GET("neo/rest/v1/feed?api_key=" + BuildConfig.API_KEY)
    fun getAsteroidsAsync(startDate : String, endDate : String) : Deferred<NetworkAsteroidContainer>


}

object NasaApi {
    val retrofitService : NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}