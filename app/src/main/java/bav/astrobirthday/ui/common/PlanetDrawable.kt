package bav.astrobirthday.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.utils.sha1
import bav.astrobirthday.utils.toPx
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class PlanetDrawable(private val context: Context, planet: Planet) : Drawable() {

    private val bg =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_background)!!.mutate()
    private val shadow =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_shadow)!!.mutate()
    private val radialShadow =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_radial_shadow)!!.mutate()
    private val surfaceRings =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_surface_rings)!!.mutate()
    private val polarHat =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_polar_hat)!!.mutate()
    private val ring =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_ring)!!.mutate()
    private val innerRing =
        AppCompatResources.getDrawable(context, R.drawable.ic_features_inner_ring)!!.mutate()

    private val config = Config(planet)

    private val craterMatrix = Matrix()
    private val craterPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var craters: List<Crater> = emptyList()

    data class Crater(val oval: RectF = RectF(), val gradient: RadialGradient)

    init {
        bg.colorFilter = config.bgColorFilter
        shadow.alpha = config.shadowAlpha
        surfaceRings.colorFilter = config.surfaceRingsColorFilter
        craterPaint.colorFilter = config.craterColorFilter
        ring.colorFilter = config.ringColorFilter
        innerRing.colorFilter = config.innerRingColorFilter
    }

    override fun draw(canvas: Canvas) {
        bg.draw(canvas)
        if (config.showCraters) {
            craters.forEach { crater ->
                craterMatrix.reset()
                craterPaint.shader = crater.gradient
                craterMatrix.postTranslate(
                    (crater.oval.left + crater.oval.right) / 2,
                    (crater.oval.top + crater.oval.bottom) / 2
                )
                craterPaint.shader.setLocalMatrix(craterMatrix)
                canvas.drawOval(crater.oval, craterPaint)
            }
        }

        canvas.save()
        canvas.rotate(
            config.surfaceRotation,
            bounds.width().toFloat() / 2,
            bounds.height().toFloat() / 2
        )
        if (config.showSurfaceRings) surfaceRings.draw(canvas)
        if (config.showPolarHat) polarHat.draw(canvas)
        canvas.restore()

        shadow.draw(canvas)
        radialShadow.draw(canvas)

        canvas.save()
        canvas.rotate(
            config.ringsRotation,
            bounds.width().toFloat() / 2,
            bounds.height().toFloat() / 2
        )
        if (config.showRing) ring.draw(canvas)
        if (config.showInnerRing) innerRing.draw(canvas)
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        val ratio = (right - left) / 56.toPx(context)
        val largePadding = (12.toPx(context).toInt() * ratio).toInt()
        val mediumPadding = (7.toPx(context).toInt() * ratio).toInt()
        val smallPadding = 0
        val (bgPadding, innerPadding) = when {
            config.showRing -> largePadding to mediumPadding
            config.showInnerRing -> mediumPadding to smallPadding
            else -> smallPadding to smallPadding
        }
        val ringBounds = Rect(left, top, right, bottom)
        val bgBounds = Rect(ringBounds).apply { inset(bgPadding, bgPadding) }
        val innerRingBounds = Rect(ringBounds).apply { inset(innerPadding, innerPadding) }
        bg.bounds = bgBounds
        shadow.bounds = bgBounds
        radialShadow.bounds = bgBounds
        surfaceRings.bounds = bgBounds
        polarHat.bounds = bgBounds
        ring.bounds = ringBounds
        innerRing.bounds = innerRingBounds

        // Craters
        if (config.showCraters) {
            val boundsR = bgBounds.right - bgBounds.exactCenterX()
            val cx = bgBounds.exactCenterX()
            val cy = bgBounds.exactCenterY()
            craters = filterIntersectCraters(
                config.craterConfigs,
                boundsR,
                context,
                ratio
            ).map { crater ->
                val r = crater.r.toPx(context).toInt() * ratio
                val a = crater.a
                val rv = ((crater.rv * (boundsR - 2 * r)) + r)
                val px = rv * cos(a)
                val py = rv * sin(a)
                Crater(
                    oval = RectF(
                        (px - r).toFloat() + cx,
                        (py - r).toFloat() + cy,
                        (px + r).toFloat() + cx,
                        (py + r).toFloat() + cy
                    ),
                    gradient = RadialGradient(
                        0f,
                        0f,
                        r,
                        radialColors,
                        null,
                        Shader.TileMode.CLAMP
                    )
                )
            }
        }
    }

    private fun filterIntersectCraters(
        craters: List<CraterConfig>,
        br: Float,
        context: Context,
        ratio: Float
    ): List<CraterConfig> {
        val filteredCraters = mutableListOf<CraterConfig>()
        craters.forEach { c ->
            val cr = c.r.toPx(context) * ratio
            val r1 = ((c.rv * (br - 2 * cr)) + cr).toDouble()
            if (filteredCraters.none { f ->
                    val fr = f.r.toPx(context) * ratio
                    val r2 = ((f.rv * (br - 2 * fr)) + fr).toDouble()
                    (sqrt(r1.pow(2.0) + r2.pow(2.0) - 2.0 * r1 * r2 * cos(c.a - f.a)) <= cr + fr)
                }) filteredCraters.add(c)
        }
        return filteredCraters.take(config.craterCount)
    }

    private companion object {
        fun Random.nextColor(): Int = this.nextInt(0, 255)
        fun Random.nextAlpha(from: Int = 75, to: Int = 180): Int = this.nextInt(from, to)
        fun Random.nextBoolean(chance: Float): Boolean = this.nextFloat() < chance
        val radialColors = arrayOf(0xFF000000.toInt(), 0x00000000).toIntArray()
    }

    class Config(planet: Planet) {
        private val random = Random(planet.pl_name.sha1().contentHashCode())

        val showRing = random.nextBoolean(0.4f)
        val showInnerRing = random.nextBoolean(0.4f)
        val showPolarHat = random.nextBoolean()
        val showCraters = random.nextBoolean(0.65f)
        val surfaceRotation = random.nextInt(0, 360).toFloat()
        val ringsRotation = surfaceRotation + random.nextInt(-15, 15).toFloat()
        val showSurfaceRings = random.nextBoolean()
        val bgColorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.argb(255, random.nextColor(), random.nextColor(), random.nextColor()),
            BlendModeCompat.SRC_IN
        )
        val shadowAlpha = random.nextAlpha()
        val surfaceRingsColorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.argb(
                random.nextAlpha(),
                random.nextColor(),
                random.nextColor(),
                random.nextColor()
            ), BlendModeCompat.SRC_IN
        )
        val craterColorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.argb(
                random.nextAlpha(from = 75, to = 100),
                random.nextColor(),
                random.nextColor(),
                random.nextColor()
            ), BlendModeCompat.SRC_IN
        )
        val ringColorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.argb(
                random.nextAlpha(),
                random.nextColor(),
                random.nextColor(),
                random.nextColor()
            ), BlendModeCompat.SRC_IN
        )
        val innerRingColorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.argb(
                random.nextAlpha(),
                random.nextColor(),
                random.nextColor(),
                random.nextColor()
            ), BlendModeCompat.SRC_IN
        )
        val craterCount = random.nextInt(3, 20)
        val craterConfigs = (0 until 25).map {
            CraterConfig(random.nextInt(3, 7), random.nextDouble(0.0, 2 * PI), random.nextFloat())
        }
    }

    data class CraterConfig(val r: Int, val a: Double, val rv: Float)
}