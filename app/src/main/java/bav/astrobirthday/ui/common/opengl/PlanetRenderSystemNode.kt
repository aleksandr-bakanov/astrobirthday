package bav.astrobirthday.ui.common.opengl

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PlanetRenderSystemNode(
    // relative to parent mass center
    val orbitRadius: Float = 0.0f,
    // in radians
    var orbitAngle: Float = 0.0f,
    // in radians
    val angularVelocity: Float = 0.0f,

    val planets: List<PlanetRenderData> = mutableListOf(),
    val satellites: List<PlanetRenderSystemNode> = mutableListOf()
) {
    // in world coordinates
    private var massCenterX: Float = 0.0f
    private var massCenterY: Float = 0.0f
    private var massCenterZ: Float = 0.0f

    /**
     * Update planet system according to its params. Each frame
     *
     * @param parentX x coordinate of parent node in world coordinates
     * @param parentY y coordinate of parent node in world coordinates
     * @param parentZ z coordinate of parent node in world coordinates
     */
    fun update(parentX: Float = 0f, parentY: Float = 0f, parentZ: Float = 0f) {
        orbitAngle += angularVelocity
        if (orbitAngle > 2 * PI)
            orbitAngle -= 2 * PI.toFloat()

        massCenterX = parentX + cos(orbitAngle) * orbitRadius
        massCenterY = parentY + sin(orbitAngle) * orbitRadius
        massCenterZ = parentZ

        for (planet in planets) planet.update(massCenterX, massCenterY, massCenterZ)
        for (satellite in satellites) satellite.update(massCenterX, massCenterY, massCenterZ)
    }
}