package bav.astrobirthday.ui.common

import android.animation.AnimatorInflater
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageButton
import bav.astrobirthday.R


class FavoriteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    private var initialSetup = true

    init {
        stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.anim_zoom_out)
        scaleType = ScaleType.CENTER_INSIDE
    }

    fun setFavorite(isChecked: Boolean) {
        if (initialSetup) {
            initialSetup = false
            setImageResource(
                if (isChecked) R.drawable.ic_baseline_favorite_24_checked
                else R.drawable.ic_baseline_favorite_24
            )
            return
        }
        setImageResource(
            if (isChecked) R.drawable.anim_favorite_unchecked_to_checked
            else R.drawable.anim_favorite_checked_to_unchecked
        )
        (drawable as AnimatedVectorDrawable).start()
    }
}