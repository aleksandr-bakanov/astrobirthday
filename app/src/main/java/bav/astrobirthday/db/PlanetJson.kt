package bav.astrobirthday.db

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanetJson(
    // Planet Name. Planet name most commonly used in the literature
    val pl_name: String,

    // Host Name . Stellar name most commonly used in the literature
    val hostname: String,

    // Orbital Period [days]. Time the planet takes to make a complete orbit around the host star or system [days]
    val pl_orbper: Double
)
