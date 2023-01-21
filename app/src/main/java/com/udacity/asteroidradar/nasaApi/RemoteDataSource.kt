package com.udacity.asteroidradar.nasaApi

import android.os.Build
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import org.json.JSONObject
import retrofit2.Call
import retrofit2.await
import retrofit2.awaitResponse
import java.time.LocalDate


class RemoteDataSource {
    private val nasaApi = NasaApi.retrofitService

     suspend fun getAsteroids(startDate: LocalDate, endDate: LocalDate) : ArrayList<DatabaseAsteroid> {
         println("Method get asteroids")
       val response = nasaApi.getAsteroidsAsync(
            start_date = startDate.toString(),
            end_date = endDate.toString(),
            apiKey = BuildConfig.API_KEY )
        println("response" + response)
       return parseAsteroidsJsonResult(
            JSONObject(
                response.await()

            )
        )
    }

    fun getImageOfTheDay(): Call<PictureOfDay> {
      return  nasaApi.getImageOfTheDay()
    }
}