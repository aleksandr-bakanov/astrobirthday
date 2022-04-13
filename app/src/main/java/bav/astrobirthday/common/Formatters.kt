package bav.astrobirthday.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Formatters {

    fun formatLocalDate(value: LocalDate): String {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(value)
    }

    fun parseIsoLocalDate(value: String): LocalDate {
        return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(value))
    }
}