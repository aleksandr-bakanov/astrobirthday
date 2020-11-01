package bav.astrobirthday.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import bav.astrobirthday.common.DiscoveryMethod

/**
 * Data: https://exoplanetarchive.ipac.caltech.edu/cgi-bin/TblView/nph-tblView?app=ExoTbls&config=PS
 * Columns description: https://exoplanetarchive.ipac.caltech.edu/docs/API_PS_columns.html
 */
@Entity(tableName = "planets")
data class Planet(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    // NAMES
    // Planet Name. Planet name most commonly used in the literature
    @ColumnInfo(name = "pl_name") val pl_name: String,

    // Host Name . Stellar name most commonly used in the literature
    @ColumnInfo(name = "hostname") val hostname: String,

    // Planet Letter. Letter assigned to the planetary component of a planetary system
    @ColumnInfo(name = "pl_letter") val pl_letter: String,

    // HD ID. Name of the star as given by the Henry Draper Catalog
    @ColumnInfo(name = "hd_name") val hd_name: String,

    // HIP ID. Name of the star as given by the Hipparcos Catalog
    @ColumnInfo(name = "hip_name") val hip_name: String,

    // TIC ID. Name of the star as given by the TESS Input Catalog
    @ColumnInfo(name = "tic_id") val tic_id: String,

    // GAIA ID. Name of the star as given by the Gaia Catalog
    @ColumnInfo(name = "gaia_id") val gaia_id: String,

    // Default Parameter Set. Boolean flag indicating whether given set of planet parameters has been selected as default (1=yes, 0=no)
    @ColumnInfo(name = "default_flag") val default_flag: Boolean,

    // SYSTEM COMPOSITION
    // Number of Stars. Number of stars in the planetary system
    @ColumnInfo(name = "sy_snum") val sy_snum: Int,

    // Number of Planets. Number of planets in the planetary system
    @ColumnInfo(name = "sy_pnum") val sy_pnum: Int,

    // Number of Moons. Number of moons in the planetary system
    @ColumnInfo(name = "sy_mnum") val sy_mnum: Int,

    // Circumbinary Flag. Flag indicating whether the planet orbits a binary system (1=yes, 0=no)
    @ColumnInfo(name = "cb_flag") val cb_flag: Boolean,

    // PLANET DISCOVERY
    // Discovery Method. Method by which the planet was first identified
    @ColumnInfo(name = "discoverymethod") val discoverymethod: String,

    // Discovery Year. Year the planet was discovered
    @ColumnInfo(name = "disc_year") val disc_year: Int,

    // Discovery Reference. Reference name for discovery publication
    @ColumnInfo(name = "disc_refname") val disc_refname: String,

    // Discovery Publication Date. Publication Date of the planet discovery referee publication
    @ColumnInfo(name = "disc_pubdate") val disc_pubdate: String,

    // Discovery Locale. Location of observation of planet discovery (Ground or Space)
    @ColumnInfo(name = "disc_locale") val disc_locale: String,

    // Discovery Facility. Name of facility of planet discovery observations
    @ColumnInfo(name = "disc_facility") val disc_facility: String,

    // Discovery Telescope. Name of telescope of planet discovery observations
    @ColumnInfo(name = "disc_telescope") val disc_telescope: String,

    // Discovery Instrument. Name of instrument of planet discovery observations
    @ColumnInfo(name = "disc_instrument") val disc_instrument: String,

    // DETECTIONS
    // Detections by Radial Velocity Variations. Flag indicating if the planet host star exhibits radial velocity variations due to the planet (1=yes, 0=no)
    @ColumnInfo(name = "rv_flag") val rv_flag: Boolean,

    // Detected by Pulsar Timing Variations. Boolean flag indicating if the planet host star exhibits pulsar timing variations due to the planet (1=yes, 0=no)
    @ColumnInfo(name = "pul_flag") val pul_flag: Boolean,

    // Detected by Pulsation Timing Variations. Boolean flag indicating if the planet host star exhibits pulsation timing variations due to the planet (1=yes, 0=no)
    @ColumnInfo(name = "ptv_flag") val ptv_flag: Boolean,

    // Detected by Transits. Flag indicating if the planet transits its host star (1=yes, 0=no)
    @ColumnInfo(name = "tran_flag") val tran_flag: Boolean,

    // Detected by Astrometric Variations. Flag indicating if the planet host star exhibits astrometrical variations due to the planet (1=yes, 0=no)
    @ColumnInfo(name = "ast_flag") val ast_flag: Boolean,

    // Detected by Orbital Brightness Modulations. Flag indicating whether the planet exhibits orbital modulations on the phase curve (1=yes, 0=no)
    @ColumnInfo(name = "obm_flag") val obm_flag: Boolean,

    // Detected by Microlensing. Boolean flag indicating if the planetary system acted as a lens during an observed microlensing event (1=yes, 0=no)
    @ColumnInfo(name = "micro_flag") val micro_flag: Boolean,

    // Detected by Eclipse Timing Variations. Boolean flag indicating if the planet induces transit timing variations on the orbit of another another planet in the system (1=yes, 0=no)
    @ColumnInfo(name = "etv_flag") val etv_flag: Boolean,

    // Detected by Imaging. Flag indicating if the planet has been observed via imaging techniques (1=yes, 0=no)
    @ColumnInfo(name = "ima_flag") val ima_flag: Boolean,

    // Detection by Disk Kinematics. Boolean flag indicating if the presence of the planet was inferred due to its kinematic influence on the protoplanetary disk of its host star (1=yes, 0=no)
    @ColumnInfo(name = "dkin_flag") val dkin_flag: Boolean,

    // Solution Type. Disposition of planet according to given planet parameter set
    @ColumnInfo(name = "soltype") val soltype: String,

    // Controversial Flag. Flag indicating whether the confirmation status of a planet has been questioned in the published literature (1=yes, 0=no)
    @ColumnInfo(name = "pl_controv_flag") val pl_controv_flag: Boolean,

    // PLANET PARAMETERS
    // Planetary Parameter Reference. Reference of publication used for given planet parameter set
    @ColumnInfo(name = "pl_refname") val pl_refname: String,

    // Orbital Period [days]. Time the planet takes to make a complete orbit around the host star or system [days]
    @ColumnInfo(name = "pl_orbper") val pl_orbper: String,
    // Orbital Period Upper Unc. [days]
    @ColumnInfo(name = "pl_orbpererr1") val pl_orbpererr1: String,
    // Orbital Period Lower Unc. [days]
    @ColumnInfo(name = "pl_orbpererr2") val pl_orbpererr2: String,
    // Orbital Period Limit Flag
    @ColumnInfo(name = "pl_orbperlim") val pl_orbperlim: String,

    // Orbit Semi-Major Axis [au]). The longest radius of an elliptic orbit, or, for exoplanets detected via gravitational microlensing or direct imaging, the projected separation in the plane of the sky
    @ColumnInfo(name = "pl_orbsmax") val pl_orbsmax: String,
    // Orbit Semi-Major Axis Upper Unc. [au]
    @ColumnInfo(name = "pl_orbsmaxerr1") val pl_orbsmaxerr1: String,
    // Orbit Semi-Major Axis Lower Unc. [au]
    @ColumnInfo(name = "pl_orbsmaxerr2") val pl_orbsmaxerr2: String,
    // Orbit Semi-Major Axis Limit Flag
    @ColumnInfo(name = "pl_orbsmaxlim") val pl_orbsmaxlim: String,

    // Planet Radius [Earth Radius]. Length of a line segment from the center of the planet to its surface, measured in units of radius of the Earth
    @ColumnInfo(name = "pl_rade") val pl_rade: String,
    // Planet Radius Upper Unc. [Earth Radius]
    @ColumnInfo(name = "pl_radeerr1") val pl_radeerr1: String,
    // Planet Radius Lower Unc. [Earth Radius]
    @ColumnInfo(name = "pl_radeerr2") val pl_radeerr2: String,
    // Planet Radius Limit Flag
    @ColumnInfo(name = "pl_radelim") val pl_radelim: String,

    // Planet Radius [Jupiter Radius]. Length of a line segment from the center of the planet to its surface, measured in units of radius of Jupiter
    @ColumnInfo(name = "pl_radj") val pl_radj: String,
    // Planet Radius Upper Unc. [Jupiter Radius]
    @ColumnInfo(name = "pl_radjerr1") val pl_radjerr1: String,
    // Planet Radius Lower Unc. [Jupiter Radius]
    @ColumnInfo(name = "pl_radjerr2") val pl_radjerr2: String,
    // Planet Radius Limit Flag
    @ColumnInfo(name = "pl_radjlim") val pl_radjlim: String,

    // Planet Mass [Earth Mass]. Amount of matter contained in the planet, measured in units of masses of the Earth
    @ColumnInfo(name = "pl_masse") val pl_masse: String,
    // Planet Mass [Earth Mass] Upper Unc.
    @ColumnInfo(name = "pl_masseerr1") val pl_masseerr1: String,
    // Planet Mass [Earth Mass] Lower Unc.
    @ColumnInfo(name = "pl_masseerr2") val pl_masseerr2: String,
    // Planet Mass [Earth Mass] Limit Flag
    @ColumnInfo(name = "pl_masselim") val pl_masselim: String,

    // Planet Mass [Jupiter Mass]. Amount of matter contained in the planet, measured in units of masses of Jupiter
    @ColumnInfo(name = "pl_massj") val pl_massj: String,
    // Planet Mass [Jupiter Mass] Upper Unc.
    @ColumnInfo(name = "pl_massjerr1") val pl_massjerr1: String,
    // Planet Mass [Jupiter Mass] Lower Unc.
    @ColumnInfo(name = "pl_massjerr2") val pl_massjerr2: String,
    // Planet Mass [Jupiter Mass] Limit Flag
    @ColumnInfo(name = "pl_massjlim") val pl_massjlim: String,

    // Planet Mass*sin(i) [Earth Mass]. Minimum mass of a planet as measured by radial velocity, measured in units of masses of the Earth
    @ColumnInfo(name = "pl_msinie") val pl_msinie: String,
    // Planet Mass*sin(i) [Earth Mass] Upper Unc.
    @ColumnInfo(name = "pl_msinieerr1") val pl_msinieerr1: String,
    // Planet Mass*sin(i) [Earth Mass] Lower Unc.
    @ColumnInfo(name = "pl_msinieerr2") val pl_msinieerr2: String,
    // Planet Mass*sin(i) [Earth Mass] Limit Flag
    @ColumnInfo(name = "pl_msinielim") val pl_msinielim: String,

    // Planet Mass*sin(i) [Jupiter Mass]. Minimum mass of a planet as measured by radial velocity, measured in units of masses of Jupiter
    @ColumnInfo(name = "pl_msinij") val pl_msinij: String,
    // Planet Mass*sin(i) [Jupiter Mass] Upper Unc.
    @ColumnInfo(name = "pl_msinijerr1") val pl_msinijerr1: String,
    // Planet Mass*sin(i) [Jupiter Mass] Lower Unc.
    @ColumnInfo(name = "pl_msinijerr2") val pl_msinijerr2: String,
    // Planet Mass*sin(i) [Jupiter Mass] Limit Flag
    @ColumnInfo(name = "pl_msinijlim") val pl_msinijlim: String,

    // Planet Mass*sin(i)/sin(i) [Earth Mass]. A calculated quantity indicating the quotient of the lower limit of the measured planet mass, denoted as its mass times the sine of its inclination, and the sine of its inclination, measured in units of the mass of the Earth. This is specified for references in which the inclination is provided as well as the planet mass limit, but the true mass is not reported.
    @ColumnInfo(name = "pl_cmasse") val pl_cmasse: String,
    // Planet Mass*sin(i)/sin(i) [Earth Mass] Upper Unc.
    @ColumnInfo(name = "pl_cmasseerr1") val pl_cmasseerr1: String,
    // Planet Mass*sin(i)/sin(i) [Earth Mass] Lower Unc.
    @ColumnInfo(name = "pl_cmasseerr2") val pl_cmasseerr2: String,
    // Planet Mass*sin(i)/sin(i) [Earth Mass] Limit Flag
    @ColumnInfo(name = "pl_cmasselim") val pl_cmasselim: String,

    // Planet Mass*sin(i)/sin(i) [Jupiter Mass]. A calculated quantity indicating the quotient of the lower limit of the measured planet mass, denoted as its mass times the sine of its inclination, and the sine of its inclination, measured in units of the mass of Jupiter. This is specified for references in which the inclination is provided as well as the planet mass limit, but the true mass is not reported.
    @ColumnInfo(name = "pl_cmassj") val pl_cmassj: String,
    // Planet Mass*sin(i)/sin(i) [Jupiter Mass] Upper Unc.
    @ColumnInfo(name = "pl_cmassjerr1") val pl_cmassjerr1: String,
    // Planet Mass*sin(i)/sin(i) [Jupiter Mass] Lower Unc.
    @ColumnInfo(name = "pl_cmassjerr2") val pl_cmassjerr2: String,
    // Planet Mass*sin(i)/sin(i) [Jupiter Mass] Limit Flag
    @ColumnInfo(name = "pl_cmassjlim") val pl_cmassjlim: String,

    // Planet Mass or Mass*sin(i) [Earth Mass]. Best planet mass estimate available, in order of preference: Mass, M*sin(i)/sin(i), or M*sin(i), depending on availability, and measured in Earth masses
    @ColumnInfo(name = "pl_bmasse") val pl_bmasse: String,
    // Planet Mass or Mass*sin(i) [Earth Mass] Upper Unc.
    @ColumnInfo(name = "pl_bmasseerr1") val pl_bmasseerr1: String,
    // Planet Mass or Mass*sin(i) [Earth Mass] Lower Unc.
    @ColumnInfo(name = "pl_bmasseerr2") val pl_bmasseerr2: String,
    // Planet Mass or Mass*sin(i) [Earth Mass] Limit Flag
    @ColumnInfo(name = "pl_bmasselim") val pl_bmasselim: String,

    // Planet Mass or Mass*sin(i) [Jupiter Mass]. Best planet mass estimate available, in order of preference: Mass, M*sin(i)/sin(i), or M*sin(i), depending on availability, and measured in Jupiter masses
    @ColumnInfo(name = "pl_bmassj") val pl_bmassj: String,
    // Planet Mass or Mass*sin(i) [Jupiter Mass] Upper Unc.
    @ColumnInfo(name = "pl_bmassjerr1") val pl_bmassjerr1: String,
    // Planet Mass or Mass*sin(i) [Jupiter Mass] Lower Unc.
    @ColumnInfo(name = "pl_bmassjerr2") val pl_bmassjerr2: String,
    // Planet Mass or Mass*sin(i) [Jupiter Mass] Limit Flag
    @ColumnInfo(name = "pl_bmassjlim") val pl_bmassjlim: String,

    // Planet Mass or Mass*sin(i) Provenance. Provenance of the measurement of the best mass. Options are: Mass, M*sin(i)/sin(i), and M*sini
    @ColumnInfo(name = "pl_bmassprov") val pl_bmassprov: String,

    // Planet Density [g/cm**3]. Amount of mass per unit of volume of the planet
    @ColumnInfo(name = "pl_dens") val pl_dens: String,
    // Planet Density Upper Unc. [g/cm**3]
    @ColumnInfo(name = "pl_denserr1") val pl_denserr1: String,
    // Planet Density Lower Unc. [g/cm**3]
    @ColumnInfo(name = "pl_denserr2") val pl_denserr2: String,
    // Planet Density Limit Flag
    @ColumnInfo(name = "pl_denslim") val pl_denslim: String,

    // Eccentricity. Amount by which the orbit of the planet deviates from a perfect circle
    @ColumnInfo(name = "pl_orbeccen") val pl_orbeccen: String,
    // Eccentricity Upper Unc.
    @ColumnInfo(name = "pl_orbeccenerr1") val pl_orbeccenerr1: String,
    // Eccentricity Lower Unc.
    @ColumnInfo(name = "pl_orbeccenerr2") val pl_orbeccenerr2: String,
    // Eccentricity Limit Flag
    @ColumnInfo(name = "pl_orbeccenlim") val pl_orbeccenlim: String,

    // Insolation Flux [Earth Flux]. Insolation flux is another way to give the equilibrium temperature. It's given in units relative to those measured for the Earth from the Sun.
    @ColumnInfo(name = "pl_insol") val pl_insol: String,
    // Insolation Flux Upper Unc. [Earth Flux]
    @ColumnInfo(name = "pl_insolerr1") val pl_insolerr1: String,
    // Insolation Flux Lower Unc. [Earth Flux]
    @ColumnInfo(name = "pl_insolerr2") val pl_insolerr2: String,
    // Insolation Flux Limit Flag
    @ColumnInfo(name = "pl_insollim") val pl_insollim: String,

    // Equilibrium Temperature [K]. The equilibrium temperature of the planet as modeled by a black body heated only by its host star, or for directly imaged planets, the effective temperature of the planet required to match the measured luminosity if the planet were a black body
    @ColumnInfo(name = "pl_eqt") val pl_eqt: String,
    // Equilibrium Temperature Upper Unc. [K]
    @ColumnInfo(name = "pl_eqterr1") val pl_eqterr1: String,
    // Equilibrium Temperature Lower Unc. [K]
    @ColumnInfo(name = "pl_eqterr2") val pl_eqterr2: String,
    // Equilibrium Temperature Limit Flag
    @ColumnInfo(name = "pl_eqtlim") val pl_eqtlim: String,

    // Inclination [deg]. Angle of the plane of the orbit relative to the plane perpendicular to the line-of-sight from Earth to the object
    @ColumnInfo(name = "pl_orbincl") val pl_orbincl: String,
    // Inclination Upper Unc. [deg]
    @ColumnInfo(name = "pl_orbinclerr1") val pl_orbinclerr1: String,
    // Inclination Lower Unc. [deg]
    @ColumnInfo(name = "pl_orbinclerr2") val pl_orbinclerr2: String,
    // Inclination Limit Flag
    @ColumnInfo(name = "pl_orbincllim") val pl_orbincllim: String,

    // Transit Midpoint [days]. The time given by the average of the time the planet begins to cross the stellar limb and the time the planet finishes crossing the stellar limb.
    @ColumnInfo(name = "pl_tranmid") val pl_tranmid: String,
    // Transit Midpoint Upper Unc. [days]
    @ColumnInfo(name = "pl_tranmiderr1") val pl_tranmiderr1: String,
    // Transit Midpoint Lower Unc. [days]
    @ColumnInfo(name = "pl_tranmiderr2") val pl_tranmiderr2: String,
    // Transit Midpoint Limit Flag
    @ColumnInfo(name = "pl_tranmidlim") val pl_tranmidlim: String,

    // Time Reference Frame and Standard. Time system basis for temporal and orbital parameters
    @ColumnInfo(name = "pl_tsystemref") val pl_tsystemref: String,

    // Data show Transit Timing Variations. Flag indicating if the planet orbit exhibits transit timing variations from another planet in the system (1=yes, 0=no).
    // Note: Non-transiting planets discovered via the transit timing variations of another planet in the system will not have their TTV flag set, since they do not themselves demonstrate TTVs.
    @ColumnInfo(name = "ttv_flag") val ttv_flag: String,

    // Impact Parameter. The sky-projected distance between the center of the stellar disc and the center of the planet disc at conjunction, normalized by the stellar radius
    @ColumnInfo(name = "pl_imppar") val pl_imppar: String,
    // Impact Parameter Upper Unc.
    @ColumnInfo(name = "pl_impparerr1") val pl_impparerr1: String,
    // Impact Parameter Lower Unc.
    @ColumnInfo(name = "pl_impparerr2") val pl_impparerr2: String,
    // Impact Parameter Limit Flag
    @ColumnInfo(name = "pl_impparlim") val pl_impparlim: String,

    // Transit Depth [%]. The size of the relative flux decrement caused by the orbiting body transiting in front of the star
    @ColumnInfo(name = "pl_trandep") val pl_trandep: String,
    // Transit Depth Upper Unc. [%]
    @ColumnInfo(name = "pl_trandeperr1") val pl_trandeperr1: String,
    // Transit Depth Lower Unc. [%]
    @ColumnInfo(name = "pl_trandeperr2") val pl_trandeperr2: String,
    // Transit Depth Limit Flag
    @ColumnInfo(name = "pl_trandeplim") val pl_trandeplim: String,

    // Transit Duration [hours]. The length of time from the moment the planet begins to cross the stellar limb to the moment the planet finishes crossing the stellar limb
    @ColumnInfo(name = "pl_trandur") val pl_trandur: String,
    // Transit Duration Upper Unc. [hours]
    @ColumnInfo(name = "pl_trandurerr1") val pl_trandurerr1: String,
    // Transit Duration Lower Unc. [hours]
    @ColumnInfo(name = "pl_trandurerr2") val pl_trandurerr2: String,
    // Transit Duration Limit Flag
    @ColumnInfo(name = "pl_trandurlim") val pl_trandurlim: String,

    // Ratio of Semi-Major Axis to Stellar. Radius The distance between the planet and the star at mid-transit divided by the stellar radius. For the case of zero orbital eccentricity, the distance at mid-transit is the semi-major axis of the planetary orbit.
    @ColumnInfo(name = "pl_ratdor") val pl_ratdor: String,
    // Ratio of Semi-Major Axis to Stellar Radius Upper Unc.
    @ColumnInfo(name = "pl_ratdorerr1") val pl_ratdorerr1: String,
    // Ratio of Semi-Major Axis to Stellar Radius Lower Unc.
    @ColumnInfo(name = "pl_ratdorerr2") val pl_ratdorerr2: String,
    // Ratio of Semi-Major Axis to Stellar Radius Limit Flag
    @ColumnInfo(name = "pl_ratdorlim") val pl_ratdorlim: String,

    // Ratio of Planet to Stellar Radius. The planet radius divided by the stellar radius
    @ColumnInfo(name = "pl_ratror") val pl_ratror: String,
    // Ratio of Planet to Stellar Radius Upper Unc.
    @ColumnInfo(name = "pl_ratrorerr1") val pl_ratrorerr1: String,
    // Ratio of Planet to Stellar Radius Lower Unc.
    @ColumnInfo(name = "pl_ratrorerr2") val pl_ratrorerr2: String,
    // Ratio of Planet to Stellar Radius Limit Flag
    @ColumnInfo(name = "pl_ratrorlim") val pl_ratrorlim: String,

    // Occultation Depth [%]. Depth of occultation of secondary eclipse
    @ColumnInfo(name = "pl_occdep") val pl_occdep: String,
    // Occultation Depth Upper Unc. [%]
    @ColumnInfo(name = "pl_occdeperr1") val pl_occdeperr1: String,
    // Occultation Depth Lower Unc. [%]
    @ColumnInfo(name = "pl_occdeperr2") val pl_occdeperr2: String,
    // Occultation Depth Limit Flag
    @ColumnInfo(name = "pl_occdeplim") val pl_occdeplim: String,

    // Epoch of Periastron [days]. The time of the planet's periastron passage
    @ColumnInfo(name = "pl_orbtper") val pl_orbtper: String,
    // Epoch of Periastron Upper Unc. [days]
    @ColumnInfo(name = "pl_orbtpererr1") val pl_orbtpererr1: String,
    // Epoch of Periastron Lower Unc. [days]
    @ColumnInfo(name = "pl_orbtpererr2") val pl_orbtpererr2: String,
    // Epoch of Periastron Limit Flag
    @ColumnInfo(name = "pl_orbtperlim") val pl_orbtperlim: String,

    // Argument of Periastron [deg]. The angle between the planet's ascending node and its periastron
    @ColumnInfo(name = "pl_orblper") val pl_orblper: String,
    // Argument of Periastron Upper Unc. [deg]
    @ColumnInfo(name = "pl_orblpererr1") val pl_orblpererr1: String,
    // Argument of Periastron Lower Unc. [deg]
    @ColumnInfo(name = "pl_orblpererr2") val pl_orblpererr2: String,
    // Argument of Periastron Limit Flag
    @ColumnInfo(name = "pl_orblperlim") val pl_orblperlim: String,

    // Radial Velocity Amplitude [m/s]. Half the peak-to-peak amplitude of variability in the stellar radial velocity
    @ColumnInfo(name = "pl_rvamp") val pl_rvamp: String,
    // Radial Velocity Amplitude Upper Unc. [m/s]
    @ColumnInfo(name = "pl_rvamperr1") val pl_rvamperr1: String,
    // Radial Velocity Amplitude Lower Unc. [m/s]
    @ColumnInfo(name = "pl_rvamperr2") val pl_rvamperr2: String,
    // Radial Velocity Amplitude Limit Flag
    @ColumnInfo(name = "pl_rvamplim") val pl_rvamplim: String,

    // Projected Obliquity [deg]. The angle between the angular momentum vector of the rotation of the host star and the angular momentum vector of the orbit of the planet, projected into the plane of the sky. Depending on the choice of coordinate system, projected obliquity is represented in the literature as either lambda (λ) or beta (β), where λ is defined as the negative of β (i.e., λ = -β). Since λ is reported more often than β, all values of β have been converted to λ.
    @ColumnInfo(name = "pl_projobliq") val pl_projobliq: String,
    // Projected Obliquity Upper Unc. [deg]
    @ColumnInfo(name = "pl_projobliqerr1") val pl_projobliqerr1: String,
    // Projected Obliquity Lower Unc. [deg]
    @ColumnInfo(name = "pl_projobliqerr2") val pl_projobliqerr2: String,
    // Projected Obliquity Limit Flag
    @ColumnInfo(name = "pl_projobliqlim") val pl_projobliqlim: String,

    // True Obliquity [deg]. The angle between the angular momentum vector of the rotation of the host star and the angular momentum vector of the orbit of the planet
    @ColumnInfo(name = "pl_trueobliq") val pl_trueobliq: String,
    // True Obliquity Upper Unc. [deg]
    @ColumnInfo(name = "pl_trueobliqerr1") val pl_trueobliqerr1: String,
    // True Obliquity Lower Unc. [deg]
    @ColumnInfo(name = "pl_trueobliqerr2") val pl_trueobliqerr2: String,
    // True Obliquity Limit Flag
    @ColumnInfo(name = "pl_trueobliqlim") val pl_trueobliqlim: String,

    // STELLAR DATA
    // Stellar Parameter Reference. Reference of publication used for given stellar parameter set
    @ColumnInfo(name = "st_refname") val st_refname: String,

    // Spectral Type. Classification of the star based on their spectral characteristics following the Morgan-Keenan system
    @ColumnInfo(name = "st_spectype") val st_spectype: String,

    // Stellar Effective Temperature [K]. Temperature of the star as modeled by a black body emitting the same total amount of electromagnetic radiation
    @ColumnInfo(name = "st_teff") val st_teff: String,
    // Stellar Effective Temperature Upper Unc. [K]
    @ColumnInfo(name = "st_tefferr1") val st_tefferr1: String,
    // Stellar Effective Temperature Lower Unc. [K]
    @ColumnInfo(name = "st_tefferr2") val st_tefferr2: String,
    // Stellar Effective Temperature Limit Flag
    @ColumnInfo(name = "st_tefflim") val st_tefflim: String,

    // Stellar Radius [Solar Radius]. Length of a line segment from the center of the star to its surface, measured in units of radius of the Sun
    @ColumnInfo(name = "st_rad") val st_rad: String,
    // Stellar Radius Upper Unc. [Solar Radius]
    @ColumnInfo(name = "st_raderr1") val st_raderr1: String,
    // Stellar Radius Lower Unc. [Solar Radius]
    @ColumnInfo(name = "st_raderr2") val st_raderr2: String,
    // Stellar Radius Limit Flag
    @ColumnInfo(name = "st_radlim") val st_radlim: String,

    // Stellar Mass [Solar mass]. Amount of matter contained in the star, measured in units of masses of the Sun
    @ColumnInfo(name = "st_mass") val st_mass: String,
    // Stellar Mass Upper Unc. [Solar mass]
    @ColumnInfo(name = "st_masserr1") val st_masserr1: String,
    // Stellar Mass Lower Unc. [Solar mass]
    @ColumnInfo(name = "st_masserr2") val st_masserr2: String,
    // Stellar Mass Limit Flag
    @ColumnInfo(name = "st_masslim") val st_masslim: String,

    // Stellar Metallicity [dex]. Measurement of the metal content of the photosphere of the star as compared to the hydrogen content
    @ColumnInfo(name = "st_met") val st_met: String,
    // Stellar Metallicity Upper Unc. [dex]
    @ColumnInfo(name = "st_meterr1") val st_meterr1: String,
    // Stellar Metallicity Lower Unc. [dex]
    @ColumnInfo(name = "st_meterr2") val st_meterr2: String,
    // Stellar Metallicity Limit Flag
    @ColumnInfo(name = "st_metlim") val st_metlim: String,

    // Stellar Metallicity Ratio. Ratio for the Metallicity Value ([Fe/H] denotes iron abundance, [M/H] refers to a general metal content)
    @ColumnInfo(name = "st_metratio") val st_metratio: String,

    // Stellar Luminosity [log(Solar)]. Amount of energy emitted by a star per unit time, measured in units of solar luminosities
    @ColumnInfo(name = "st_lum") val st_lum: String,
    // Stellar Luminosity Upper Unc. [log(Solar)]
    @ColumnInfo(name = "st_lumerr1") val st_lumerr1: String,
    // Stellar Luminosity Lower Unc. [log(Solar)]
    @ColumnInfo(name = "st_lumerr2") val st_lumerr2: String,
    // Stellar Luminosity Limit Flag
    @ColumnInfo(name = "st_lumlim") val st_lumlim: String,

    // Stellar Surface Gravity [log10(cm/s**2)]. Gravitational acceleration experienced at the stellar surface
    @ColumnInfo(name = "st_logg") val st_logg: String,
    // Stellar Surface Gravity Upper Unc. [log10(cm/s**2)]
    @ColumnInfo(name = "st_loggerr1") val st_loggerr1: String,
    // Stellar Surface Gravity Lower Unc. [log10(cm/s**2)]
    @ColumnInfo(name = "st_loggerr2") val st_loggerr2: String,
    // Stellar Surface Gravity Limit Flag
    @ColumnInfo(name = "st_logglim") val st_logglim: String,

    // Stellar Age [Gyr]. The age of the host star
    @ColumnInfo(name = "st_age") val st_age: String,
    // Stellar Age Upper Unc. [Gyr]
    @ColumnInfo(name = "st_ageerr1") val st_ageerr1: String,
    // Stellar Age Lower Unc. [Gyr]
    @ColumnInfo(name = "st_ageerr2") val st_ageerr2: String,
    // Stellar Age Limit Flag
    @ColumnInfo(name = "st_agelim") val st_agelim: String,

    // Stellar Density [g/cm**3]. Amount of mass per unit of volume of the star
    @ColumnInfo(name = "st_dens") val st_dens: String,
    // Stellar Density Upper Unc. [g/cm**3]
    @ColumnInfo(name = "st_denserr1") val st_denserr1: String,
    // Stellar Density Lower Unc. [g/cm**3]
    @ColumnInfo(name = "st_denserr2") val st_denserr2: String,
    // Stellar Density Limit Flag
    @ColumnInfo(name = "st_denslim") val st_denslim: String,

    // Stellar Rotational Velocity [km/s]. Rotational velocity at the equator of the star multiplied by the sine of the inclination
    @ColumnInfo(name = "st_vsin") val st_vsin: String,
    // Stellar Rotational Velocity [km/s] Upper Unc.
    @ColumnInfo(name = "st_vsinerr1") val st_vsinerr1: String,
    // Stellar Rotational Velocity [km/s] Lower Unc.
    @ColumnInfo(name = "st_vsinerr2") val st_vsinerr2: String,
    // Stellar Rotational Velocity Limit Flag
    @ColumnInfo(name = "st_vsinlim") val st_vsinlim: String,

    // Stellar Rotational Period [days]. The time required for the planet host star to complete one rotation, assuming it is a solid body
    @ColumnInfo(name = "st_rotp") val st_rotp: String,
    // Stellar Rotational Period [days] Upper Unc.
    @ColumnInfo(name = "st_rotperr1") val st_rotperr1: String,
    // Stellar Rotational Period [days] Lower Unc.
    @ColumnInfo(name = "st_rotperr2") val st_rotperr2: String,
    // Stellar Rotational Period Limit Flag
    @ColumnInfo(name = "st_rotplim") val st_rotplim: String,

    // Systemic Radial Velocity [km/s]. Velocity of the star in the direction of the line of sight
    @ColumnInfo(name = "st_radv") val st_radv: String,
    // Systemic Radial Velocity Upper Unc. [km/s]
    @ColumnInfo(name = "st_radverr1") val st_radverr1: String,
    // Systemic Radial Velocity Lower Unc. [km/s]
    @ColumnInfo(name = "st_radverr2") val st_radverr2: String,
    // Systemic Radial Velocity Limit Flag
    @ColumnInfo(name = "st_radvlim") val st_radvlim: String,

    // SYSTEM DATA
    // System Parameter Reference. Reference of publication used for given system parameter set
    @ColumnInfo(name = "sy_refname") val sy_refname: String,

    // POSITION (SYSTEM DATA SUBSET)
    // RA [sexagesimal]. Right Ascension of the planetary system in sexagesimal format
    @ColumnInfo(name = "rastr") val rastr: String,

    // RA [decimal]. Right Ascension of the planetary system in decimal degrees
    @ColumnInfo(name = "ra") val ra: String,

    // Dec [sexagesimal]. Declination of the planetary system in sexagesimal notation
    @ColumnInfo(name = "decstr") val decstr: String,

    // Dec [decimal]. Declination of the planetary system in decimal degrees
    @ColumnInfo(name = "dec") val dec: String,

    // RA Upper Unc
    @ColumnInfo(name = "raerr1") val raerr1: String,
    // RA Lower Unc
    @ColumnInfo(name = "raerr2") val raerr2: String,

    // Dec Upper Unc
    @ColumnInfo(name = "decerr1") val decerr1: String,
    // Dec Lower Unc
    @ColumnInfo(name = "decerr2") val decerr2: String,

    // Galactic Latitude [deg]. Galactic latitude of the planetary system in units of decimal degrees
    @ColumnInfo(name = "glat") val glat: String,
    // Galactic Latitude [deg] Upper Unc
    @ColumnInfo(name = "glaterr1") val glaterr1: String,
    // Galactic Latitude [deg] Lower Unc
    @ColumnInfo(name = "glaterr2") val glaterr2: String,

    // Galactic Longitude [deg]. Galactic longitude of the planetary system in units of decimal degrees
    @ColumnInfo(name = "glon") val glon: String,
    // Galactic Longitude [deg] Upper Unc
    @ColumnInfo(name = "glonerr1") val glonerr1: String,
    // Galactic Longitude [deg] Lower Unc
    @ColumnInfo(name = "glonerr2") val glonerr2: String,

    // Ecliptic Latitude [deg]. Ecliptic latitude of the planetary system in units of decimal degrees
    @ColumnInfo(name = "elat") val elat: String,
    // Ecliptic Latitude [deg] Upper Unc
    @ColumnInfo(name = "elaterr1") val elaterr1: String,
    // Ecliptic Latitude [deg] Lower Unc
    @ColumnInfo(name = "elaterr2") val elaterr2: String,

    // Ecliptic Longitude [deg]. Ecliptic longitude of the planetary system in units of decimal degrees
    @ColumnInfo(name = "elon") val elon: String,
    // Ecliptic Longitude [deg] Upper Unc
    @ColumnInfo(name = "elonerr1") val elonerr1: String,
    // Ecliptic Longitude [deg] Lower Unc
    @ColumnInfo(name = "elonerr2") val elonerr2: String,

    // SYSTEM DATA
    // Total Proper Motion [mas/yr]. Angular change in position over time as seen from the center of mass of the Solar System
    @ColumnInfo(name = "sy_pm") val sy_pm: String,
    // Total Proper Motion Upper Unc [mas/yr]
    @ColumnInfo(name = "sy_pmerr1") val sy_pmerr1: String,
    // Total Proper Motion Lower Unc [mas/yr]
    @ColumnInfo(name = "sy_pmerr2") val sy_pmerr2: String,

    // Proper Motion (RA) [mas/yr]. Angular change in right ascension over time as seen from the center of mass of the Solar System
    @ColumnInfo(name = "sy_pmra") val sy_pmra: String,
    // Proper Motion (RA) [mas/yr] Upper Unc
    @ColumnInfo(name = "sy_pmraerr1") val sy_pmraerr1: String,
    // Proper Motion (RA) [mas/yr] Lower Unc
    @ColumnInfo(name = "sy_pmraerr2") val sy_pmraerr2: String,

    // Proper Motion (Dec) [mas/yr]. Angular change in declination over time as seen from the center of mass of the Solar System
    @ColumnInfo(name = "sy_pmdec") val sy_pmdec: String,
    // Proper Motion (Dec) [mas/yr] Upper Unc
    @ColumnInfo(name = "sy_pmdecerr1") val sy_pmdecerr1: String,
    // Proper Motion (Dec) [mas/yr] Lower Unc
    @ColumnInfo(name = "sy_pmdecerr2") val sy_pmdecerr2: String,

    // Distance [pc]. Distance to the planetary system in units of parsecs
    @ColumnInfo(name = "sy_dist") val sy_dist: String,
    // Distance [pc] Upper Unc
    @ColumnInfo(name = "sy_disterr1") val sy_disterr1: String,
    // Distance [pc] Lower Unc
    @ColumnInfo(name = "sy_disterr2") val sy_disterr2: String,

    // Parallax [mas]. Difference in the angular position of a star as measured at two opposite positions within the Earth's orbit
    @ColumnInfo(name = "sy_plx") val sy_plx: String,
    // Parallax [mas] Upper Unc
    @ColumnInfo(name = "sy_plxerr1") val sy_plxerr1: String,
    // Parallax [mas] Lower Unc
    @ColumnInfo(name = "sy_plxerr2") val sy_plxerr2: String,

    // PHOTOMETRY (SYSTEM DATA SUBSET)
    // B (Johnson) Magnitude. Brightness of the host star as measured using the B (Johnson) band in units of magnitudes
    @ColumnInfo(name = "sy_bmag") val sy_bmag: String,
    // B (Johnson) Magnitude Upper Unc
    @ColumnInfo(name = "sy_bmagerr1") val sy_bmagerr1: String,
    // B (Johnson) Magnitude Lower Unc
    @ColumnInfo(name = "sy_bmagerr2") val sy_bmagerr2: String,

    // V (Johnson) Magnitude. Brightness of the host star as measured using the V (Johnson) band in units of magnitudes
    @ColumnInfo(name = "sy_vmag") val sy_vmag: String,
    // V (Johnson) Magnitude Upper Unc
    @ColumnInfo(name = "sy_vmagerr1") val sy_vmagerr1: String,
    // V (Johnson) Magnitude Lower Unc
    @ColumnInfo(name = "sy_vmagerr2") val sy_vmagerr2: String,

    // J (2MASS) Magnitude. Brightness of the host star as measured using the J (2MASS) band in units of magnitudes
    @ColumnInfo(name = "sy_jmag") val sy_jmag: String,
    // J (2MASS) Magnitude Upper Unc
    @ColumnInfo(name = "sy_jmagerr1") val sy_jmagerr1: String,
    // J (2MASS) Magnitude Lower Unc
    @ColumnInfo(name = "sy_jmagerr2") val sy_jmagerr2: String,

    // H (2MASS) Magnitude. Brightness of the host star as measured using the H (2MASS) band in units of magnitudes
    @ColumnInfo(name = "sy_hmag") val sy_hmag: String,
    // H (2MASS) Magnitude Upper Unc
    @ColumnInfo(name = "sy_hmagerr1") val sy_hmagerr1: String,
    // H (2MASS) Magnitude Lower Unc
    @ColumnInfo(name = "sy_hmagerr2") val sy_hmagerr2: String,

    // Ks (2MASS) Magnitude. Brightness of the host star as measured using the K (2MASS) band in units of magnitudes
    @ColumnInfo(name = "sy_kmag") val sy_kmag: String,
    // Ks (2MASS) Magnitude Upper Unc
    @ColumnInfo(name = "sy_kmagerr1") val sy_kmagerr1: String,
    // Ks (2MASS) Magnitude Lower Unc
    @ColumnInfo(name = "sy_kmagerr2") val sy_kmagerr2: String,

    // u (Sloan) Magnitude. Brightness of the host star as measured using the Sloan Digital Sky Survey (SDSS) u band, in units of magnitudes
    @ColumnInfo(name = "sy_umag") val sy_umag: String,
    // u (Sloan) Magnitude Upper Unc
    @ColumnInfo(name = "sy_umagerr1") val sy_umagerr1: String,
    // u (Sloan) Magnitude Lower Unc
    @ColumnInfo(name = "sy_umagerr2") val sy_umagerr2: String,

    // g (Sloan) Magnitude. Brightness of the host star as measured using the Sloan Digital Sky Survey (SDSS) g band, in units of magnitudes
    @ColumnInfo(name = "sy_gmag") val sy_gmag: String,
    // g (Sloan) Magnitude Upper Unc
    @ColumnInfo(name = "sy_gmagerr1") val sy_gmagerr1: String,
    // g (Sloan) Magnitude Lower Unc
    @ColumnInfo(name = "sy_gmagerr2") val sy_gmagerr2: String,

    // r (Sloan) Magnitude. Brightness of the host star as measured using the Sloan Digital Sky Survey (SDSS) r band, in units of magnitudes
    @ColumnInfo(name = "sy_rmag") val sy_rmag: String,
    // r (Sloan) Magnitude Upper Unc
    @ColumnInfo(name = "sy_rmagerr1") val sy_rmagerr1: String,
    // r (Sloan) Magnitude Lower Unc
    @ColumnInfo(name = "sy_rmagerr2") val sy_rmagerr2: String,

    // i (Sloan) Magnitude. Brightness of the host star as measured using the Sloan Digital Sky Survey (SDSS) i band, in units of magnitudes
    @ColumnInfo(name = "sy_imag") val sy_imag: String,
    // i (Sloan) Magnitude Upper Unc
    @ColumnInfo(name = "sy_imagerr1") val sy_imagerr1: String,
    // i (Sloan) Magnitude Lower Unc
    @ColumnInfo(name = "sy_imagerr2") val sy_imagerr2: String,

    // z (Sloan) Magnitude. Brightness of the host star as measured using the Sloan Digital Sky Survey (SDSS) z band, in units of magnitudes
    @ColumnInfo(name = "sy_zmag") val sy_zmag: String,
    // z (Sloan) Magnitude Upper Unc
    @ColumnInfo(name = "sy_zmagerr1") val sy_zmagerr1: String,
    // z (Sloan) Magnitude Lower Unc
    @ColumnInfo(name = "sy_zmagerr2") val sy_zmagerr2: String,

    // W1 (WISE) Magnitude. Brightness of the host star as measured using the 3.4um (WISE) band in units of magnitudes.
    @ColumnInfo(name = "sy_w1mag") val sy_w1mag: String,
    // W1 (WISE) Magnitude Upper Unc
    @ColumnInfo(name = "sy_w1magerr1") val sy_w1magerr1: String,
    // W1 (WISE) Magnitude Lower Unc
    @ColumnInfo(name = "sy_w1magerr2") val sy_w1magerr2: String,

    // W2 (WISE) Magnitude. Brightness of the host star as measured using the 4.6um (WISE) band in units of magnitudes
    @ColumnInfo(name = "sy_w2mag") val sy_w2mag: String,
    // W2 (WISE) Magnitude Upper Unc
    @ColumnInfo(name = "sy_w2magerr1") val sy_w2magerr1: String,
    // W2 (WISE) Magnitude Lower Unc
    @ColumnInfo(name = "sy_w2magerr2") val sy_w2magerr2: String,

    // W3 (WISE) Magnitude. Brightness of the host star as measured using the 12.um (WISE) band in units of magnitudes
    @ColumnInfo(name = "sy_w3mag") val sy_w3mag: String,
    // W3 (WISE) Magnitude Upper Unc
    @ColumnInfo(name = "sy_w3magerr1") val sy_w3magerr1: String,
    // W3 (WISE) Magnitude Lower Unc
    @ColumnInfo(name = "sy_w3magerr2") val sy_w3magerr2: String,

    // W4 (WISE) Magnitude. Brightness of the host star as measured using the 22.um (WISE) band in units of magnitudes
    @ColumnInfo(name = "sy_w4mag") val sy_w4mag: String,
    // W4 (WISE) Magnitude Upper Unc
    @ColumnInfo(name = "sy_w4magerr1") val sy_w4magerr1: String,
    // W4 (WISE) Magnitude Lower Unc
    @ColumnInfo(name = "sy_w4magerr2") val sy_w4magerr2: String,

    // Gaia Magnitude. Brightness of the host star as measuring using the Gaia band in units of magnitudes. Objects matched to Gaia using the Hipparcos or 2MASS IDs provided in Gaia DR2.
    @ColumnInfo(name = "sy_gaiamag") val sy_gaiamag: String,
    // Gaia Magnitude Upper Unc
    @ColumnInfo(name = "sy_gaiamagerr1") val sy_gaiamagerr1: String,
    // Gaia Magnitude Lower Unc
    @ColumnInfo(name = "sy_gaiamagerr2") val sy_gaiamagerr2: String,

    // I (Cousins) Magnitude. Brightness of the host star as measured using the I (Cousins) band in units of magnitudes.
    @ColumnInfo(name = "sy_icmag") val sy_icmag: String,
    // I (Cousins) Magnitude Upper Unc
    @ColumnInfo(name = "sy_icmagerr1") val sy_icmagerr1: String,
    // I (Cousins) Magnitude Lower Unc
    @ColumnInfo(name = "sy_icmagerr2") val sy_icmagerr2: String,

    // TESS Magnitude. Brightness of the host star as measured using the TESS bandpass, in units of magnitudes
    @ColumnInfo(name = "sy_tmag") val sy_tmag: String,
    // TESS Magnitude Upper Unc
    @ColumnInfo(name = "sy_tmagerr1") val sy_tmagerr1: String,
    // TESS Magnitude Lower Unc
    @ColumnInfo(name = "sy_tmagerr2") val sy_tmagerr2: String,

    // Kepler Magnitude. Brightness of the host star as measured using the Kepler bandpass, in units of magnitudes
    @ColumnInfo(name = "sy_kepmag") val sy_kepmag: String,
    // Kepler Magnitude Upper Unc
    @ColumnInfo(name = "sy_kepmagerr1") val sy_kepmagerr1: String,
    // Kepler Magnitude Lower Unc
    @ColumnInfo(name = "sy_kepmagerr2") val sy_kepmagerr2: String,

    // Date of Last Update. Date of last update of the planet parameters
    @ColumnInfo(name = "rowupdate") val rowupdate: String,

    // Planetary Parameter Reference Publication Date. Date of the publication of the given planet parameter set
    @ColumnInfo(name = "pl_pubdate") val pl_pubdate: String,

    // Release Date. Date that the given planet parameter set was publicly released by the NASA Exoplanet Archive
    @ColumnInfo(name = "releasedate") val releasedate: String,

    // Number of Notes. Number of Notes associated with the planet. View all notes in the Confirmed Planet Overview page.
    @ColumnInfo(name = "pl_nnotes") val pl_nnotes: String,

    // Number of Photometry Time Series. Number of photometric time series records, including planet transit light curves, general transit light curves, and amateur light curves.
    @ColumnInfo(name = "st_nphot") val st_nphot: String,

    // Number of Radial Velocity Time Series. Number of literature radial velocity curves available for this star in the NASA Exoplanet Archive.
    @ColumnInfo(name = "st_nrvc") val st_nrvc: String,

    // Number of Stellar Spectra Measurements. Number of literature of spectra available for this star in the NASA Exoplanet Archive
    @ColumnInfo(name = "st_nspec") val st_nspec: String,

    // Number of Emission Spectroscopy Measurements
    @ColumnInfo(name = "pl_nespec") val pl_nespec: String,

    // Number of Transmission Spectroscopy Measurements. Number of literature emission spectrum measurements for this star in the NASA Exoplanet Archive
    @ColumnInfo(name = "pl_ntranspec") val pl_ntranspec: String,


)
