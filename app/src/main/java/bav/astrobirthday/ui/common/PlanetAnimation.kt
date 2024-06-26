package bav.astrobirthday.ui.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import bav.astrobirthday.R
import bav.astrobirthday.domain.model.Planet
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.toPx
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class PlanetAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mainOrbitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color =
            (ContextCompat.getColor(context, R.color.mainOrbitColor) and 0x00FFFFFF) or 0x55000000
        strokeWidth = 2.toPx(context)
    }

    private val planetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.white2)
    }
    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.white2)
    }
    private val planetShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.planetShadowColor)
    }

    private val secondaryOrbitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = (ContextCompat.getColor(
            context,
            R.color.planetShadowColor
        ) and 0x00FFFFFF) or 0x28000000
        strokeWidth = 1.toPx(context)
    }

    private var w: Float = 0f
    private var h: Float = 0f
    private var vOffset: Float = 0f
    private var initialW: Float = 0f
    private var initialH: Float = 0f

    private var mainPlanet: Planet? = null
    private var mainPlanetEcc: Float = 0f
    private val planetOrbitRect: RectF = RectF(0f, 0f, 0f, 0f)
    private var neighbours: List<Planet> = emptyList()
    private var neighboursEccs: List<Float> = emptyList()
    private var neighboursSemiMajorAxes: List<Float> = emptyList()
    private var neighboursOrbitRects: List<RectF> = emptyList()
    private var starX: Float = 0f
    private var starY: Float = 0f
    private val starPath = Path()
    private val bufferStarPath = Path()
    private val starRadius: Float = 8.toPx(context)
    private val margin: Int = 16.toPx(context).toInt()
    private var a: Float = 0f
    private var b: Float = 0f
    private var c: Float = 0f

    private var initialized = false
    private var isDataSet = false

    private var angle = START_ANGLE

    private var animator = ValueAnimator.ofFloat(START_ANGLE, FINISH_ANGLE).apply {
        addUpdateListener {
            angle = it.animatedValue as Float
            invalidate()
        }
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat() - (paddingLeft + paddingRight).toFloat()
        this.h = h.toFloat() - (paddingTop + paddingBottom).toFloat()
        if (!initialized) {
            initialW = this.w
            initialH = this.h
            initialized = true
            prepareParams()
        }
        vOffset = initialH - this.h + 56.toPx(context)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isDataSet) {
            canvas?.let {
                // Secondary orbits
                for (so in neighboursOrbitRects) {
                    it.drawOval(
                        so.left,
                        so.top - vOffset,
                        so.right,
                        so.bottom - vOffset,
                        secondaryOrbitPaint
                    )
                }

                // Main planet orbit
                it.drawOval(
                    planetOrbitRect.left,
                    planetOrbitRect.top - vOffset,
                    planetOrbitRect.right,
                    planetOrbitRect.bottom - vOffset,
                    mainOrbitPaint
                )

                // Star
                starPath.offset(starX, starY - vOffset, bufferStarPath)
                it.drawPath(bufferStarPath, starPaint)

                // Main planet
                val trueAnomaly = getTrueAnomaly(angle, mainPlanetEcc)
                val px = a * cos(trueAnomaly) + w / 2
                val py = b * sin(trueAnomaly) + (starY - vOffset)

                it.drawCircle(px, py, starRadius, planetShadowPaint)

                val sxInPc = starX - px
                val syInPc = (starY - vOffset) - py
                val sAngle = atan2(syInPc, sxInPc)
                it.drawCircle(
                    px + (4 * cos(sAngle)),
                    py + (4 * sin(sAngle)),
                    starRadius / 1.5f,
                    planetPaint
                )
            }
        }
    }

    private fun makeStarPath(
        cx: Float,
        cy: Float,
        r: Float,
        spikeDepth: Float, // [0f..1f]
        spikes: Int,
        path: Path
    ) {
        val angleStep = PI.toFloat() / spikes
        val rInner = r * spikeDepth
        var angle = -PI.toFloat() / 2f
        path.reset()
        path.moveTo(cx + cos(angle) * r, cy + sin(angle) * r)
        for (i in 0 until spikes) {
            angle += angleStep
            path.lineTo(cx + cos(angle) * rInner, cy + sin(angle) * rInner)
            angle += angleStep
            path.lineTo(cx + cos(angle) * r, cy + sin(angle) * r)
        }
    }

    private fun getTrueAnomaly(M: Float, e: Float): Float {
        val E = giveMeJuicyE(M, e)
        val tgHalfNu = sqrt((1 + e) / (1 - e)) * tan(E / 2)
        return atan(tgHalfNu) * 2
    }

    private fun giveMeJuicyE(M: Float, e: Float): Float {
        var E: Float = M
        for (i in 0..10) {
            E -= ((E - e * sin(E) - M) / (1 - e * cos(E)))
        }
        return E
    }

    fun setData(description: PlanetAndInfo) {
        mainPlanet = description.planet
        mainPlanet?.let { planet ->
            this.mainPlanetEcc = planet.planetOrbitEccentricity?.toFloat() ?: 0f

            neighbours = description.neighbours
            neighboursEccs =
                description.neighbours.map { it.planetOrbitEccentricity?.toFloat() ?: 0f }
            neighboursSemiMajorAxes =
                description.neighbours.map { it.planetOrbitSemiMajorAxis?.toFloat() ?: 0f }

            prepareParams()
            isDataSet = true

            animator.duration =
                (log10(planet.planetOrbitalPeriod ?: 10.0) * 10000).toLong().coerceIn(500, 60000)
            if (!animator.isStarted) {
                animator.start()
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable("superState", super.onSaveInstanceState())
            putFloat("angle", angle)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            animator.currentPlayTime =
                (((state.getFloat("angle") - START_ANGLE) / MAX_ANGLE) * animator.duration).toLong()
            super.onRestoreInstanceState(state.getParcelable("superState"))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun prepareParams() {
        mainPlanet?.let { planet ->
            a = (initialW / 2) - margin
            c = a * mainPlanetEcc
            b = sqrt(a * a - c * c)

            if ((b + margin) * 2 > initialH) {
                val coef = initialH / ((b + margin) * 2)
                a *= coef
                b *= coef
                c *= coef
            }

            planetOrbitRect.set(
                (initialW / 2) - a,
                (initialH / 2) - b,
                (initialW / 2) + a,
                (initialH / 2) + b
            )

            starX = (initialW / 2) + c
            starY = initialH / 2
            makeStarPath(0f, 0f, starRadius, 0.5f, 9, starPath)

            val auInPx = max(1f, a / (planet.planetOrbitSemiMajorAxis?.toFloat() ?: 1f))

            neighboursOrbitRects =
                neighbours.filter { it.planetName != mainPlanet?.planetName }.map { neighbour ->
                    val ecc = neighbour.planetOrbitEccentricity?.toFloat() ?: 0f
                    val aInAu = neighbour.planetOrbitSemiMajorAxis?.toFloat() ?: 1f
                    val na = aInAu * auInPx
                    val nc = na * ecc
                    val np = na - nc
                    val nb = sqrt(na * na - nc * nc)
                    RectF(starX - nc - na, starY - nb, starX + np, starY + nb)
                }
        }
    }

    private companion object {
        const val MAX_ANGLE = 2 * PI.toFloat()

        const val START_ANGLE = PI.toFloat() + (PI.toFloat() / 2f)
        const val FINISH_ANGLE = 3 * PI.toFloat() + (PI.toFloat() / 2f)
    }
}