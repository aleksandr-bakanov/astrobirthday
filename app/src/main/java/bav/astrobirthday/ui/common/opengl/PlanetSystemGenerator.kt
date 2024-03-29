package bav.astrobirthday.ui.common.opengl

import android.content.Context
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.model.Planet
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.sha1
import kotlin.math.log10
import kotlin.random.Random

private const val doubleSystemProbability = 0f
private const val ringProbability = 0f
private const val maxSatellitesAmount = 11
private const val minThresholdBetweenSatellites = 1f
private const val maxThresholdBetweenSatellites = 3f
private const val maxPlanetRadius = 2.5f
private const val minPlanetRadius = 1.2f
private const val minAxisTilt = -30.0f
private const val maxAxisTilt = 30.0f
private const val minOrbitTilt = -5.0f
private const val maxOrbitTilt = 5.0f

fun getPlanetSystemDescription(
    planetAndInfo: PlanetAndInfo,
    context: Context,
    isAnimated: Boolean
): PlanetRenderSystemNode? {
    return if (planetAndInfo.planet.planetName in Config.solarPlanetNames) {
        solarPlanetSystems[planetAndInfo.planet.planetName]?.toPlanetRenderSystemNode(context)
    } else {
        val random = Random(planetAndInfo.planet.planetName.sha1().contentHashCode())

        val planets = mutableListOf<PlanetRenderData>()
        if (isSystemDouble(random)) {
            val angularVelocity = 3f.degToRad() * random.nextFloat()
            val firstPlanet = getRandomPlanetDescription(
                random,
                planetAndInfo.planet,
                context,
                angularVelocity = angularVelocity
            )
            val secondPlanet = getRandomPlanetDescription(
                random,
                planetAndInfo.planet,
                context,
                angularVelocity = angularVelocity
            )

            val distanceBetweenPlanets =
                firstPlanet.totalRadius + secondPlanet.totalRadius + 1f + 2f * random.nextFloat()

            // Bigger planet should be behind on the start
            if (firstPlanet.sphere!!.radius > secondPlanet.sphere!!.radius) {
                firstPlanet.orbitAngle = 85f.degToRad()
                secondPlanet.orbitAngle = 265f.degToRad()
            } else {
                firstPlanet.orbitAngle = 265f.degToRad()
                secondPlanet.orbitAngle = 85f.degToRad()
            }

            val radiusSum = firstPlanet.sphere.radius + secondPlanet.sphere.radius
            firstPlanet.orbitRadius =
                distanceBetweenPlanets * (secondPlanet.sphere.radius / radiusSum)
            secondPlanet.orbitRadius =
                distanceBetweenPlanets * (firstPlanet.sphere.radius / radiusSum)

            planets.add(firstPlanet)
            planets.add(secondPlanet)
        } else {
            val centralPlanet = getRandomPlanetDescription(
                random = random,
                planet = planetAndInfo.planet,
                context = context,
                isRandomTexture = true
            )
            planets.add(centralPlanet)

            if (isAnimated) {
                val satellitesAmount = random.nextInt(maxSatellitesAmount)
                var currentMaxOrbit =
                    centralPlanet.totalRadius + getRandomThresholdBetweenSatellites(random)
                var currentAngularVelocity = 1f.degToRad()
                for (index in 0 until satellitesAmount) {
                    val satellite = getRandomPlanetDescription(
                        random = random,
                        planet = planetAndInfo.planet,
                        context = context,
                        isStrictlyWithoutRing = true,
                        orbitRadius = currentMaxOrbit,
                        orbitAngle = index * (360f / satellitesAmount).degToRad(),
                        angularVelocity = currentAngularVelocity,
                        sizeFactor = 0.01f + 0.07f * random.nextFloat(),
                        isSatellite = true,
                        centralPlanetAxisTiltX = centralPlanet.axisTiltX,
                        centralPlanetAxisTiltY = centralPlanet.axisTiltY
                    )
                    currentMaxOrbit += getRandomThresholdBetweenSatellites(random)
                    currentAngularVelocity /= 1.85f + 0.3f * random.nextFloat()
                    planets.add(satellite)
                }
            }
        }

        return PlanetRenderSystemNode(
            planets = planets
        )
    }
}

fun getPlanetTextureId(planetName: String): Int {
    val random = Random(planetName.sha1().contentHashCode())
    isSystemDouble(random)
    isRing(random)
    return getRandomPlanetTexture(random, TextureType.Unknown)
}

fun getRandomThresholdBetweenSatellites(random: Random): Float {
    return minThresholdBetweenSatellites + (maxThresholdBetweenSatellites - minThresholdBetweenSatellites) * random.nextFloat()
}

fun getTextureTypeByRadius(random: Random, radius: Float): TextureType {
    return when {
        radius >= 1.5f -> TextureType.GasGiant
        radius >= 1f -> TextureType.middleSizeTypes[random.nextInt(TextureType.middleSizeTypes.size)]
        else -> TextureType.smallSizeTypes[random.nextInt(TextureType.smallSizeTypes.size)]
    }
}

