package bav.astrobirthday.ui.common.opengl

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PlanetSystemNode(
    // in world coordinates
    var massCenterX: Float = 0.0f,
    var massCenterY: Float = 0.0f,
    var massCenterZ: Float = 0.0f,

    // relative to parent mass center
    val orbitRadius: Float = 0.0f,
    // in radians
    var orbitAngle: Float = 0.0f,
    // in radians
    val angularVelocity: Float = 0.0f,

    val planets: MutableList<PlanetData> = mutableListOf(),
    val satellites: MutableList<PlanetSystemNode> = mutableListOf()
) {
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

        for (planet in planets) planet.update(massCenterX, massCenterY, massCenterZ)
        for (satellite in satellites) satellite.update(massCenterX, massCenterY, massCenterZ)
    }

    fun draw(program: Int, vpMatrix: FloatArray) {
        for (planet in planets) planet.draw(program, vpMatrix)
        for (satellite in satellites) satellite.draw(program, vpMatrix)
    }
}