package bav.astrobirthday.ui.common

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.utils.toPx
import com.google.android.material.button.MaterialButton

class ExpandableButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private val expandInterpolator = AccelerateDecelerateInterpolator()

    private val radiusAnimator = ValueAnimator.ofInt(
        EXPANDED_RADIUS.toPx(context).toInt(),
        COLLAPSED_RADIUS.toPx(context).toInt()
    ).apply {
        addUpdateListener {
            cornerRadius = it.animatedValue as Int
        }
        interpolator = expandInterpolator
    }

    private val showAnimator = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@ExpandableButtonView, ALPHA, 0f, 1f),
            ObjectAnimator.ofFloat(this@ExpandableButtonView, SCALE_X, 0f, 1f),
            ObjectAnimator.ofFloat(this@ExpandableButtonView, SCALE_Y, 0f, 1f)
        )
        interpolator = expandInterpolator
    }

    private val hideAnimator = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@ExpandableButtonView, ALPHA, 1f, 0f),
            ObjectAnimator.ofFloat(this@ExpandableButtonView, SCALE_X, 1f, 0f),
            ObjectAnimator.ofFloat(this@ExpandableButtonView, SCALE_Y, 1f, 0f)
        )
        interpolator = expandInterpolator
    }

    init {
        cornerRadius = EXPANDED_RADIUS.toPx(context).toInt()
    }

    private var expandWidth: Int = 0
    private var collapsedWidth: Int = minWidth

    fun bindToRecycler(recyclerView: RecyclerView) {
        recyclerView.doOnLayout {
            expandWidth = recyclerView.measuredWidth - marginStart - marginEnd
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            var scrolledDown = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(ItemTouchHelper.UP)) {
                    expand()
                    scrolledDown = true
                } else if (scrolledDown) {
                    collapse()
                    scrolledDown = false
                }
            }
        })
    }

    fun show() {
        if (!isVisible) {
            isVisible = true
            showAnimator.start()
        }
    }

    fun hide() {
        if (isVisible) {
            isVisible = false
            hideAnimator.start()
        }
    }

    private fun expand() {
        radiusAnimator.start()
        animateWidth(width, expandWidth).start()
    }

    private fun collapse() {
        radiusAnimator.reverse()
        animateWidth(width, collapsedWidth).start()
    }

    private fun animateWidth(start: Int, end: Int): ValueAnimator {
        return ValueAnimator.ofInt(
            start,
            end
        ).apply {
            addUpdateListener {
                layoutParams = layoutParams.apply {
                    width = it.animatedValue as Int
                }
            }
            interpolator = expandInterpolator
        }
    }

    private companion object {
        const val EXPANDED_RADIUS = 24
        const val COLLAPSED_RADIUS = 4
    }
}

