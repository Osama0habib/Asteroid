package com.udacity.asteroidradar
import AsteroidSyncWorker
import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class AsteroidApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() = applicationScope.launch {
        initAsteroidWorker()
    }


    private fun initAsteroidWorker()
    {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresCharging(true)
            .build()

        val syncWorkRequest =
            PeriodicWorkRequestBuilder<AsteroidSyncWorker>(
                1,
                TimeUnit.DAYS
            ).setConstraints(constraints).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidSyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkRequest
        )

    }

}

