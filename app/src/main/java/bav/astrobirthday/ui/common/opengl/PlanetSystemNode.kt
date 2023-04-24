package bav.astrobirthday.ui.common.opengl

import android.content.Context

data class PlanetSystemNode(
    // relative to parent mass center
    val orbitRadius: Float = 0.0f,
    // in radians
    val orbitAngle: Float = 0.0f,
    // in radians
    val angularVelocity: Float = 0.0f,
    // in degrees
    val orbitTilt: Float = 0.0f,

    val planets: List<PlanetData> = listOf(),
    val satellites: List<PlanetSystemNode> = listOf()
)

fun PlanetSystemNode.toPlanetRenderSystemNode(context: Context): PlanetRenderSystemNode {
    return PlanetRenderSystemNode(
        orbitRadius = orbitRadius,
        orbitAngle = orbitAngle,
        angularVelocity = angularVelocity,
        orbitTilt = orbitTilt,
        planets = planets.map {
            it.toPlanetRenderData(context)
        },
        satellites = satellites.map { it.toPlanetRenderSystemNode(context) }
    )
}