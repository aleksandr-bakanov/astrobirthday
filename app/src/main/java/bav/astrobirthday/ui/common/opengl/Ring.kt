package bav.astrobirthday.ui.common.opengl

import android.opengl.GLES20
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Ring(
    val innerRadius: Float,
    val outerRadius: Float,
    sectorCount: Int = 64
) {

    val vertices: FloatArray = FloatArray(size = 2 * (sectorCount + 1) * 3)
    val normals: FloatArray = FloatArray(size = 2 * (sectorCount + 1) * 3)
    val texCoords: FloatArray = FloatArray(size = 2 * (sectorCount + 1) * 2)

    val indices: IntArray = IntArray(size = sectorCount * 6)

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f)
    val colorTrans = floatArrayOf(0f, 0f, 0f, 0.7f)

    var angle: Float = 0f

    val modelTransformMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }
    val modelRotationMatrix = FloatArray(16).also {
        Matrix.setIdentityM(it, 0)
    }

    init {
        var x: Float
        var y: Float
        val z: Float = 0f
        var radius: Float

        var nx: Float
        var ny: Float
        var nz: Float

        var s: Float
        var t: Float

        val sectorStep = (2 * PI / sectorCount).toFloat()
        var sectorAngle: Float

        var vIndex = 0
        var nIndex = 0
        var tIndex = 0

        for (i in 0..1) {
            radius = if (i == 0) innerRadius else outerRadius

            for (j in 0..sectorCount)
            {
                sectorAngle = j * sectorStep           // starting from 0 to 2pi

                // vertex position (x, y, z)
                x = radius * cos(sectorAngle)
                y = radius * sin(sectorAngle)
                vertices[vIndex++] = x
                vertices[vIndex++] = y
                vertices[vIndex++] = z

                // normalized vertex normal (nx, ny, nz)
                nx = 0f
                ny = 0f
                nz = 1f
                normals[nIndex++] = nx
                normals[nIndex++] = ny
                normals[nIndex++] = nz

                // vertex tex coord (s, t) range between [0, 1]
                s = j.toFloat() / sectorCount
                t = i.toFloat()
                texCoords[tIndex++] = t
                texCoords[tIndex++] = s
            }
        }

        // generate CCW index list of ring triangles
        // k1--k1+1
        // |  / |
        // | /  |
        // k2--k2+1
        var k1: Int
        var k2: Int
        var kIndex = 0
        for (i in 0 until 1) {
            k1 = i * (sectorCount + 1)  // beginning of current stack
            k2 = k1 + sectorCount + 1   // beginning of next stack

            for (j in 0 until sectorCount) {
                // k1 => k2 => k1+1
                indices[kIndex++] = k1
                indices[kIndex++] = k2
                indices[kIndex++] = k1 + 1

                // k1+1 => k2 => k2+1
                indices[kIndex++] = k1 + 1
                indices[kIndex++] = k2
                indices[kIndex++] = k2 + 1

                k1++
                k2++
            }
        }
    }

    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(vertices.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(vertices)
                // set the buffer to read the first coordinate
                position(0)
            }
        }

    private var indicesBuffer: IntBuffer =
        ByteBuffer.allocateDirect(indices.size * 4).run {
            order(ByteOrder.nativeOrder())
            asIntBuffer().apply {
                put(indices)
                position(0)
            }
        }

    private var textureBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(texCoords.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(texCoords)
                position(0)
            }
        }

    private var normalBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(normals.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(normals)
                position(0)
            }
        }

    fun draw(program: Int, vpMatrix: FloatArray, texture: Int) {
        GLES20.glUseProgram(program)

        GLES20.glGetUniformLocation(program, "lightColor").also {
            GLES20.glUniform3fv(it, 1, lightColor, 0)
        }
        GLES20.glGetUniformLocation(program, "lightPos").also {
            GLES20.glUniform3fv(it, 1, lightPos, 0)
        }

        GLES20.glGetUniformLocation(program, "secondLightColor").also {
            GLES20.glUniform3fv(it, 1, secondLightColor, 0)
        }
        GLES20.glGetUniformLocation(program, "secondLightPos").also {
            GLES20.glUniform3fv(it, 1, secondLightPos, 0)
        }

        val vertexHandle = GLES20.glGetAttribLocation(program, "aPosition").also {
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                it,
                coordsPerVertex,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )
        }

        val textureHandle = GLES20.glGetAttribLocation(program, "a_Texture").also {
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                it,
                texturePerVertex,
                GLES20.GL_FLOAT,
                false,
                textureStride,
                textureBuffer
            )
        }

        val normalHandle = GLES20.glGetAttribLocation(program, "aNormal").also {
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                it,
                coordsPerVertex,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                normalBuffer
            )
        }

        GLES20.glGetAttribLocation(program, "a_Texture").also {
            GLES20.glUniform1i(it, 0)
        }

        // get handle to shape's transformation matrix
        GLES20.glGetUniformLocation(program, "uVPMatrix").also {
            GLES20.glUniformMatrix4fv(it, 1, false, vpMatrix, 0)
        }

        // Apply transformation matrix
        GLES20.glGetUniformLocation(program, "uModelTransform").also {
            GLES20.glUniformMatrix4fv(it, 1, false, modelTransformMatrix, 0)
        }
        GLES20.glGetUniformLocation(program, "uModelRotation").also {
            GLES20.glUniformMatrix4fv(it, 1, false, modelRotationMatrix, 0)
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.size, GLES20.GL_UNSIGNED_INT, indicesBuffer)

        GLES20.glDisableVertexAttribArray(vertexHandle)
        GLES20.glDisableVertexAttribArray(normalHandle)
        GLES20.glDisableVertexAttribArray(textureHandle)
    }

    companion object {
        private const val coordsPerVertex = 3
        private const val texturePerVertex = 2
        private const val vertexStride = coordsPerVertex * 4
        private const val textureStride = texturePerVertex * 4
    }
}