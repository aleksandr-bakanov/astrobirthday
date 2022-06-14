package bav.astrobirthday.data.entities

import bav.astrobirthday.domain.model.Planet

object Config {

    val defaultFilters = mapOf<Column, PlanetFilter>(
        Column.DISTANCE to PlanetFilter.FilterFromTo(0f, 6780.0f),
        Column.PERIOD to PlanetFilter.FilterFromTo(0f, 7300000.0f),
        Column.PLANET_MASS to PlanetFilter.FilterFromTo(0f, 17669F),
        Column.PLANET_RADIUS to PlanetFilter.FilterFromTo(0f, 24F),
        Column.STAR_MASS to PlanetFilter.FilterFromTo(0f, 11F),
        Column.STAR_RADIUS to PlanetFilter.FilterFromTo(0f, 84F),
    )

    private const val SUN_NAME = "Sun"
    private const val SUN_STAR_NUMBER = 1
    private const val SUN_PLANET_NUMBER = 14
    private const val SUN_SPECTRAL_TYPE = "G2V"
    private const val SUN_EFFECTIVE_TEMPERATURE = 5772.0
    private const val SUN_RADIUS_SOLAR = 1.0
    private const val SUN_MASS_SOLAR = 1.0
    private const val SUN_DISTANCE = 0.0

    val solarPlanets = listOf(
        Planet(
            planetName = "Mercury",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            planetOrbitalPeriod = 87.969,
            planetOrbitSemiMajorAxis = 0.38709927,
            planetRadiusEarth = 0.3829,
            planetBestMassEstimateEarth = 0.055274,
            planetOrbitEccentricity = 0.20563593,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Venus",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            planetOrbitalPeriod = 224.701,
            planetOrbitSemiMajorAxis = 0.723332,
            planetRadiusEarth = 0.9499,
            planetBestMassEstimateEarth = 0.815,
            planetOrbitEccentricity = 0.0068,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Earth",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            planetOrbitalPeriod = 365.25,
            planetOrbitSemiMajorAxis = 1.00000261,
            planetRadiusEarth = 1.0,
            planetBestMassEstimateEarth = 1.0,
            planetOrbitEccentricity = 0.01671123,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Mars",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            planetOrbitalPeriod = 779.94,
            planetOrbitSemiMajorAxis = 1.523662,
            planetRadiusEarth = 0.532,
            planetBestMassEstimateEarth = 0.107,
            planetOrbitEccentricity = 0.0933941,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Ceres",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 1801,
            planetOrbitalPeriod = 1680.5,
            planetOrbitSemiMajorAxis = 2.7653,
            planetRadiusEarth = 0.072914,
            planetBestMassEstimateEarth = 0.000157268,
            planetOrbitEccentricity = 0.07934,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Jupiter",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            planetOrbitalPeriod = 4332.589,
            planetOrbitSemiMajorAxis = 5.204267,
            planetRadiusEarth = 10.97331,
            planetBestMassEstimateEarth = 317.8,
            planetOrbitEccentricity = 0.048775,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Saturn",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            planetOrbitalPeriod = 10759.22,
            planetOrbitSemiMajorAxis = 9.554909,
            planetRadiusEarth = 9.1402,
            planetBestMassEstimateEarth = 95.159,
            planetOrbitEccentricity = 0.055723219,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Uranus",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 1781,
            planetOrbitalPeriod = 30685.4,
            planetOrbitSemiMajorAxis = 19.22941195,
            planetRadiusEarth = 3.98085,
            planetBestMassEstimateEarth = 14.54,
            planetOrbitEccentricity = 0.044405586,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Neptune",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 1846,
            planetOrbitalPeriod = 60190.03,
            planetOrbitSemiMajorAxis = 30.10366151,
            planetRadiusEarth = 3.8646994,
            planetBestMassEstimateEarth = 17.147,
            planetOrbitEccentricity = 0.011214269,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Pluto",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 1930,
            planetOrbitalPeriod = 90553.02,
            planetOrbitSemiMajorAxis = 39.482117,
            planetRadiusEarth = 0.186517,
            planetBestMassEstimateEarth = 0.002,
            planetOrbitEccentricity = 0.2488273,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Haumea",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 2004,
            planetOrbitalPeriod = 103647.0,
            planetOrbitSemiMajorAxis = 43.28708,
            planetRadiusEarth = 0.12836647,
            planetBestMassEstimateEarth = 0.00066,
            planetOrbitEccentricity = 0.1920504,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Makemake",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 2005,
            planetOrbitalPeriod = 111867.0,
            planetOrbitSemiMajorAxis = 45.436301,
            planetRadiusEarth = 0.11625346,
            planetBestMassEstimateEarth = 0.0005,
            planetOrbitEccentricity = 0.16254481,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Eris",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 2005,
            planetOrbitalPeriod = 203830.0,
            planetOrbitSemiMajorAxis = 67.781,
            planetRadiusEarth = 0.182953687,
            planetBestMassEstimateEarth = 0.0027961,
            planetOrbitEccentricity = 0.44068,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        ),
        Planet(
            planetName = "Sedna",
            starName = SUN_NAME,
            systemStarNumber = SUN_STAR_NUMBER,
            systemPlanetNumber = SUN_PLANET_NUMBER,
            discoveryYear = 2003,
            planetOrbitalPeriod = 4404480.0,
            planetOrbitSemiMajorAxis = 541.429506,
            planetRadiusEarth = 0.1565252957,
            planetBestMassEstimateEarth = 0.000138968,
            planetOrbitEccentricity = 0.8590486,
            starSpectralType = SUN_SPECTRAL_TYPE,
            starEffectiveTemperature = SUN_EFFECTIVE_TEMPERATURE,
            starRadiusSolar = SUN_RADIUS_SOLAR,
            starMassSolar = SUN_MASS_SOLAR,
            systemDistance = SUN_DISTANCE
        )
    )

    val solarPlanetNames = solarPlanets.map { it.planetName }.toSet()
}