package bav.astrobirthday.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.utils.setColorFilter
import bav.astrobirthday.utils.sha1

class PlanetDrawable(context: Context, planet: Planet) : Drawable() {

    private val bg =
        ContextCompat.getDrawable(context, R.drawable.ic_features_background)!!.mutate()
    private val points =
        ContextCompat.getDrawable(context, R.drawable.ic_features_points)!!.mutate()
    private val shadow =
        ContextCompat.getDrawable(context, R.drawable.ic_features_shadow)!!.mutate()
    private val ring =
        ContextCompat.getDrawable(context, R.drawable.ic_features_ring)!!.mutate()

    private val hash = planet.sha1().contentHashCode()

    init {
        bg.setColorFilter(hash.backRColor, hash.backGColor, hash.backBColor)
        shadow.alpha = hash.shadowAlpha
        points.setColorFilter(hash.pointsR, hash.pointsG, hash.pointsB, hash.pointsA)
        ring.setColorFilter(hash.ringR, hash.ringG, hash.ringB, hash.ringA)
    }

    override fun draw(canvas: Canvas) {
        bg.draw(canvas)
        if (hash.pointsV) points.draw(canvas)
        shadow.draw(canvas)
        if (hash.ringV) ring.draw(canvas)
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
        val padding = if (hash.ringV) 30 else 0
        val bgBounds = Rect(left + padding, top + padding, right - padding, bottom - padding)
        val ringBounds = Rect(left, top, right, bottom)
        bg.bounds = bgBounds
        points.bounds = bgBounds
        shadow.bounds = bgBounds
        ring.bounds = ringBounds
    }

    private companion object {
        fun Int.mask(mask: Int = 0b11111111, shift: Int) = (this and (mask shl shift)) shr shift

        val Int.backRColor: Int get() = this.mask(shift = 0)
        val Int.backGColor: Int get() = this.mask(shift = 7)
        val Int.backBColor: Int get() = this.mask(shift = 14)

        val Int.shadowAlpha: Int get() = this.mask(shift = 3).coerceIn(75, 180)

        val Int.pointsV: Boolean get() = this.mask(0b1, shift = 27) == 1
        val Int.pointsA: Int get() = this.mask(shift = 5)
        val Int.pointsR: Int get() = this.mask(shift = 15)
        val Int.pointsG: Int get() = this.mask(shift = 10)
        val Int.pointsB: Int get() = this.mask(shift = 5)

        val Int.ringV: Boolean get() = this.mask(0b1, shift = 29) == 1
        val Int.ringA: Int get() = this.mask(shift = 7).coerceIn(75, 180)
        val Int.ringR: Int get() = this.mask(shift = 17)
        val Int.ringG: Int get() = this.mask(shift = 12)
        val Int.ringB: Int get() = this.mask(shift = 7)
    }
}