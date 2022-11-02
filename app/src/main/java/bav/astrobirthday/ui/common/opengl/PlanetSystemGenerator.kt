package bav.astrobirthday.ui.common.opengl

import android.content.Context
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.sha1
import kotlin.random.Random

private const val doubleSystemProbability = 0.2f
private const val ringProbability = 0.33f

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
                getRandomTextureType(random),
                context,
                angularVelocity = angularVelocity
            )
            val secondPlanet = getRandomPlanetDescription(
                random,
                getRandomTextureType(random),
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
            planets.add(getRandomPlanetDescription(random, getRandomTextureType(random), context))
        }

        return PlanetRenderSystemNode(
            planets = planets
        )
    }
}

fun getRandomTextureType(random: Random): TextureType {
    return TextureType.allTypes[random.nextInt(TextureType.allTypes.size)]
}

fun isSystemDouble(random: Random): Boolean = random.nextFloat() < doubleSystemProbability
fun isRing(random: Random): Boolean = random.nextFloat() < ringProbability

fun getRandomPlanetDescription(
    random: Random,
    textureType: TextureType,
    context: Context,
    orbitRadius: Float = 0f,
    orbitAngle: Float = 0f,
    angularVelocity: Float = 0f,
    factor: Float = 1f
): PlanetRenderData {
    val planetRadius = textureType.getRandomRadius(random) * factor
    val ring: Ring? = if (isRing(random)) {
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