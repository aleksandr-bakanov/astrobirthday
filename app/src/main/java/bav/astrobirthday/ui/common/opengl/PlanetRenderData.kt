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
    // in degrees
    val axisTiltY: Float = 0.0f,
    // in degrees
    val orbitTiltY: Float = 0.0f,
    // in degrees
    val axisTiltX: Float = 0f,
    // in degrees
    val orbitTiltX: Float = 0.0f,

    val sphere: Sphere? = null,
    val ring: Ring? = null,

    var sphereTexture: Int = 0,
    var ringTexture: Int = 0
) {
    val totalRadius: Float
        get() = max(this.sphere?.radius ?: 0f, this.ring?.outerRadius ?: 0f)

    // Position in world coordinates
    private val centerPosition = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    private val orbitTiltComputeMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
        Matrix.rotateM(it, 0, orbitTiltY, 0f, 1f, 0f)
        Matrix.rotateM(it, 0, orbitTiltX, 1f, 0f, 0f)
    }
    private val orbitTiltComputeVector = floatArrayOf(0f, 0f, 0f, 0f)
    private val orbitTiltResultVector = floatArrayOf(0f, 0f, 0f, 0f)

    fun update(parentTransform: FloatArray) {
        orbitAngle += angularVelocity
        if (orbitAngle > 2 * PI)
            orbitAngle -= 2 * PI.toFloat()

        parentTransform.copyInto(centerPosition)

        orbitTiltComputeVector[0] = cos(orbitAngle) * orbitRadius
        orbitTiltComputeVector[1] = sin(orbitAngle) * orbitRadius
        orbitTiltComputeVector[2] = 0f

        Matrix.multiplyMV(
            orbitTiltResultVector,
            0,
            orbitTiltComputeMatrix,
            0,
            orbitTiltComputeVector,
            0
        )

        Matrix.translateM(
            centerPosition,
            0,
            orbitTiltResultVector[0],
            orbitTiltResultVector[1],
            orbitTiltResultVector[2]
        )

        sphere?.let {
            it.angle += axisRotationSpeed
            if (it.angle > 360f) it.angle -= 360f

            centerPosition.copyInto(it.modelTransformMatrix)

            Matrix.setIdentityM(it.modelRotationMatrix, 0)
            Matrix.rotateM(it.modelRotationMatrix, 0, axisTiltY, 0f, 1f, 0f)
            Matrix.rotateM(it.modelRotationMatrix, 0, axisTiltX, 1f, 0f, 0f)
            Matrix.rotateM(it.modelRotationMatrix, 0, it.angle, 0f, 0f, 1f)
        }
        ring?.let {
            centerPosition.copyInto(it.modelTransformMatrix)

            Matrix.setIdentityM(it.modelRotationMatrix, 0)
            Matrix.rotateM(it.modelRotationMatrix, 0, axisTiltY, 0f, 1f, 0f)
            Matrix.rotateM(it.modelRotationMatrix, 0, axisTiltX, 1f, 0f, 0f)
        }
    }
}
