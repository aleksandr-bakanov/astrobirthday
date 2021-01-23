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
import bav.astrobirthday.utils.setColorFilter
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

    private val random = Random(planet.sha1().contentHashCode())
    private val showRing = random.nextBoolean()
    private val showInnerRing = random.nextBoolean()
    private val showPolarHat = random.nextBoolean()
    private val showCraters = random.nextBoolean()
    private val surfaceRotation = random.nextInt(0, 360).toFloat()
    private val ringsRotation = random.nextInt(0, 360).toFloat()
    private val showSurfaceRings = random.nextBoolean()
    private val craterPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var craters: List<Crater> = emptyList()

    data class Crater(val oval: RectF = RectF())

    init {
        with(random) {
            val bgR = nextColor()
            val bgG = nextColor()
            val bgB = nextColor()
            bg.setColorFilter(bgR, bgG, bgB)
            shadow.alpha = nextAlpha()
            val sRR = nextColor()
            val sRG = nextColor()
            val sRB = nextColor()
            surfaceRings.setColorFilter(sRR, sRG, sRB, nextAlpha())
            craterPaint.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                Color.argb(nextAlpha(from = 75, to = 100), sRR, sRG, sRB), BlendModeCompat.SRC
            )
            ring.setColorFilter(nextColor(), nextColor(), nextColor(), nextAlpha())
            innerRing.setColorFilter(nextColor(), nextColor(), nextColor(), nextAlpha())
        }
    }

    override fun draw(canvas: Canvas) {
        bg.draw(canvas)
        if (showCraters) {
            craters.forEach { crater ->
                canvas.drawOval(crater.oval, craterPaint)
            }
        }

        canvas.save()
        canvas.rotate(
            surfaceRotation,
            bounds.width().toFloat() / 2,
            bounds.height().toFloat() / 2
        )
        if (showSurfaceRings) surfaceRings.draw(canvas)
        if (showPolarHat) polarHat.draw(canvas)
        canvas.restore()

        shadow.draw(canvas)
        radialShadow.draw(canvas)

        canvas.save()
        canvas.rotate(
            ringsRotation,
            bounds.width().toFloat() / 2,
            bounds.height().toFloat() / 2
        )
        if (showRing) ring.draw(canvas)
        if (showInnerRing) innerRing.draw(canvas)
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
        val largePadding = 10.toDp(context).toInt()
        val smallPadding = 0
        val padding = when {
            showRing || showInnerRing -> largePadding
            else -> smallPadding
        }
        val bgBounds = Rect(left + padding, top + padding, right - padding, bottom - padding)
        val ringBounds = Rect(left, top, right, bottom)
        bg.bounds = bgBounds
        shadow.bounds = bgBounds
        radialShadow.bounds = bgBounds
        surfaceRings.bounds = bgBounds
        polarHat.bounds = bgBounds
        ring.bounds = ringBounds
        innerRing.bounds = ringBounds

        // Craters
        if (showCraters) {
            val boundsR = bgBounds.right - bgBounds.exactCenterX()
            val cx = bgBounds.exactCenterX()
            val cy = bgBounds.exactCenterY()
            craters = (0 until random.nextInt(3, 8)).map {
                val r = random.nextInt(3, 5).toDp(context).toInt()
                val a = random.nextDouble(0.0, 2 * PI)
                val rv = random.nextInt(r, boundsR.toInt() - r)
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

        fun Canvas.rotateRandomlyAndDraw(drawable: Drawable, random: Random, bounds: Rect) {
            save()
            rotate(
                random.nextInt(0, 360).toFloat(),
                bounds.width().toFloat() / 2,
                bounds.height().toFloat() / 2
            )
            drawable.draw(this)
            restore()
        }
    }
}