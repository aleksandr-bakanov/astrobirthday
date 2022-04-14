package bav.astrobirthday.ui.setup

import bav.astrobirthday.domain.exception.DateInFuture
import bav.astrobirthday.domain.exception.DateNotParsed
import bav.astrobirthday.domain.exception.YearExceedMinValue
import java.time.LocalDate

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
                val month = groups[2]?.value!!.toInt()
                val day = groups[3]?.value!!.toInt()
                if (year < MIN_YEAR) {
                    throw YearExceedMinValue()
                }
                val date = LocalDate.of(year, month, day)
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