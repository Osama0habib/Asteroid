package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow



@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "codename")
    val codename: String,
    @ColumnInfo(name = "closeApproachDate")
    var closeApproachDate: String,
    @ColumnInfo(name = "absoluteMagnitude")
    var absoluteMagnitude: Double,
    @ColumnInfo(name = "estimatedDiameter")
    var estimatedDiameter: Double,
    @ColumnInfo(name = "relativeVelocity")
    var relativeVelocity: Double,
    @ColumnInfo(name = "distanceFromEarth")
    var distanceFromEarth: Double,
    @ColumnInfo(name = "isPotentiallyHazardous")
    var isPotentiallyHazardous: Boolean)



fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id = it.id ,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
                )
    }
}

