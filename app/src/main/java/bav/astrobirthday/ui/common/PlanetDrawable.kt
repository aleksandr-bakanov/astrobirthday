package bav.astrobirthday.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.utils.sha1
import bav.astrobirthday.utils.toDp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class PlanetDrawable(private val context: Context, planet: Planet) : Drawable() {

    private val bg =
        ContextCompat.getDrawable(context, R.drawable.ic_features_background)!!.mutate()
    private val shadow =
        ContextCompat.getDrawable(context, R.drawable.ic_features_shadow)!!.mutate()
    private val radialShadow =
        ContextCompat.getDrawable(context, R.drawable.ic_features_radial_shadow)!!.mutate()
    private val surfaceRings =
        ContextCompat.getDrawable(context, R.drawable.ic_features_surface_rings)!!.mutate()
    private val polarHat =
        ContextCompat.getDrawable(context, R.drawable.ic_features_polar_hat)!!.mutate()
    private val ring =
        ContextCompat.getDrawable(context, R.drawable.ic_features_ring)!!.mutate()
    private val innerRing =
        ContextCompat.getDrawable(context, R.drawable.ic_features_inner_ring)!!.mutate()

    private val config = Config(planet)

    private val craterPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var craters: List<Crater> = emptyList()

    data class Crater(val oval: RectF = RectF())

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

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        val largePadding = 12.toDp(context).toInt()
        val mediumPadding = 7.toDp(context).toInt()
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
            craters = config.craterConfigs.map { crater ->
                val r = crater.r.toDp(context).toInt()
                val a = crater.a
                val rv = (crater.rv * (boundsR - 2 * r)) + r
                val px = rv * cos(a)
                val py = rv * sin(a)
                Crater(
                    oval = RectF(
                        (px - r).toFloat() + cx,
                        (py - r).toFloat() + cy,
                        (px + r).toFloat() + cx,
                        (py + r).toFloat() + cy
                    )
                )
            }
        }
    }

    private companion object {
        fun Random.nextColor(): Int = this.nextInt(0, 255)
        fun Random.nextAlpha(from: Int = 75, to: Int = 180): Int = this.nextInt(from, to)
        fun Random.nextBoolean(chance: Float): Boolean = this.nextFloat() < chance
    }

    class Config(planet: Planet) {
        private val random = Random(planet.pl_name.sha1().contentHashCode())

        val showRing = random.nextBoolean(0.3f)
        val showInnerRing = random.nextBoolean(0.3f)
        val showPolarHat = random.nextBoolean()
        val showCraters = random.nextBoolean()
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
        val craterCount = random.nextInt(3, 8)
        val craterConfigs = (0 until craterCount).map {
            CraterConfig(random.nextInt(3, 5), random.nextDouble(0.0, 2 * PI), random.nextFloat())
        }
    }

    data class CraterConfig(val r: Int, val a: Double, val rv: Float)
}