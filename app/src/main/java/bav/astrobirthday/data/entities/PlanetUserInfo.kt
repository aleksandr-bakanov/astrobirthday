package bav.astrobirthday.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "planets_user_info")
data class PlanetUserInfo(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "is_favorite") val is_favorite: Boolean = false,
    @ColumnInfo(name = "age") val age: Double,
    @ColumnInfo(name = "birthday") val birthday: LocalDate
)
