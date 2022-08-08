package bav.astrobirthday.ui.common.opengl

import android.content.Context
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import bav.astrobirthday.R
import kotlin.math.cos
import kotlin.math.sin

class PlanetView3dRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private lateinit var sphere: Sphere
    private lateinit var sphere2: Sphere

    private var program: Int = 0

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    private var texture: Int = 0

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glDepthFunc(GLES20.GL_LEQUAL)
        GLES20.glDepthMask(true)

        GLES20.glLineWidth(3f)

        initProgram()

        texture = TextureUtils.loadTexture(context, R.drawable.earth_texture)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)

        sphere = Sphere(1.0f, 64, 32)
        Matrix.translateM(sphere.transform, 0, 0.5f, 0f, 0f)

        sphere2 = Sphere(0.5f, 64, 32)
        Matrix.translateM(sphere2.transform, 0, -0.5f, -0.5f, 0f)
    }

    override fun onDrawFrame(unused: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, -4f, 5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        sphere.draw(program, vPMatrix)

        val time = SystemClock.uptimeMillis() % 144000L
        val angle = 0.0025f * time.toInt()
        Matrix.setIdentityM(sphere2.transform, 0)
        Matrix.translateM(sphere2.transform, 0, -0.75f * sin(angle), -0.75f * cos(angle), 0f)

        sphere2.draw(program, vPMatrix)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        val ratio: Float = width.toFloat() / height.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 10f)

    }

    private fun initProgram() {
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // create empty OpenGL ES Program
        program = GLES20.glCreateProgram().also {
            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)
            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)
            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        return GLES20.glCreateShader(type).also { shader ->
            // add the source code to the shader and compile it
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    companion object {
        private val vertexShaderCode =
                    "uniform mat4 uMVPMatrix;" +
                    "uniform mat4 uTransform;" +
                    "attribute vec2 a_Texture;" +
                    "varying vec2 v_Texture;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * uTransform * vPosition;" +
                    "  v_Texture = a_Texture;" +
                    "}"

        private val fragmentShaderCode =
                    "precision mediump float;" +
                    "uniform sampler2D u_TextureUnit;" +
                    "varying vec2 v_Texture;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(u_TextureUnit, v_Texture);" +
                    "}"
    }

}
