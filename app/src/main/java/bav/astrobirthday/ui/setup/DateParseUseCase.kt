package bav.astrobirthday.ui.setup

import bav.astrobirthday.common.Formatters
import bav.astrobirthday.domain.exception.DateInFuture
import bav.astrobirthday.domain.exception.DateNotParsed
import bav.astrobirthday.domain.exception.YearExceedMinValue
import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateParseUseCase {

    companion object {
        private const val MIN_YEAR = -999999999
    }

    private val dateParseRegex = Regex("(-?\\d+)-(\\d{1,2})-(\\d{1,2})")

    fun parseDate(value: String): LocalDate {
        val groups = dateParseRegex.matchEntire(value)?.groups
        if (groups == null) {
            throw DateNotParsed()
        } else {
            try {
                val year = groups[1]?.value!!.toInt()
                if (year < MIN_YEAR) {
                    throw YearExceedMinValue()
                }
                val date = Formatters.parseIsoLocalDate(value)
                if (date.isAfter(LocalDate.now())) {
                    throw DateInFuture()
                }
                return date
            } catch (t: NumberFormatException) {
                throw DateNotParsed()
            }
        }
    }
}