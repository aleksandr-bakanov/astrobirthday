package bav.astrobirthday.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bav.astrobirthday.common.DiscoveryMethod
import com.squareup.moshi.JsonClass

/**
 * Data: https://exoplanetarchive.ipac.caltech.edu/cgi-bin/TblView/nph-tblView?app=ExoTbls&config=PS
 * Columns description: https://exoplanetarchive.ipac.caltech.edu/docs/API_PS_columns.html
 */
@Entity(tableName = "planets")
@JsonClass(generateAdapter = true)
data class Planet(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    // Planet Name. Planet name most commonly used in the literature
    @ColumnInfo(name = "pl_name") val pl_name: String,

    // Host Name . Stellar name most commonly used in the literature
    @ColumnInfo(name = "hostname") val hostname: String,

    // Orbital Period [days]. Time the planet takes to make a complete orbit around the host star or system [days]
    @ColumnInfo(name = "pl_orbper") val pl_orbper: Double
)
