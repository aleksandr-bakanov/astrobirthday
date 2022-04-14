package bav.astrobirthday.ui.setup

import bav.astrobirthday.common.Formatters
import java.time.LocalDate

class DateParseUseCase {

    fun stringToDate(value: String): LocalDate {
        return Formatters.stringToDate(value)
    }

    fun dateToString(date: LocalDate): String {
        return Formatters.dateToString(date)
    }
}
