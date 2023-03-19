package bav.astrobirthday.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.common.opengl.getPlanetTextureId
import bav.astrobirthday.ui.common.opengl.planetImagesByTextureId

class PlanetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    fun setPlanet(desc: PlanetAndInfo) {
        if (desc.planetType != null) {
            setImageResource(desc.planetType.imageResId)
        } else {
            planetImagesByTextureId[getPlanetTextureId(desc.planet.planetName)]?.let {
                setImageResource(it)
            }
        }
    }
}

