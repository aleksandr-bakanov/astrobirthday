package bav.astrobirthday.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import bav.astrobirthday.utils.enqueuePeriodicBirthdayUpdateWorker

class BirthdayUpdater(
    private val context: Context
) {
    fun updateBirthdays() {
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<BirthdayUpdateWorker>().build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
        enqueuePeriodicBirthdayUpdateWorker(context)
    }
}