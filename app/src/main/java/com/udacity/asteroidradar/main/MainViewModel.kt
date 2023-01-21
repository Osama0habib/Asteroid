package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import com.udacity.asteroidradar.R
import kotlinx.coroutines.async

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(app: Application) : ViewModel() {


    private val database = getDatabase(app)

    private val asteroidRepository = AsteroidRepository(database)



    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids : LiveData<List<Asteroid>>
        get() = _asteroids

    private val _imageOfDay = MutableLiveData<PictureOfDay?>()

    val imageOfDay: LiveData<PictureOfDay?>
        get() = _imageOfDay
    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails
        get() = _navigateToAsteroidDetails

    init {

        getAsteroids(null)
        getImage()
//        println("asteroid lenght" + asteroids.value?.size)
    }

     fun getImage(){
         viewModelScope.launch {
             _imageOfDay.value = asteroidRepository.getImage()
         }
    }

    fun getAsteroids(item : Int?){
        viewModelScope.launch {
         _asteroids.value =   asteroidRepository.getAsteroids(item)
        }
    }




    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }


    fun onAsteroidNavigated() {
        _navigateToAsteroidDetails.value = null
    }

     fun filterAsteroids(item: Int){
          getAsteroids(item)

    }
        class Factory(val app: Application) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(app) as T
                }
                throw IllegalArgumentException("Unable to construct viewmodel")
            }
        }
    }



