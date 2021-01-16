package bav.astrobirthday.data.local

import androidx.room.TypeConverter
import bav.astrobirthday.common.DiscoveryMethod
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun discoveryMethodFromInt(value: Int?): DiscoveryMethod? {
        return value?.let { DiscoveryMethod.values[it] }
    }

    @TypeConverter
    fun discoveryMethodToInt(method: DiscoveryMethod?): Int? {
        return method?.ordinal
    }

    @TypeConverter
    fun stringToLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }
    }

    @TypeConverter
    fun localDateToString(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
