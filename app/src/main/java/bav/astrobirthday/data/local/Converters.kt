package bav.astrobirthday.data.local

import androidx.room.TypeConverter
import bav.astrobirthday.common.DiscoveryMethod

class Converters {
    @TypeConverter
    fun discoveryMethodFromInt(value: Int?): DiscoveryMethod? {
        return value?.let { DiscoveryMethod.values[it] }
    }
    @TypeConverter
    fun discoveryMethodToInt(method: DiscoveryMethod?): Int? {
        return method?.let { it.ordinal }
    }
}
