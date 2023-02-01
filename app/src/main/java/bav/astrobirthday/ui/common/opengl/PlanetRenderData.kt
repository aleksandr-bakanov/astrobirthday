package bav.astrobirthday.ui.common.opengl

import android.opengl.Matrix
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class PlanetRenderData(
    // in degree
    val axisRotationSpeed: Float = 1.0f,

    // relative to parent mass center
    var orbitRadius: Float = 0.0f,
    // in radians
    var orbitAngle: Float = 0.0f,
    // in radians
    val angularVelocity: Float = 0.0f,

    val sphere: Sphere? = null,
    val ring: Ring? = null,

    var sphereTexture: Int = 0,
    var ringTexture: Int = 0
) {
    val totalRadius: Float
        get() = max(this.sphere?.radius ?: 0f, this.ring?.outerRadius ?: 0f)

    // position in world coordinates
    private var x: Float = 0.0f
    private var y: Float = 0.0f
    private var z: Float = 0.0f

    /**
     * Update planet position according to its params. Each frame
     *
     * @param parentX x coordinate of parent node in world coordinates
     * @param parentY y coordinate of parent node in world coordinates
     * @param parentZ z coordinate of parent node in world coordinates
     */
    fun update(parentX: Float = 0f, parentY: Float = 0f, parentZ: Float = 0f) {
        orbitAngle += angularVelocity
        if (orbitAngle > 2 * PI)
            orbitAngle -= 2 * PI.toFloat()

        x = parentX + cos(orbitAngle) * orbitRadius
        y = parentY + sin(orbitAngle) * orbitRadius
        z = parentZ

        sphere?.let {
            it.angle += axisRotationSpeed
            if (it.angle > 360f) it.angle -= 360f

            Matrix.setIdentityM(it.modelTransformMatrix, 0)
            Matrix.translateM(it.modelTransformMatrix, 0, x, y, z)
        }
        ring?.let {
            Matrix.setIdentityM(it.modelTransformMatrix, 0)
            Matrix.translateM(it.modelTransformMatrix, 0, x, y, z)
        }
    }

    fun draw(program: Int, vpMatrix: FloatArray) {
        sphere?.draw(program, vpMatrix, sphereTexture)
        ring?.draw(program, vpMatrix, ringTexture)
    }
}
