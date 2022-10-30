package bav.astrobirthday.ui.common.opengl

import bav.astrobirthday.R
import kotlin.math.PI

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
                sphereRadius = 2f,
                sphereTextureResId = R.drawable.tex_solar_venus_atmosphere,
            )
        )
    ),
    "Earth" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 3f,
                sphereRadius = 2f,
                sphereTextureResId = R.drawable.tex_solar_earth,
            )
        ),
        satellites = mutableListOf(
            PlanetSystemNode(
                orbitRadius = 4f,
                angularVelocity = (PI / 180.0).toFloat(),
                planets = mutableListOf(
                    PlanetData(
                        axisRotationSpeed = 1.0f,
                        sphereRadius = 0.5f,
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
                sphereRadius = 2.5f,
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
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.3f,
                ringInnerRadius = 2.0f,
                ringOuterRadius = 7.5f,
                sphereTextureResId = R.drawable.tex_solar_saturn,
                ringTextureResId = R.drawable.tex_solar_saturn_ring_alpha
            )
        )
    ),
    "Uranus" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.2f,
                sphereTextureResId = R.drawable.tex_solar_uranus,
            )
        )
    ),
    "Neptune" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.2f,
                sphereTextureResId = R.drawable.tex_solar_neptune,
            )
        )
    ),
    "Pluto" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 1.2f,
                orbitAngle = 0.0f,
                angularVelocity = (PI / 180.0).toFloat(),
                sphereRadius = 0.8f,
                sphereTextureResId = R.drawable.tex_solar_pluto,
            ),
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 2f,
                orbitAngle = PI.toFloat(),
                angularVelocity = (PI / 180.0).toFloat(),
                sphereRadius = 0.4f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
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