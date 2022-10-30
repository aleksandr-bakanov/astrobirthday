package bav.astrobirthday.ui.common.opengl

import bav.astrobirthday.R
import kotlin.math.PI

val solarPlanetSystems = mapOf(
    "Mercury" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 2f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_mercury,
                ringTextureResId = 0
            )
        )
    ),
    "Venus" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 3f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_venus_atmosphere,
                ringTextureResId = 0
            )
        )
    ),
    "Earth" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 3f,
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
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 2f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_mars,
                ringTextureResId = 0
            )
        )
    ),
    "Ceres" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 1f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_ceres,
                ringTextureResId = 0
            )
        )
    ),
    "Jupiter" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 4f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_jupiter,
                ringTextureResId = 0
            )
        )
    ),
    "Saturn" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 3f,
                ringInnerRadius = 3.2f,
                ringOuterRadius = 5f,
                sphereTextureResId = R.drawable.tex_solar_saturn,
                ringTextureResId = R.drawable.tex_solar_saturn_ring_alpha
            )
        )
    ),
    "Uranus" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 4f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_uranus,
                ringTextureResId = 0
            )
        )
    ),
    "Neptune" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 4f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_neptune,
                ringTextureResId = 0
            )
        )
    ),
    "Pluto" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 1f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_pluto,
                ringTextureResId = 0
            )
        )
    ),
    "Haumea" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 1f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_haumea,
                ringTextureResId = 0
            )
        )
    ),
    "Makemake" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 1f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
                ringTextureResId = 0
            )
        )
    ),
    "Eris" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 1f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_eris,
                ringTextureResId = 0
            )
        )
    ),
    "Sedna" to PlanetSystemNode(
        planets = mutableListOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 0f,
                orbitAngle = 0.0f,
                angularVelocity = 0f,
                sphereRadius = 1f,
                ringInnerRadius = 0f,
                ringOuterRadius = 0f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
                ringTextureResId = 0
            )
        )
    )
)