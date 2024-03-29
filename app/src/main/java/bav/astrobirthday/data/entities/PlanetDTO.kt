package bav.astrobirthday.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bav.astrobirthday.common.DiscoveryMethod
import bav.astrobirthday.domain.model.Planet
import kotlinx.parcelize.Parcelize

/**
 * Data: https://exoplanetarchive.ipac.caltech.edu/cgi-bin/TblView/nph-tblView?app=ExoTbls&config=PS
 * Columns description: https://exoplanetarchive.ipac.caltech.edu/docs/API_PS_columns.html
 */
// TODO: сделать поля nullable, чтобы можно было их преобразовывать в n/a, если параметр неизвестен
const val PLANETS_TABLE = "planets"

@Entity(tableName = PLANETS_TABLE)
@Parcelize
data class PlanetDTO(
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
) : Parcelable

fun PlanetDTO.toDomain(): Planet {
    return Planet(
        planetName = pl_name,
        systemStarNumber = sy_snum,
        systemPlanetNumber = sy_pnum,
        discoveryMethod = discoverymethod,
        discoveryYear = disc_year,
        discoveryFacility = disc_facility,
        planetBestMassEstimateEarth = pl_bmasse,
        planetReference = pl_refname,
        planetOrbitalPeriod = pl_orbper,
        planetOrbitSemiMajorAxis = pl_orbsmax,
        planetRadiusEarth = pl_rade,
        planetOrbitEccentricity = pl_orbeccen,
        planetEquilibriumTemperature = pl_eqt,
        starName = hostname,
        starReference = st_refname,
        starSpectralType = st_spectype,
        starEffectiveTemperature = st_teff,
        starRadiusSolar = st_rad,
        starMassSolar = st_mass,
        systemReference = sy_refname,
        systemDistance = sy_dist
    )
}