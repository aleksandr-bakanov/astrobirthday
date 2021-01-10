package bav.astrobirthday.ui.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class PlanetAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 4.0f
    }

    private var w: Float = 0f
    private var h: Float = 0f
    private var vOffset: Float = 0f
    private var initialW: Float = 0f
    private var initialH: Float = 0f

    private var ecc: Float = 0f
    private val orbitRect: RectF = RectF(0f, 0f, 0f, 0f)
    private var starX: Float = 0f
    private var starY: Float = 0f
    private val starRadius: Float = 10f
    private val margin: Int = 64
    private var a: Float = 0f
    private var b: Float = 0f
    private var c: Float = 0f

    private var initialized = false
    private var isDataSet = false

    private var angle = 0f

    private var animator = ValueAnimator.ofFloat(0f, 2 * PI.toFloat()).apply {
        duration = 3000
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
        }
        vOffset = initialH - this.h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isDataSet) {
            canvas?.let {
                it.drawOval(orbitRect.left, orbitRect.top - vOffset, orbitRect.right, orbitRect.bottom - vOffset, paint)
                it.drawCircle(starX, starY - vOffset, starRadius, paint)
                it.drawLine(w / 2 - starRadius, starY - vOffset, w / 2 + starRadius, starY - vOffset, paint)
                it.drawLine(w / 2, starY - vOffset - starRadius, w / 2, starY - vOffset + starRadius, paint)

                val px = a * cos(angle) + w / 2
                val py = b * sin(angle) + (starY - vOffset)
                it.drawCircle(px, py, starRadius, paint)
            }
        }
    }

    fun setData(ecc: Float) {
        this.ecc = ecc
        prepareParams()
        isDataSet = true
        animator.start()
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

        orbitRect.set((initialW / 2) - a, (initialH / 2) - b, (initialW / 2) + a, (initialH / 2) + b)

        starX = (initialW / 2) - c
        starY = initialH / 2

        invalidate()
    }
}