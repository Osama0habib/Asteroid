package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.domain.PictureOfDay

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid"
            ).build()
        }
    }
    return INSTANCE
}
@Dao
interface AsteroidDao {

    @Query("select * from DatabaseAsteroid")
    fun getAsteroid(): LiveData<List<DatabaseAsteroid>>

    @Query("select * from DatabaseAsteroid")
    fun getPictureOfTheDay() : LiveData<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: DatabaseAsteroid)


}

@Database(entities = [AsteroidDatabase::class], version = 1)
@Entity
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

}