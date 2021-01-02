package bav.astrobirthday.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import bav.astrobirthday.data.entities.Config
import bav.astrobirthday.data.entities.PlanetDescription

class PlanetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val planetDrawable: PlanetDrawable by lazy(LazyThreadSafetyMode.NONE) {
        PlanetDrawable(context)
    }

    fun setPlanet(desc: PlanetDescription) {
        if (desc.planet.pl_name in Config.solarPlanetList) {
            setImageResource(desc.planetType.imageResId)
        } else {
            planetDrawable.setPlanet(desc.planet)
            setImageDrawable(planetDrawable)
        }
    }
}

