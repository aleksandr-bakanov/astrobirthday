package bav.astrobirthday.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

const val PLANET_USER_INFO_TABLE = "planets_user_info"

val PLANET_USER_INFO_COLUMNS = arrayOf(
    "name",
    "is_favorite",
    "age",
    "birthday"
)

@Entity(tableName = PLANET_USER_INFO_TABLE)
data class PlanetUserInfo(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "is_favorite") val is_favorite: Boolean = false,
    @ColumnInfo(name = "age") val age: Double?,
    @ColumnInfo(name = "birthday") val birthday: LocalDate?
)
