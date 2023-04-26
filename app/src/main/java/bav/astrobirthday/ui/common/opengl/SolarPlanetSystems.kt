package bav.astrobirthday.ui.common.opengl

import bav.astrobirthday.R
import kotlin.math.PI

fun Float.degToRad(): Float = (this * PI / 180.0).toFloat()

val solarPlanetSystems = mapOf(
    "Mercury" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisRotationSpeed = 2.0f,
                sphereRadius = 1f,
                sphereTextureResId = R.drawable.tex_solar_mercury,
            )
        )
    ),
    "Venus" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisTiltY = 177.3f,
                axisRotationSpeed = 0.2f,
                sphereRadius = 1.6f,
                sphereTextureResId = R.drawable.tex_solar_venus_atmosphere,
            )
        )
    ),
    "Earth" to PlanetSystemNode(
        planets = listOf(
            // Earth
            PlanetData(
                axisTiltY = 23.44f,
                axisRotationSpeed = 1.5f,
                sphereRadius = 1.6f,
                sphereTextureResId = R.drawable.tex_solar_earth_with_clouds,
            ),
            // Moon
            PlanetData(
                axisTiltY = 0.0f,
                orbitTiltY = 5.14f,
                orbitRadius = 6f,
                angularVelocity = 1f.degToRad(),
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.3f,
                sphereTextureResId = R.drawable.tex_solar_moon,
            )
        )
    ),
    "Mars" to PlanetSystemNode(
        planets = listOf(
            // Mars
            PlanetData(
                axisTiltY = 25.2f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 1.2f,
                sphereTextureResId = R.drawable.tex_solar_mars,
            ),
            // Phobos
            PlanetData(
                orbitTiltY = 25.2f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.06f,
                sphereTextureResId = R.drawable.tex_rock_satellite_1,
                orbitRadius = 2.7f,
                orbitAngle = 0f,
                angularVelocity = 0.5f.degToRad()
            ),
            // Deimos
            PlanetData(
                orbitTiltY = 25.2f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_rock_satellite_2,
                orbitRadius = 5f,
                orbitAngle = 180f.degToRad(),
                angularVelocity = 0.25f.degToRad()
            )
        )
    ),
    "Ceres" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisRotationSpeed = 0.5f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_ceres,
            )
        )
    ),
    "Jupiter" to PlanetSystemNode(
        planets = listOf(
            // Jupiter
            PlanetData(
                axisTiltY = 3.1f,
                axisTiltX = 3.1f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.0f,
                sphereTextureResId = R.drawable.tex_solar_jupiter,
            ),
            // Io
            PlanetData(
                axisTiltY = 3.1f,
                orbitTiltY = 3.1f,
                axisTiltX = 3.1f,
                orbitTiltX = 3.1f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 4f,
                orbitAngle = 0f,
                angularVelocity = (PI / 360.0).toFloat(),
                sphereRadius = 0.1f,
                sphereTextureResId = R.drawable.tex_solar_io,
            ),
            // Europa
            PlanetData(
                axisTiltY = 3.1f,
                orbitTiltY = 3.1f,
                axisTiltX = 3.1f,
                orbitTiltX = 3.1f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 5f,
                orbitAngle = (PI / 2.0).toFloat(),
                angularVelocity = (PI / 720.0).toFloat(),
                sphereRadius = 0.08f,
                sphereTextureResId = R.drawable.tex_solar_europa,
            ),
            // Ganymede
            PlanetData(
                axisTiltY = 3.1f,
                orbitTiltY = 3.1f,
                axisTiltX = 3.1f,
                orbitTiltX = 3.1f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 6f,
                orbitAngle = PI.toFloat(),
                angularVelocity = (PI / 1080.0).toFloat(),
                sphereRadius = 0.15f,
                sphereTextureResId = R.drawable.tex_solar_ganymede,
            ),
            // Callisto
            PlanetData(
                axisTiltY = 3.1f,
                orbitTiltY = 3.1f,
                axisTiltX = 3.1f,
                orbitTiltX = 3.1f,
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
        planets = listOf(
            // Saturn
            PlanetData(
                axisTiltY = 26.7f,
                axisTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 1.6f,
                ringInnerRadius = 1.4f,
                ringOuterRadius = 5.2f,
                sphereTextureResId = R.drawable.tex_solar_saturn,
                ringTextureResId = R.drawable.tex_solar_saturn_ring_alpha
            ),
            // Mimas
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 6f,
                orbitAngle = 0f,
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.02f,
                sphereTextureResId = R.drawable.tex_ice_satellite_1,
            ),
            // Enceladus
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 7f,
                orbitAngle = 50f.degToRad(),
                angularVelocity = 0.5f.degToRad(),
                sphereRadius = 0.02f,
                sphereTextureResId = R.drawable.tex_ice_satellite_2,
            ),
            // Tethys
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 8f,
                orbitAngle = 100f.degToRad(),
                angularVelocity = 0.25f.degToRad(),
                sphereRadius = 0.04f,
                sphereTextureResId = R.drawable.tex_ice_satellite_3,
            ),
            // Dione
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 9f,
                orbitAngle = 150f.degToRad(),
                angularVelocity = 0.125f.degToRad(),
                sphereRadius = 0.04f,
                sphereTextureResId = R.drawable.tex_ice_satellite_1,
            ),
            // Rhea
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 10f,
                orbitAngle = 200f.degToRad(),
                angularVelocity = 0.0625f.degToRad(),
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_ice_satellite_2,
            ),
            // Titan
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 11f,
                orbitAngle = 250f.degToRad(),
                angularVelocity = 0.03125f.degToRad(),
                sphereRadius = 0.15f,
                sphereTextureResId = R.drawable.tex_venusian_2,
            ),
            // Iapetus
            PlanetData(
                orbitTiltY = 26.7f,
                orbitTiltX = 26.7f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 12f,
                orbitAngle = 300f.degToRad(),
                angularVelocity = 0.015625f.degToRad(),
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_ice_satellite_3,
            )
        )
    ),
    "Uranus" to PlanetSystemNode(
        planets = listOf(
            // Uranus
            PlanetData(
                axisTiltY = 97.8f,
                axisTiltX = 45.0f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.0f,
                ringInnerRadius = 1.8f,
                ringOuterRadius = 4f,
                sphereTextureResId = R.drawable.tex_solar_uranus,
                ringTextureResId = R.drawable.tex_solar_uranus_ring_alpha
            ),
            // Miranda
            PlanetData(
                orbitTiltY = 97.8f,
                orbitTiltX = 45.0f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 5f,
                orbitAngle = 0f,
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.05f,
                sphereTextureResId = R.drawable.tex_ice_satellite_1,
            ),
            // Ariel
            PlanetData(
                orbitTiltY = 97.8f,
                orbitTiltX = 45.0f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 6f,
                orbitAngle = 72f.degToRad(),
                angularVelocity = 0.5f.degToRad(),
                sphereRadius = 0.1f,
                sphereTextureResId = R.drawable.tex_ice_satellite_2,
            ),
            // Umbriel
            PlanetData(
                orbitTiltY = 97.8f,
                orbitTiltX = 45.0f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 7f,
                orbitAngle = 144f.degToRad(),
                angularVelocity = 0.125f.degToRad(),
                sphereRadius = 0.1f,
                sphereTextureResId = R.drawable.tex_ice_satellite_3,
            ),
            // Titania
            PlanetData(
                orbitTiltY = 97.8f,
                orbitTiltX = 45.0f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 8f,
                orbitAngle = 216f.degToRad(),
                angularVelocity = 0.0625f.degToRad(),
                sphereRadius = 0.10f,
                sphereTextureResId = R.drawable.tex_ice_satellite_1,
            ),
            // Oberon
            PlanetData(
                orbitTiltY = 97.8f,
                orbitTiltX = 45.0f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 9f,
                orbitAngle = 288f.degToRad(),
                angularVelocity = 0.03125f.degToRad(),
                sphereRadius = 0.10f,
                sphereTextureResId = R.drawable.tex_ice_satellite_2,
            )
        )
    ),
    "Neptune" to PlanetSystemNode(
        planets = listOf(
            // Neptune
            PlanetData(
                axisTiltY = 28.3f,
                axisTiltX = 12.0f,
                axisRotationSpeed = 1.0f,
                sphereRadius = 2.0f,
                ringInnerRadius = 2.1f,
                ringOuterRadius = 5f,
                sphereTextureResId = R.drawable.tex_solar_neptune,
                ringTextureResId = R.drawable.tex_solar_neptune_ring_alpha
            ),
            // Triton
            PlanetData(
                orbitTiltY = 5.3f,
                axisTiltY = 5.3f,
                orbitTiltX = 12.0f,
                axisRotationSpeed = 1.0f,
                orbitRadius = 7f,
                orbitAngle = 0f,
                angularVelocity = (-1f).degToRad(),
                sphereRadius = 0.15f,
                sphereTextureResId = R.drawable.tex_ice_satellite_2,
            )
        )
    ),
    "Pluto" to PlanetSystemNode(
        planets = listOf(
            // Pluto
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 1.1f,
                orbitAngle = 0.0f,
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.8f,
                sphereTextureResId = R.drawable.tex_solar_pluto,
            ),
            // Charon
            PlanetData(
                axisRotationSpeed = 1.0f,
                orbitRadius = 3f,
                orbitAngle = PI.toFloat(),
                angularVelocity = 1f.degToRad(),
                sphereRadius = 0.3f,
                sphereTextureResId = R.drawable.tex_rock_1,
            )
        )
    ),
    "Haumea" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_haumea,
            )
        )
    ),
    "Makemake" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
            )
        )
    ),
    "Eris" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_eris,
            )
        )
    ),
    "Sedna" to PlanetSystemNode(
        planets = listOf(
            PlanetData(
                axisRotationSpeed = 1.0f,
                sphereRadius = 0.5f,
                sphereTextureResId = R.drawable.tex_solar_makemake,
            )
        )
    )
)