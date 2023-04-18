package bav.astrobirthday.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import bav.astrobirthday.R
import bav.astrobirthday.domain.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class NotificationHelper(
    private val userRepository: UserRepository,
    private val context: Context
) {
    fun areNotificationsAllowedBySystem(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun isNotificationsEnabled(
        userSetting: Boolean?
    ): Boolean {
        val systemSetting = areNotificationsAllowedBySystem()
        return when {
            userSetting == null -> systemSetting
            !systemSetting -> false
            else -> userSetting
        }
    }

    fun scheduleNotification(planetNames: List<String>) {
        val userSetting = runBlocking { userRepository.notificationsEnabledFlow.first() }
        if (isNotificationsEnabled(userSetting = userSetting) && planetNames.isNotEmpty()) {
            createNotificationChannel()
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_notification)
                .setContentTitle(context.resources.getString(R.string.notification_title))
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(context.resources.getString(
                        R.string.notification_content,
                        planetNames.joinToString { it }
                    )))
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)

            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.notification_channel_name)
            val descriptionText =
                context.resources.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "astro_birthday_solar_and_favorite"
        const val NOTIFICATION_ID = 9973
    }
}