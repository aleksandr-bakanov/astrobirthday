package bav.astrobirthday.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BirthdayUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val syncPlanetsInfo: SyncPlanetsInfo by inject()

    override suspend fun doWork(): Result {
        syncPlanetsInfo.sync()
        return Result.success()
    }

    companion object {
        const val UNIQUE_WORK_NAME = "bav.astrobirthday.birthdays_update"
        const val UNIQUE_ONE_TIME_WORK_NAME = "bav.astrobirthday.birthdays_update_one_time"
    }
}