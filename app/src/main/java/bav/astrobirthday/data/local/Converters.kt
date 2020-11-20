package bav.astrobirthday.data.local

import androidx.room.TypeConverter
import bav.astrobirthday.common.DiscoveryMethod

class Converters {
    @TypeConverter
    fun discoveryMethodFromInt(value: Int): DiscoveryMethod {
        return DiscoveryMethod.values[value]
    }

    @TypeConverter
    fun discoveryMethodToInt(method: DiscoveryMethod): Int {
        return method.ordinal
    }
}
