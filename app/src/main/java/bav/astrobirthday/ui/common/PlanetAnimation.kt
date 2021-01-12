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
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.utils.getIntAttribute
import bav.astrobirthday.utils.toDp
import bav.astrobirthday.utils.toPlanetIndex
import kotlin.math.*

class PlanetAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mainOrbitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = context.getIntAttribute(R.attr.colorOnPrimary)
        strokeWidth = 2.toDp(context)
    }

    private val planetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = context.getIntAttribute(R.attr.colorOnPrimary)
        strokeWidth = 2.toDp(context)
    }

    private val secondaryOrbitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = context.getIntAttribute(R.attr.colorOnPrimary)
        strokeWidth = 1.toDp(context)
    }

    private var w: Float = 0f
    private var h: Float = 0f
    private var vOffset: Float = 0f
    private var initialW: Float = 0f
    private var initialH: Float = 0f

    private var ecc: Float = 0f
    private val orbitRect: RectF = RectF(0f, 0f, 0f, 0f)
    private val upperOrbitRect: RectF = RectF(0f, 0f, 0f, 0f)
    private var starX: Float = 0f
    private var starY: Float = 0f
    private val starRadius: Float = 10f
    private val margin: Int = 8.toDp(context).toInt()
    private var a: Float = 0f
    private var b: Float = 0f
    private var c: Float = 0f

    private var totalPlanets = 1
    private var planetIndex = 1
    private var systemRadius = 1f

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
                //
                it.drawOval(
                    orbitRect.left,
                    orbitRect.top - vOffset,
                    orbitRect.right,
                    orbitRect.bottom - vOffset,
                    mainOrbitPaint
                )
                it.drawCircle(starX, starY - vOffset, starRadius, planetPaint)
                it.drawLine(
                    w / 2 - starRadius,
                    starY - vOffset,
                    w / 2 + starRadius,
                    starY - vOffset,
                    planetPaint
                )
                it.drawLine(
                    w / 2,
                    starY - vOffset - starRadius,
                    w / 2,
                    starY - vOffset + starRadius,
                    planetPaint
                )

                val px = a * cos(angle) + w / 2
                val py = b * sin(angle) + (starY - vOffset)
                it.drawCircle(px, py, starRadius, planetPaint)
            }
        }
    }

    fun setData(planet: Planet) {
        this.ecc = planet.pl_orbeccen?.toFloat() ?: 0f
        totalPlanets = planet.sy_pnum ?: 1
        planetIndex = planet.pl_name!!.toPlanetIndex().coerceIn(1, totalPlanets)
        prepareParams()
        isDataSet = true
        animator.duration = (log10(planet.pl_orbper ?: 10.0) * 10000).toLong().coerceIn(100, 60000)
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
            animator.setCurrentFraction(state.getFloat("angle") / MAX_ANGLE)
            super.onRestoreInstanceState(state.getParcelable("superState"))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun prepareParams() {
        a = (initialW / 2) - margin
        c = a * ecc
        b = sqrt(a * a - c * c)

        if ((b + margin) * 2 > initialH) {
            val coef = initialH / ((b + margin) * 2)
            a *= coef
            b *= coef
            c *= coef
        }

        upperOrbitRect.set(
            (initialW / 2) - a,
            (initialH / 2) - b,
            (initialW / 2) + a,
            (initialH / 2) + b
        )
        orbitRect.set(
            (initialW / 2) - a,
            (initialH / 2) - b,
            (initialW / 2) + a,
            (initialH / 2) + b
        )

        starX = (initialW / 2) - c
        starY = initialH / 2

        systemRadius = starX - upperOrbitRect.left

    }

    private companion object {
        const val MAX_ANGLE = 2 * PI.toFloat()
    }
}