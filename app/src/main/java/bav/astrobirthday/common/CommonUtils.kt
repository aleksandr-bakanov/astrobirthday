package bav.astrobirthday.common

import android.content.Context
import androidx.core.os.ConfigurationCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommonUtils(context: Context) {

    private val dateFormat = DateTimeFormatter.ofPattern("d MMM yyyy", ConfigurationCompat.getLocales(context.resources.configuration).get(0))

    fun localDateToString(date: LocalDate): String {
        return dateFormat.format(date)
    }
}
