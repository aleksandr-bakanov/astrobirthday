package bav.astrobirthday.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.data.entities.isSolar

class PlanetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    fun setPlanet(desc: PlanetDescription) {
        if (desc.isSolar) {
            setImageResource(desc.planetType.imageResId)
        } else {
            setImageDrawable(PlanetDrawable(context, desc.planet))
        }
    }
}

