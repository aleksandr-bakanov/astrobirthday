package bav.astrobirthday.ui.common.opengl

import android.opengl.Matrix
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
    // in degrees
    val orbitTilt: Float = 0.0f,

    val planets: List<PlanetRenderData> = mutableListOf(),
    val satellites: List<PlanetRenderSystemNode> = mutableListOf()
) {

    // In world coordinates
    private val massCenter = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    /**
     * Update planet system according to its params. Each frame
     *
     * @param parentTransform transform matrix [4,4] of the parent node in world coordinates
     */
    fun update(parentTransform: FloatArray) {
        orbitAngle += angularVelocity
        if (orbitAngle > 2 * PI)
            orbitAngle -= 2 * PI.toFloat()

        parentTransform.copyInto(massCenter)
        Matrix.translateM(
            massCenter,
            0,
            cos(orbitAngle) * orbitRadius,
            sin(orbitAngle) * orbitRadius,
            0f
        )
        Matrix.rotateM(massCenter, 0, orbitTilt, 0f, 1f, 0f)

        for (planet in planets) planet.update(massCenter)
        for (satellite in satellites) satellite.update(massCenter)
    }
}