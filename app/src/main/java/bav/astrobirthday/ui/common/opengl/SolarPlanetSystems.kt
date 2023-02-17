package bav.astrobirthday.ui.common.opengl

import bav.astrobirthday.R
import kotlin.math.PI

fun Float.degToRad(): Float = (this * PI / 180.0).toFloat()

val solarPlanetSystems = mapOf(
    "Mercury" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 2.0f,
                sphereRadius = 1f,
                sphereTextureResId = R.drawable.tex_solar_mercury,
            )
        )
    ),
    "Venus" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 0.2f,
                sphereRadius = 1.6f,
                sphereTextureResId = R.drawable.tex_solar_venus_atmosphere,
            )
        )
    ),
    "Earth" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.5f,
                sphereRadius = 1.6f,
                sphereTextureResId = R.drawable.tex_solar_earth_with_clouds,
            )
        ),
        satellites = mutableListOf(
            PlanetSystemNode(
                orbitRadius = 6f,
                angularVelocity = 1f.degToRad(),
                planets = mutableListOf(
                    PlanetData(
                        axisRotationSpeed = 1.0f,
                        sphereRadius = 0.3f,
                        sphereTextureResId = R.drawable.tex_solar_moon,
                    )
                )
            )
        )
    ),
    "Mars" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 1.2f,
                sphereTextureResId = R.drawable.tex_solar_mars,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.06f,
                sphereTextureResId = R.drawable.tex_rock_1,
                orbitRadius = 2.7f,
                orbitAngle = 0f,
                angularVelocity = 0.5f.degToRad()
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_rock_2,
                orbitRadius = 5f,
                orbitAngle = 180f.degToRad(),
                angularVelocity = 0.25f.degToRad()
            )
        )
    ),
    "Ceres" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 0.5f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_ceres,
            )
        )
    ),
    "Jupiter" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.0f,
                sphereTextureResId = R.drawable.tex_solar_jupiter,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 4f,
                orbitAngle = 0f,
                angularVelocity = (PI / 360.0).toFloat(),
                sphereRadius = 0.1f,
                sphereTextureResId = R.drawable.tex_solar_io,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 5f,
                orbitAngle = (PI / 2.0).toFloat(),
                angularVelocity = (PI / 720.0).toFloat(),
                sphereRadius = 0.08f,
                sphereTextureResId = R.drawable.tex_solar_europa,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 6f,
                orbitAngle = PI.toFloat(),
                angularVelocity = (PI / 1080.0).toFloat(),
                sphereRadius = 0.15f,
                sphereTextureResId = R.drawable.tex_solar_ganymede,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 7f,
                orbitAngle = (PI * 3.0 / 2.0).toFloat(),
                angularVelocity = (PI / 1440.0).toFloat(),
                sphereRadius = 0.14f,
                sphereTextureResId = R.drawable.tex_solar_callisto,
            )
        )
    ),
    "Saturn" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData( // Saturn
                axisRotationSpeed = 1.0f,
                sphereRadius = 1.8f,
                ringInnerRadius = 1.57f,
                ringOuterRadius = 5.85f,
                sphereTextureResId = R.drawable.tex_solar_saturn,
                ringTextureResId = R.drawable.tex_solar_saturn_ring_alpha
            ),
            PlanetData(
                // Mimas
                axisRotationSpeed = 1.0f,
                orbitRadius = 6f,
                orbitAngle = 0f,
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.02f,
                sphereTextureResId = R.drawable.tex_ice_1,
            ),
            PlanetData(
                // Enceladus
                axisRotationSpeed = 1.0f,
                orbitRadius = 7f,
                orbitAngle = 50f.degToRad(),
                angularVelocity = 0.5f.degToRad(),
                sphereRadius = 0.02f,
                sphereTextureResId = R.drawable.tex_ice_2,
            ),
            PlanetData(
                // Tethys
                axisRotationSpeed = 1.0f,
                orbitRadius = 8f,
                orbitAngle = 100f.degToRad(),
                angularVelocity = 0.25f.degToRad(),
                sphereRadius = 0.04f,
                sphereTextureResId = R.drawable.tex_ice_3,
            ),
            PlanetData(
                // Dione
                axisRotationSpeed = 1.0f,
                orbitRadius = 9f,
                orbitAngle = 150f.degToRad(),
                angularVelocity = 0.125f.degToRad(),
                sphereRadius = 0.04f,
                sphereTextureResId = R.drawable.tex_ice_4,
            ),
            PlanetData(
                // Rhea
                axisRotationSpeed = 1.0f,
                orbitRadius = 10f,
                orbitAngle = 200f.degToRad(),
                angularVelocity = 0.0625f.degToRad(),
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_ice_5,
            ),
            PlanetData(
                // Titan
                axisRotationSpeed = 1.0f,
                orbitRadius = 11f,
                orbitAngle = 250f.degToRad(),
                angularVelocity = 0.03125f.degToRad(),
                sphereRadius = 0.15f,
                sphereTextureResId = R.drawable.tex_venusian_11,
            ),
            PlanetData(
                // Iapetus
                axisRotationSpeed = 1.0f,
                orbitRadius = 12f,
                orbitAngle = 300f.degToRad(),
                angularVelocity = 0.015625f.degToRad(),
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_ice_7,
            )
        )
    ),
    "Uranus" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.0f,
                sphereTextureResId = R.drawable.tex_solar_uranus,
            )
        )
    ),
    "Neptune" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.0f,
                sphereTextureResId = R.drawable.tex_solar_neptune,
            )
        )
    ),
    "Pluto" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 1.1f,
                orbitAngle = 0.0f,
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.8f,
                sphereTextureResId = R.drawable.tex_solar_pluto,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 3f,
                orbitAngle = PI.toFloat(),
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.3f,
                sphereTextureResId = R.drawable.tex_rock_3,
            )
        )
    ),
    "Haumea" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_haumea,
            )
        )
    ),
    "Makemake" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
            )
        )
    ),
    "Eris" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_eris,
            )
        )
    ),
    "Sedna" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
            )
        )
    )
)