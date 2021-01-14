package bav.astrobirthday.ui.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.utils.getIntAttribute
import bav.astrobirthday.utils.toDp
import bav.astrobirthday.utils.toPlanetIndex
import kotlin.math.*

class PlanetAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mainOrbitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = (context.getIntAttribute(R.attr.colorOnPrimary) and 0x00FFFFFF) or 0x55000000
        strokeWidth = 2.toDp(context)
    }

    private val planetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getIntAttribute(R.attr.colorOnPrimary)
    }
    private val planetShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.planetShadowColor)
    }

    private val secondaryOrbitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = (context.getIntAttribute(R.attr.colorOnPrimary) and 0x00FFFFFF) or 0x28000000
        strokeWidth = 1.toDp(context)
    }

    private var w: Float = 0f
    private var h: Float = 0f
    private var vOffset: Float = 0f
    private var initialW: Float = 0f
    private var initialH: Float = 0f

    private var mainPlanetEcc: Float = 0f
    private val planetOrbitRect: RectF = RectF(0f, 0f, 0f, 0f)
    private var neighboursEccs: List<Float> = emptyList()
    private var neighboursOrbitRects: List<RectF> = emptyList()
    private var starX: Float = 0f
    private var starY: Float = 0f
    private val starRadius: Float = 8.toDp(context)
    private val margin: Int = 16.toDp(context).toInt()
    private var a: Float = 0f
    private var b: Float = 0f
    private var c: Float = 0f

    private var totalPlanets = 1
    private var planetIndex = 1
    private var mainPlanetPerigee = 1f

    private var initialized = false
    private var isDataSet = false

    private var angle = 0f

    private var animator = ValueAnimator.ofFloat(0f, MAX_ANGLE).apply {
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
        vOffset = initialH - this.h
    }

    override fun onDraw(canvas: Canvas?) {
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
                it.drawCircle(starX, starY - vOffset, starRadius, planetPaint)

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

    fun getTrueAnomaly(M: Float, e: Float): Float {
        val E = giveMeJuicyE(M, e)
        val tgHalfNu = sqrt((1 + e) / (1 - e)) * tan(E / 2)
        return atan(tgHalfNu) * 2
    }

    fun giveMeJuicyE(M: Float, e: Float): Float {
        var E: Float = M
        for (i in 0..10) {
            E -= ((E - e * sin(E) - M) / (1 - e * cos(E)))
        }
        return E
    }

    fun setData(description: PlanetDescription) {
        val planet = description.planet
        this.mainPlanetEcc = planet.pl_orbeccen?.toFloat() ?: 0f
        totalPlanets = planet.sy_pnum ?: 1
        planetIndex = planet.pl_name!!.toPlanetIndex().coerceIn(1, totalPlanets)

        neighboursEccs = description.neighbours.map { it.pl_orbeccen?.toFloat() ?: 0f }

        prepareParams()
        isDataSet = true

        animator.duration = (log10(planet.pl_orbper ?: 10.0) * 10000).toLong().coerceIn(500, 60000)
        if (!animator.isStarted) {
            animator.start()
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
                ((state.getFloat("angle") / MAX_ANGLE) * animator.duration).toLong()
            super.onRestoreInstanceState(state.getParcelable("superState"))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun prepareParams() {
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

        mainPlanetPerigee = planetOrbitRect.right - starX

        val distanceBetweenOrbits = mainPlanetPerigee / planetIndex

        neighboursOrbitRects = neighboursEccs.mapIndexed { index, ecc ->
            val np = distanceBetweenOrbits * (index + 1)
            val na = np / (1 - ecc)
            val nc = na * ecc
            val nb = sqrt(na * na - nc * nc)
            RectF(starX - nc - na, starY - nb, starX + np, starY + nb)
        }

    }

    private companion object {
        const val MAX_ANGLE = 2 * PI.toFloat()
    }
}