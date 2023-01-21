package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.nasaApi.RemoteDataSource
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.time.LocalDate


class AsteroidRepository(private val database: AsteroidDatabase) {
    val dataSource = RemoteDataSource()

//    private val _asteroids = MutableLiveData<List<Asteroid>>()
    lateinit var asteroids : LiveData<List<Asteroid>>
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = LocalDate.now();
    @RequiresApi(Build.VERSION_CODES.O)
    val lastDate = LocalDate.now().plusDays(7)

//     suspend fun filter(value : Int?): LiveData<List<Asteroid>> {
//
//
//         asteroids = when(value){
//             R.id.show_today_menu -> Transformations.map(database.asteroidDao.getTodayAsteroid(currentDate.toString())) {
//                 it.asDomainModel()
//             }
////             R.id.show_saved_menu -> Transformations.map(database.asteroidDao.getAllAsteroid()) {
////                 it.asDomainModel()
////             }
//             else -> Transformations.map(database.asteroidDao.get7DayAsteroid(currentDate.toString(),lastDate.toString())) {
//                 it.asDomainModel()
//             }
//         }
//         return asteroids;
//    }





    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAsteroids(item : Int?) : List<Asteroid>? {
        lateinit var asteroidList: List<Asteroid>
        try {
            database.asteroidDao.clearFromThePast(currentDate.toString())
        asteroidList = database.asteroidDao.getAllAsteroid().asDomainModel()
            asteroidList = when(item){
             R.id.show_today_menu -> database.asteroidDao.getTodayAsteroid(currentDate.toString()).asDomainModel()
             R.id.show_saved_menu -> database.asteroidDao.getAllAsteroid().asDomainModel()
             else -> database.asteroidDao.get7DayAsteroid(currentDate.toString(),lastDate.toString()).asDomainModel()
         }

         return asteroidList;

            println("getasteroid" + asteroidList.size)
        }catch (e : Exception){
           println(e.message)
        }
        return  asteroidList
    }

    suspend fun getImage(): PictureOfDay? {
         var pictureOfDay: PictureOfDay? = null
        try {
                pictureOfDay =  dataSource.getImageOfTheDay().await()
            } catch (e: Exception) {
            println(e.message)
            }
        return pictureOfDay
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshAsteroids() {
        println("refresh asteroids")
        withContext(Dispatchers.IO) {
            val remoteDataSource = RemoteDataSource()
            val startDate = LocalDate.now();
            val endDate = LocalDate.now().plusDays(7)
            val asteroid = remoteDataSource.getAsteroids(startDate = startDate, endDate = endDate)
            database.asteroidDao.insertAll(asteroid)
        }
    }

}


