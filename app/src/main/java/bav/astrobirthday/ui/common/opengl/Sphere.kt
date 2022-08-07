package bav.astrobirthday.ui.common.opengl

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Sphere(
    radius: Float,
    sectorCount: Int,
    stackCount: Int
) {

    val vertices: FloatArray = FloatArray(size = (sectorCount + 1) * (stackCount + 1) * 3)
    val normals: FloatArray = FloatArray(size = (sectorCount + 1) * (stackCount + 1) * 3)
    val texCoords: FloatArray = FloatArray(size = (sectorCount + 1) * (stackCount + 1) * 2)

    val indices: IntArray = IntArray(size = (stackCount - 1) * sectorCount * 6)
    val lineIndices: IntArray = IntArray(size = stackCount * sectorCount * 4 - sectorCount * 2)

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(1.0f, 0f, 0f, 1.0f)

    init {
        var x: Float
        var y: Float
        var z: Float
        var xy: Float

        var nx: Float
        var ny: Float
        var nz: Float
        val lengthInv = 1.0f / radius

        var s: Float
        var t: Float

        val sectorStep = (2 * PI / sectorCount).toFloat()
        val stackStep = (PI / stackCount).toFloat()
        var sectorAngle: Float
        var stackAngle: Float

        for (i in 0..stackCount)
        {
            stackAngle = (PI / 2 - i * stackStep).toFloat()        // starting from pi/2 to -pi/2
            xy = radius * cos(stackAngle);             // r * cos(u)
            z = radius * sin(stackAngle);              // r * sin(u)

            // the first and last vertices have same position and normal, but different tex coords
            for (j in 0..sectorCount)
            {
                sectorAngle = j * sectorStep           // starting from 0 to 2pi

                // vertex position (x, y, z)
                x = xy * cos(sectorAngle)             // r * cos(u) * cos(v)
                y = xy * sin(sectorAngle)             // r * cos(u) * sin(v)
                vertices[i * sectorCount + j] = x
                vertices[i * sectorCount + j + 1] = y
                vertices[i * sectorCount + j + 2] = z

                // normalized vertex normal (nx, ny, nz)
                nx = x * lengthInv
                ny = y * lengthInv
                nz = z * lengthInv
                normals[i * sectorCount + j] = nx
                normals[i * sectorCount + j + 1] = ny
                normals[i * sectorCount + j + 2] = nz

                // vertex tex coord (s, t) range between [0, 1]
                s = j.toFloat() / sectorCount
                t = i.toFloat() / stackCount
                texCoords[i * sectorCount + j] = s
                texCoords[i * sectorCount + j + 1] = t
            }
        }

        // generate CCW index list of sphere triangles
        // k1--k1+1
        // |  / |
        // | /  |
        // k2--k2+1
        var k1: Int
        var k2: Int
        var kIndex = 0
        var kLineIndex = 0
        for (i in 0 until stackCount) {
            k1 = i * (sectorCount + 1)  // beginning of current stack
            k2 = k1 + sectorCount + 1   // beginning of next stack

            for (j in 0 until sectorCount) {
                // 2 triangles per sector excluding first and last stacks
                // k1 => k2 => k1+1
                if(i != 0)
                {
                    indices[kIndex++] = k1
                    indices[kIndex++] = k2
                    indices[kIndex++] = k1 + 1
                }

                // k1+1 => k2 => k2+1
                if(i != (stackCount-1))
                {
                    indices[kIndex++] = k1 + 1
                    indices[kIndex++] = k2
                    indices[kIndex++] = k2 + 1
                }

                // store indices for lines
                // vertical lines for all stacks, k1 => k2
                lineIndices[kLineIndex++] = k1
                lineIndices[kLineIndex++] = k2
                if(i != 0)  // horizontal lines except 1st stack, k1 => k+1
                {
                    lineIndices[kLineIndex++] = k1
                    lineIndices[kLineIndex++] = k1 + 1
                }

                k1++
                k2++
            }
        }
        Log.d("cqhg43", "kIndex = $kIndex, indices.size = ${indices.size}")
        Log.d("cqhg43", "kLineIndex = $kLineIndex, lineIndices.size = ${lineIndices.size}")
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

    private var lineIndicesBuffer: IntBuffer =
        ByteBuffer.allocateDirect(lineIndices.size * 4).run {
            order(ByteOrder.nativeOrder())
            asIntBuffer().apply {
                put(lineIndices)
                position(0)
            }
        }

    private var vertexHandle = 0

    fun draw(program: Int, mvpMatrix: FloatArray) {
        GLES20.glUseProgram(program)
        Log.d("cqhg43", "program = $program")

        // get handle to shape's transformation matrix
        GLES20.glGetUniformLocation(program, "uMVPMatrix").also {
            GLES20.glUniformMatrix4fv(it, 1, false, mvpMatrix, 0)
        }

        vertexHandle = GLES20.glGetAttribLocation(program, "vPosition").also {
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

        GLES20.glGetUniformLocation(program, "vColor").also { colorHandle ->
            // Set color for drawing the triangle
            GLES20.glUniform4fv(colorHandle, 1, color, 0)
        }

        GLES20.glDrawElements(GLES20.GL_LINES, vertices.size / coordsPerVertex, GLES20.GL_UNSIGNED_INT, lineIndicesBuffer)

        GLES20.glDisableVertexAttribArray(vertexHandle)
    }

    companion object {
        private const val coordsPerVertex = 3
        private const val vertexStride = coordsPerVertex * 4
    }
}