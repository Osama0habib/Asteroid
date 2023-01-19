package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.nasaApi.NasaApi
import com.udacity.asteroidradar.database.nasaApi.NasaApiService
import com.udacity.asteroidradar.database.nasaApi.RemoteDataSource
import com.udacity.asteroidradar.database.nasaApi.asDatabaseModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

import java.util.Date


class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroid()) {
        it.asDomainModel()
    }

    val pictureOfDay : LiveData<PictureOfDay> = Transformations.distinctUntilChanged(database.asteroidDao.getPictureOfTheDay())

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val d = Date();
            val dataSource = RemoteDataSource()
            val playlist = dataSource.getAsteroids(startDate = LocalDate.now().toString() , endDate = LocalDate.now().toString() )
            database.asteroidDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}


