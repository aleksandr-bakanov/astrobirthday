package bav.astrobirthday.data.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    "SELECT id, pl_name, pl_orbper, is_favorite " +
            "FROM planets " +
            "LEFT JOIN planets_user_info " +
            "ON pl_name = name"
)
data class PlanetSyncView(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "pl_name") val pl_name: String,
    @ColumnInfo(name = "pl_orbper") val pl_orbper: Double? = null,
    @ColumnInfo(name = "is_favorite") val is_favorite: Boolean? = null
)