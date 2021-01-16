package bav.astrobirthday.ui.common

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class ScalableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialTextView(context, attrs, defStyleAttr) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pivotX = 0f
        pivotY = h / 2f
    }
}