fun isSystemDouble(random: Random): Boolean = random.nextFloat() < doubleSystemProbability
fun isRing(random: Random): Boolean = random.nextFloat() < ringProbability

fun getPlanetRadiusByMass(planet: Planet): Float? {
    return planet.planetBestMassEstimateEarth?.let {
        (it / 4.0).toFloat()
    }
}

fun getRenderPlanetRadius(planet: Planet): Float {
    return (0.5f +
            log10(
                planet.planetRadiusEarth?.toFloat()
                    ?: getPlanetRadiusByMass(planet)
                    ?: 1.0f
            )
            ).coerceIn(minPlanetRadius, maxPlanetRadius)
}

fun getRandomPlanetDescription(
    random: Random,
    planet: Planet,
    context: Context,
    isStrictlyWithoutRing: Boolean = false,
    orbitRadius: Float = 0f,
    orbitAngle: Float = 0f,
    angularVelocity: Float = 0f,
    sizeFactor: Float = 1f,
    isRandomTexture: Boolean = false,
    isSatellite: Boolean = false,
    centralPlanetAxisTiltX: Float = 0f,
    centralPlanetAxisTiltY: Float = 0f
): PlanetRenderData {

    val planetRadius = getRenderPlanetRadius(planet) * sizeFactor

    val ring: Ring? = if (isStrictlyWithoutRing.not() && isRing(random)) {
        Ring(
            innerRadius = planetRadius,
            outerRadius = (1f + random.nextFloat()) * planetRadius * 2f
        )
    } else {
        null
    }
    val sphere = Sphere(
        radius = planetRadius
    )

    val textureResId = when {
        isSatellite -> getRandomSatelliteTexture(random)
        isRandomTexture -> getRandomPlanetTexture(random, TextureType.Unknown)
        else -> getRandomPlanetTexture(random, getTextureTypeByRadius(random, planetRadius))
    }

    val axisRotationSpeed = random.nextFloat() * 3f

    val axisTiltY = when {
        isSatellite -> minAxisTilt + (maxAxisTilt - minAxisTilt) * random.nextFloat()
        else -> minAxisTilt + (maxAxisTilt - minAxisTilt) * random.nextFloat()
    }
    val axisTiltX = when {
        isSatellite -> minAxisTilt + (maxAxisTilt - minAxisTilt) * random.nextFloat()
        else -> minAxisTilt + (maxAxisTilt - minAxisTilt) * random.nextFloat()
    }
    val orbitTiltX = when {
        isSatellite -> centralPlanetAxisTiltX + (minOrbitTilt + (maxOrbitTilt - minOrbitTilt) * random.nextFloat())
        else -> 0f
    }
    val orbitTiltY = when {
        isSatellite -> centralPlanetAxisTiltY + (minOrbitTilt + (maxOrbitTilt - minOrbitTilt) * random.nextFloat())
        else -> 0f
    }

    return PlanetRenderData(
        axisRotationSpeed = axisRotationSpeed,
        orbitRadius = orbitRadius,
        orbitAngle = orbitAngle,
        angularVelocity = angularVelocity,
        sphere = sphere,
        ring = ring,
        sphereTexture = TextureUtils.loadTexture(
            context,
            textureResId
        ),
        ringTexture = if (ring != null) {
            TextureUtils.loadTexture(context, getRandomRingTexture(random))
        } else 0,
        axisTiltY = axisTiltY,
        axisTiltX = axisTiltX,
        orbitTiltY = orbitTiltY,
        orbitTiltX = orbitTiltX
    )
}

fun getRandomRingTexture(random: Random): Int {
    return ringTextures[random.nextInt(ringTextures.size)]
}

/**
 * @return texture resource id
 */
fun getRandomPlanetTexture(random: Random, textureType: TextureType): Int {
    when (textureType) {
        TextureType.Unknown -> {
            return allPlanetTexturesFlatten[random.nextInt(allPlanetTexturesFlatten.size)]
        }
        else -> {
            val textures = when (textureType) {
                TextureType.Fungal -> fungalTextures
                TextureType.GasGiant -> gasGiantTextures
                TextureType.Ice -> iceTextures
                TextureType.Martian -> martianTextures
                TextureType.Oasis -> oasisTextures
                TextureType.Oceanic -> oceanicTextures
                TextureType.Primordial -> primordialTextures
                TextureType.Rock -> rockTextures
                TextureType.Swamp -> swampTextures
                TextureType.Terrestrial -> terrestrialTextures
                TextureType.Volcanic -> volcanicTextures
                TextureType.Venusian -> venusianTextures
                TextureType.Wetlands -> wetlandsTextures
                else -> allPlanetTextures[random.nextInt(allPlanetTextures.size)]
            }
            return textures[random.nextInt(textures.size)]
        }
    }
}

fun getRandomSatelliteTexture(random: Random): Int {
    return satelliteTextures[random.nextInt(satelliteTextures.size)]
}