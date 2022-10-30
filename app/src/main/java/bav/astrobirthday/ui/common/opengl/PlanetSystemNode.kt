package bav.astrobirthday.ui.common.opengl

import android.content.Context

data class PlanetSystemNode(
    // relative to parent mass center
    val orbitRadius: Float = 0.0f,
    // in radians
    val orbitAngle: Float = 0.0f,
    // in radians
    val angularVelocity: Float = 0.0f,

    val planets: MutableList<PlanetData> = mutableListOf(),
    val satellites: MutableList<PlanetSystemNode> = mutableListOf()
)

fun PlanetSystemNode.toPlanetRenderSystemNode(context: Context): PlanetRenderSystemNode {
    return PlanetRenderSystemNode(
        orbitRadius = orbitRadius,
        orbitAngle = orbitAngle,
        angularVelocity = angularVelocity,
        planets = planets.map {
            it.toPlanetRenderData(context)
        },
        satellites = satellites.map { it.toPlanetRenderSystemNode(context) }
    )
}