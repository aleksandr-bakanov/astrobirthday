package bav.astrobirthday.ui.common.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.sha1
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.max
import kotlin.random.Random

val lightColor = floatArrayOf(1.0f, 1.0f, 1.0f)
val lightPos = floatArrayOf(0.0f, 0.0f, 10.0f)
val secondLightColor = floatArrayOf(0.1f, 0.1f, 0.1f)
val secondLightPos = floatArrayOf(0.0f, 0.0f, -15.0f)

class PlanetView3dRenderer(
    private val context: Context,
    private val planetAndInfo: PlanetAndInfo
) : GLSurfaceView.Renderer {

    private var program: Int = 0

    private val viewMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)

    // vPMatrix is an abbreviation for "View Projection Matrix"
    private val vPMatrix = FloatArray(16)

    private var texture: Int = 0
    private var moonTexture: Int = 0

    private var planetSystem: PlanetRenderSystemNode? = null

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        //GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glDepthFunc(GLES20.GL_LEQUAL)
        GLES20.glDepthMask(true)

        initProgram()

//        texture = TextureUtils.loadTexture(context, R.drawable.dry)
//        moonTexture = TextureUtils.loadTexture(context, R.drawable.moon_texture)


        planetSystem = getPlanetSystemDescription(planetAndInfo, context)

//        planetSystem = PlanetSystemNode(
//            planets = mutableListOf(
//                PlanetData(
//                    axisRotationSpeed = 1.0f,
//                    orbitRadius = 0.2f,
//                    orbitAngle = 0.0f,
//                    angularVelocity = (PI / 180.0).toFloat(),
//                    sphere = Sphere(1.5f),
//                    sphereTexture = texture
//                )
//            ),
//            satellites = mutableListOf(
//                PlanetSystemNode(
//                    orbitRadius = 2.7f,
//                    orbitAngle = PI.toFloat(),
//                    angularVelocity = (PI / 180.0).toFloat(),
//                    planets = mutableListOf(
//                        PlanetData(
//                            axisRotationSpeed = 2.0f,
//                            orbitRadius = 0.0f,
//                            orbitAngle = 0.0f,
//                            angularVelocity = 0.0f,
//                            sphere = Sphere(0.3f),
//                            sphereTexture = moonTexture
//                        ),
//                        PlanetData(
//                            axisRotationSpeed = 3.0f,
//                            orbitRadius = 0.7f,
//                            orbitAngle = 0.0f,
//                            angularVelocity = 5f * (PI / 180.0).toFloat(),
//                            sphere = Sphere(0.1f, 32, 16),
//                            sphereTexture = moonTexture
//                        )
//                    )
//                )
//            )
//        )
    }

    override fun onDrawFrame(unused: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        planetSystem?.let {
            it.update()
            it.draw(program, vPMatrix)
        }
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        val ratio: Float = width.toFloat() / height.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 100f)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, -15f, 3f, 0f, 0f, 1f, 0f, 1.0f, 0.0f)
        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
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

    private fun getPlanetSystemDescription(
        planetAndInfo: PlanetAndInfo,
        context: Context
    ): PlanetRenderSystemNode? {
        return if (planetAndInfo.planet.planetName in Config.solarPlanetNames) {
            solarPlanetSystems[planetAndInfo.planet.planetName]?.toPlanetRenderSystemNode(context)
        } else {
            val random = Random(planetAndInfo.planet.planetName.sha1().contentHashCode())
            return PlanetRenderSystemNode(
                planets = mutableListOf(
                    getRandomPlanetDescription(random, 3f, context)
                )
            )
        }
    }

    private fun getRandomPlanetDescription(
        random: Random,
        maxRadius: Float,
        context: Context
    ): PlanetRenderData {
        val isRing = random.nextFloat() < 0.33f
        var innerRingRadius = 0f
        val ring: Ring? = if (isRing) {
            innerRingRadius = 0.3f * maxRadius + 0.6f * maxRadius * random.nextFloat()
            Ring(
                innerRadius = innerRingRadius,
                outerRadius = innerRingRadius + (maxRadius - innerRingRadius) * random.nextFloat()
            )
        } else {
            innerRingRadius = maxRadius
            null
        }
        val sphere = Sphere(
            radius = max(0.3f, innerRingRadius * random.nextFloat())
        )
        return PlanetRenderData(
            axisRotationSpeed = random.nextFloat() * 3f,
            orbitRadius = 0f,
            orbitAngle = 0f,
            angularVelocity = 0f,
            sphere = sphere,
            ring = ring,
            sphereTexture = TextureUtils.loadTexture(
                context,
                getRandomPlanetTexture(random)
            ),
            ringTexture = if (ring != null)
                TextureUtils.loadTexture(context, R.drawable.tex_solar_saturn_ring_alpha)
            else 0
        )
    }

    /**
     * @return texture resource id
     */
    private fun getRandomPlanetTexture(random: Random): Int {
        val textures = allPlanetTextures[random.nextInt(allPlanetTextures.size)]
        return textures[random.nextInt(textures.size)]
    }

    companion object {
        private const val vertexShaderCode =
            "uniform mat4 uVPMatrix;" +
                    "uniform mat4 uModelTransform;" +
                    "uniform mat4 uModelRotation;" +
                    "attribute vec2 a_Texture;" +
                    "varying vec2 v_Texture;" +
                    "attribute vec4 aPosition;" +
                    "attribute vec3 aNormal;" +
                    "varying vec3 vNormal;" +
                    "varying vec3 vFragPos;" +
                    "void main() {" +
                    "  mat4 uModel = uModelTransform * uModelRotation;" +
                    "  gl_Position = uVPMatrix * uModel * aPosition;" +
                    "  v_Texture = a_Texture;" +
                    "  vNormal = vec3(uModelRotation * vec4(aNormal, 1.0));" +
                    "  vFragPos = vec3(uModel * aPosition);" +
                    "}"

        private const val fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D u_TextureUnit;" +
                    "uniform vec3 lightPos;" +
                    "uniform vec3 lightColor;" +
                    "uniform vec3 secondLightPos;" +
                    "uniform vec3 secondLightColor;" +
                    "varying vec2 v_Texture;" +
                    "varying vec3 vNormal;" +
                    "varying vec3 vFragPos;" +
                    "void main() {" +
                    "  vec3 norm = normalize(vNormal);" +
                    "  vec3 lightDir = normalize(lightPos - vFragPos);" +
                    "  vec3 secondLightDir = normalize(secondLightPos - vFragPos);" +
                    "  float diff = max(dot(norm, lightDir), 0.0);" +
                    "  float secondDiff = max(dot(norm, secondLightDir), 0.0);" +
                    "  vec3 diffuse = diff * lightColor;" +
                    "  vec3 secondDiffuse = secondDiff * secondLightColor;" +
                    "  float ambientStrength = 0.15;" +
                    "  vec3 ambient = ambientStrength * lightColor;" +
                    "  vec4 result = vec4(ambient + diffuse + secondDiffuse, 1.0) * texture2D(u_TextureUnit, v_Texture);" +
                    "  gl_FragColor = result;" +
                    "}"
    }

}
