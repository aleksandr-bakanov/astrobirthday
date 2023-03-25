package bav.astrobirthday.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import bav.astrobirthday.utils.enqueuePeriodicBirthdayUpdateWorker
import com.google.common.util.concurrent.ListenableFuture

class BirthdayUpdater(
    private val context: Context
) {
    fun updateBirthdays() {
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<BirthdayUpdateWorker>().build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            BirthdayUpdateWorker.UNIQUE_ONE_TIME_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
        enqueuePeriodicBirthdayUpdateWorker(context)
    }

    fun getBirthdayUpdateWorkerStateObserver(): ListenableFuture<List<WorkInfo>> {
        val workQuery = WorkQuery.Builder
            .fromUniqueWorkNames(listOf(BirthdayUpdateWorker.UNIQUE_ONE_TIME_WORK_NAME))
            .addStates(
                listOf(
                    WorkInfo.State.SUCCEEDED,
                    WorkInfo.State.ENQUEUED,
                    WorkInfo.State.RUNNING,
                    WorkInfo.State.BLOCKED
                )
            )
            .build()

        return WorkManager.getInstance(context)
            .getWorkInfos(workQuery)
    }
}