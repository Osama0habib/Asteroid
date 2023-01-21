package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate

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
    suspend fun  getAllAsteroid(): List<DatabaseAsteroid>

    @Query("select * from DatabaseAsteroid WHERE closeApproachDate BETWEEN :currentDate AND :lastDate ORDER BY closeApproachDate ASC")
    suspend fun  get7DayAsteroid(currentDate: String, lastDate: String): List<DatabaseAsteroid>
    @Query("SELECT * from DatabaseAsteroid WHERE closeApproachDate == :currentDate ORDER BY closeApproachDate ASC")
    suspend fun  getTodayAsteroid(currentDate: String): List<DatabaseAsteroid>

    @Query("DELETE from DatabaseAsteroid WHERE closeApproachDate < :currentDate")
    suspend fun clearFromThePast(currentDate: String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( asteroid: List<DatabaseAsteroid>)


}

@Database(entities = [DatabaseAsteroid::class], version = 1)
@Entity
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

}