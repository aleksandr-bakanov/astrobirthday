package bav.astrobirthday.ui.common.opengl

import android.content.Context
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.model.Planet
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.sha1
import kotlin.math.log10
import kotlin.random.Random

private const val doubleSystemProbability = 0.05f
private const val ringProbability = 0.2f
private const val maxSatellitesAmount = 11
private const val minThresholdBetweenSatellites = 1f
private const val maxThresholdBetweenSatellites = 3f
private const val maxPlanetRadius = 2.0f
private const val minPlanetRadius = 0.3f

fun getPlanetSystemDescription(
    planetAndInfo: PlanetAndInfo,
    context: Context
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
            val centralPlanet = getRandomPlanetDescription(random, planetAndInfo.planet, context)
            planets.add(centralPlanet)

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
                    sizeFactor = 0.01f + 0.07f * random.nextFloat()
                )
                currentMaxOrbit += getRandomThresholdBetweenSatellites(random)
                currentAngularVelocity /= 1.85f + 0.3f * random.nextFloat()
                planets.add(satellite)
            }
        }

        return PlanetRenderSystemNode(
            planets = planets
        )
    }
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
    sizeFactor: Float = 1f
): PlanetRenderData {

    val planetRadius = getRenderPlanetRadius(planet) * sizeFactor
    val textureType = getTextureTypeByRadius(random, planetRadius)

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
    return PlanetRenderData(
        axisRotationSpeed = random.nextFloat() * 3f,
        orbitRadius = orbitRadius,
        orbitAngle = orbitAngle,
        angularVelocity = angularVelocity,
        sphere = sphere,
        ring = ring,
        sphereTexture = TextureUtils.loadTexture(
            context,
            getRandomPlanetTexture(random, textureType)
        ),
        ringTexture = if (ring != null) {
            random.nextInt()
            TextureUtils.loadTexture(context, R.drawable.tex_solar_saturn_ring_alpha)
        } else 0
    )
}

/**
 * @return texture resource id
 */
fun getRandomPlanetTexture(random: Random, textureType: TextureType): Int {
    val textures = when (textureType) {
        TextureType.GasGiant -> gasGiantTextures
        TextureType.Ice -> iceTextures
        TextureType.Martian -> martianTextures
        TextureType.Primordial -> primordialTextures
        TextureType.Rock -> rockTextures
        TextureType.Terrestrial -> terrestrialCloudsTextures
        TextureType.Volcanic -> volcanicTextures
        TextureType.Venusian -> venusianTextures
        else -> allPlanetTextures[random.nextInt(allPlanetTextures.size)]
    }
    return textures[random.nextInt(textures.size)]
}