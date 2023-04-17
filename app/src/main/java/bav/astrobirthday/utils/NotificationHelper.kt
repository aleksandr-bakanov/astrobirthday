package bav.astrobirthday.utils

import android.content.Context
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(
    private val context: Context
) {
    fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}