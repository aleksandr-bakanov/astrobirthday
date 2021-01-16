package bav.astrobirthday.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bav.astrobirthday.common.DiscoveryMethod

/**
 * Data: https://exoplanetarchive.ipac.caltech.edu/cgi-bin/TblView/nph-tblView?app=ExoTbls&config=PS
 * Columns description: https://exoplanetarchive.ipac.caltech.edu/docs/API_PS_columns.html
 */
// TODO: сделать поля nullable, чтобы можно было их преобразовывать в n/a, если параметр неизвестен
@Entity(tableName = "planets")
data class Planet(
    @PrimaryKey val pl_name: String,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "hostname") val hostname: String? = null,
    @ColumnInfo(name = "sy_snum") val sy_snum: Int? = null,
    @ColumnInfo(name = "sy_pnum") val sy_pnum: Int? = null,
    @ColumnInfo(name = "discoverymethod") val discoverymethod: DiscoveryMethod? = null,
    @ColumnInfo(name = "disc_year") val disc_year: Int? = null,
    @ColumnInfo(name = "disc_facility") val disc_facility: String? = null,
    @ColumnInfo(name = "pl_refname") val pl_refname: String? = null,
    @ColumnInfo(name = "pl_orbper") val pl_orbper: Double? = null,
    @ColumnInfo(name = "pl_orbsmax") val pl_orbsmax: Double? = null,
    @ColumnInfo(name = "pl_rade") val pl_rade: Double? = null,
    @ColumnInfo(name = "pl_bmasse") val pl_bmasse: Double? = null,
    @ColumnInfo(name = "pl_orbeccen") val pl_orbeccen: Double? = null,
    @ColumnInfo(name = "pl_eqt") val pl_eqt: Double? = null,
    @ColumnInfo(name = "st_refname") val st_refname: String? = null,
    @ColumnInfo(name = "st_spectype") val st_spectype: String? = null,
    @ColumnInfo(name = "st_teff") val st_teff: Double? = null,
    @ColumnInfo(name = "st_rad") val st_rad: Double? = null,
    @ColumnInfo(name = "st_mass") val st_mass: Double? = null,
    @ColumnInfo(name = "sy_refname") val sy_refname: String? = null,
    @ColumnInfo(name = "sy_dist") val sy_dist: Double? = null,
    @ColumnInfo(name = "rowupdate") val rowupdate: String? = null,
    @ColumnInfo(name = "pl_pubdate") val pl_pubdate: String? = null,
    @ColumnInfo(name = "releasedate") val releasedate: String? = null
)
