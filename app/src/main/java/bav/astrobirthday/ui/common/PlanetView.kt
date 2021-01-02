package bav.astrobirthday.ui.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetDescription
import kotlin.math.max
import kotlin.math.min

class PlanetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_planet, this)
    }

    fun setPlanet(desc: PlanetDescription) {
        val hash = desc.planet.hashCode()
        Log.d("PlanetView", "hash = $hash")
        findViewById<ImageView>(R.id.background).setColorFilter(
            Color.rgb(
                hash.backRColor,
                hash.backGColor,
                hash.backBColor
            )
        )
        findViewById<ImageView>(R.id.shadow).alpha = hash.shadowAlpha
        findViewById<ImageView>(R.id.points).setColorFilter(
            Color.argb(
                hash.pointsA,
                hash.pointsR,
                hash.pointsG,
                hash.pointsB
            )
        )
    }
}

val Int.backRColor: Int get() = this and 255
val Int.backGColor: Int get() = (this and 0x3f80) shr 7
val Int.backBColor: Int get() = (this and 0x1fc000) shr 14

val Int.shadowAlpha: Float get() = max(min((((this and 2040) shr 3).toFloat() / 255f), 0.7f), 0.3f)

val Int.pointsV: Int get() = (this and 134217728) shr 27
val Int.pointsA: Int get() = (this and 8160) shr 5
val Int.pointsR: Int get() = (this and 8355840) shr 15
val Int.pointsG: Int get() = (this and 261120) shr 10
val Int.pointsB: Int get() = (this and 8160) shr 5

