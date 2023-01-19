package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.database.nasaApi.NasaApi
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

val _imageResponse = MutableLiveData<PictureOfDay>()

val imageResponse : LiveData<PictureOfDay>
        get() =_imageResponse


    init {
        getImageResponse()
    }

    private fun getImageResponse(){

    }
}