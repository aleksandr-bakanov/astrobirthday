package bav.astrobirthday.data.entities

import bav.astrobirthday.domain.model.Planet

object Config {

    val solarPlanetNames = setOf(
        "Mercury",
        "Venus",
        "Earth",
        "Mars",
        "Ceres",
        "Jupiter",
        "Saturn",
        "Uranus",
        "Neptune",
        "Pluto",
        "Haumea",
        "Makemake",
        "Eris",
        "Sedna"
    )

    val defaultFilters = mapOf<Column, PlanetFilter>(
        Column.DISTANCE to PlanetFilter.FilterFromTo(0f, 6780.0f),
        Column.PERIOD to PlanetFilter.FilterFromTo(0f, 7300000.0f),
        Column.PLANET_MASS to PlanetFilter.FilterFromTo(0f, 17669F),
        Column.PLANET_RADIUS to PlanetFilter.FilterFromTo(0f, 24F),
        Column.STAR_MASS to PlanetFilter.FilterFromTo(0f, 11F),
        Column.STAR_RADIUS to PlanetFilter.FilterFromTo(0f, 84F),
    )

    /*cursor.execute("""CREATE TABLE planets (
        pl_name TEXT PRIMARY KEY NOT NULL,
        id INT NOT NULL,

        hostname TEXT,
        sy_snum INT,
        sy_pnum INT,

        discoverymethod INT,
        disc_year INT,
        disc_facility TEXT,
        pl_refname TEXT,

        pl_orbper REAL,
        pl_orbsmax REAL,
        pl_rade REAL,
        pl_bmasse REAL,
        pl_orbeccen REAL,

        pl_eqt REAL,
        st_refname TEXT,
        st_spectype TEXT,
        st_teff REAL,
        st_rad REAL,
        st_mass REAL,
        sy_refname TEXT,
        sy_dist REAL,
        rowupdate TEXT,
        pl_pubdate TEXT,
        releasedate TEXT)
    """)*/

    /*solar_planets = [
    ('Mercury', 1, 'Sun', 1, 14, None, None, None, None, 87.969, 0.38709927, 0.3829, 0.055274, 0.20563593, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Venus', 2, 'Sun', 1, 14, None, None, None, None, 224.701, 0.723332, 0.9499, 0.815, 0.0068, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Earth', 3, 'Sun', 1, 14, None, None, None, None, 365.25, 1.00000261, 1.0, 1.0, 0.01671123, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Mars', 4, 'Sun', 1, 14, None, None, None, None, 779.94, 1.523662, 0.532, 0.107, 0.0933941, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Ceres', 5, 'Sun', 1, 14, None, 1801, None, None, 1680.5, 2.7653, 0.072914, 0.000157268, 0.07934, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Jupiter', 6, 'Sun', 1, 14, None, None, None, None, 4332.589, 5.204267, 10.97331, 317.8, 0.048775, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Saturn', 7, 'Sun', 1, 14, None, None, None, None, 10759.22, 9.554909, 10.97331659, 95.2, 0.055723219, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Uranus', 8, 'Sun', 1, 14, None, 1781, None, None, 30685.4, 19.22941195, 3.98085, 14.54, 0.044405586, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Neptune', 9, 'Sun', 1, 14, None, 1846, None, None, 60190.03, 30.10366151, 3.8646994, 17.147, 0.011214269, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Pluto', 10, 'Sun', 1, 14, None, 1930, None, None, 90553.02, 39.482117, 0.186517, 0.002, 0.2488273, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Haumea', 11, 'Sun', 1, 14, None, 2004, None, None, 103647.0, 43.28708, 0.12836647, 0.00066, 0.1920504, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Makemake', 12, 'Sun', 1, 14, None, 2005, None, None, 111867.0, 45.436301, 0.11625346, 0.0005, 0.16254481, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Eris', 13, 'Sun', 1, 14, None, 2005, None, None, 203830.0, 67.781, 0.182953687, 0.0027961, 0.44068, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Sedna', 14, 'Sun', 1, 14, None, 2003, None, None, 4404480.0, 541.429506, 0.1565252957, 0.000138968, 0.8590486, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ]*/


    val solarPlanets = listOf(
        Planet(
            planetName = "Mercury",
            starName = "Sun",
            systemStarNumber = 1,
            systemPlanetNumber = 14,
            planetOrbitalPeriod = 87.969,
            planetOrbitSemiMajorAxis = 0.38709927,
            planetRadiusEarth = 0.3829,
            planetBestMassEstimateEarth = 0.055274,
            planetOrbitEccentricity = 0.20563593,
            starSpectralType = "G2V",
            starEffectiveTemperature = 5772.0,
            starRadiusSolar = 1.0,
            starMassSolar = 1.0,
            systemDistance = 0.0
        )
    )

// pl_name id   hostname sy_snum sy_pnum   discoverymethod disc_year disc_facility pl_refname   pl_orbper pl_orbsmax pl_rade pl_bmasse pl_orbeccen   pl_eqt st_refname st_spectype st_teff st_rad st_mass sy_refname sy_dist

    /*solar_planets = [
    ('Venus', 2, 'Sun', 1, 14, None, None, None, None, 224.701, 0.723332, 0.9499, 0.815, 0.0068, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Earth', 3, 'Sun', 1, 14, None, None, None, None, 365.25, 1.00000261, 1.0, 1.0, 0.01671123, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Mars', 4, 'Sun', 1, 14, None, None, None, None, 779.94, 1.523662, 0.532, 0.107, 0.0933941, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Ceres', 5, 'Sun', 1, 14, None, 1801, None, None, 1680.5, 2.7653, 0.072914, 0.000157268, 0.07934, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Jupiter', 6, 'Sun', 1, 14, None, None, None, None, 4332.589, 5.204267, 10.97331, 317.8, 0.048775, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Saturn', 7, 'Sun', 1, 14, None, None, None, None, 10759.22, 9.554909, 10.97331659, 95.2, 0.055723219, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Uranus', 8, 'Sun', 1, 14, None, 1781, None, None, 30685.4, 19.22941195, 3.98085, 14.54, 0.044405586, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Neptune', 9, 'Sun', 1, 14, None, 1846, None, None, 60190.03, 30.10366151, 3.8646994, 17.147, 0.011214269, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Pluto', 10, 'Sun', 1, 14, None, 1930, None, None, 90553.02, 39.482117, 0.186517, 0.002, 0.2488273, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Haumea', 11, 'Sun', 1, 14, None, 2004, None, None, 103647.0, 43.28708, 0.12836647, 0.00066, 0.1920504, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Makemake', 12, 'Sun', 1, 14, None, 2005, None, None, 111867.0, 45.436301, 0.11625346, 0.0005, 0.16254481, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Eris', 13, 'Sun', 1, 14, None, 2005, None, None, 203830.0, 67.781, 0.182953687, 0.0027961, 0.44068, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ('Sedna', 14, 'Sun', 1, 14, None, 2003, None, None, 4404480.0, 541.429506, 0.1565252957, 0.000138968, 0.8590486, None, None, 'G2V', 5772.0, 1.0, 1.0, None, 0.0, None, None, None),
    ]*/
}