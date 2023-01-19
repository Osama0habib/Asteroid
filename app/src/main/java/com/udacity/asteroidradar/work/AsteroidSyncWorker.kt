import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException

class AsteroidSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object{
       const val WORK_NAME = "AsteroidSyncWorker"
    }
    override suspend fun doWork(): Result {
        try {
            // Some logic
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}
