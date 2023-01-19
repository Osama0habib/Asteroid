package com.udacity.asteroidradar.database.nasaApi

import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject


class RemoteDataSource {
     private val nasaApi = NasaApi.retrofitService

    fun getAsteroids(startDate : String , endDate : String) : Flow<List<Asteroid>> = flow{

            emit(
                parseAsteroidsJsonResult(
                    JSONObject(
                    nasaApi.getAsteroidsAsync(startDate,endDate).toString()
                    )
                )
            )
    }

    fun getImageOfTheDay() : Flow<PictureOfDay> = flow {
        emit(nasaApi.getImageOfTheDay())
    }
}