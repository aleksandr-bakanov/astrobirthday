package bav.astrobirthday.data.entities

object Config {

    val solarPlanets = setOf(
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
}