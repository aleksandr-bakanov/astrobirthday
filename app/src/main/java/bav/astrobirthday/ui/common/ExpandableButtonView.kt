package bav.astrobirthday.ui.common

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.utils.toDp
import com.google.android.material.button.MaterialButton

class ExpandableButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private val expandInterpolator = AccelerateDecelerateInterpolator()

    private val radiusAnimator = ValueAnimator.ofInt(
        24.toDp(context).toInt(),
        4.toDp(context).toInt()
    ).apply {
        addUpdateListener {
            cornerRadius = it.animatedValue as Int
        }
        interpolator = expandInterpolator
    }

    init {
        cornerRadius = 24.toDp(context).toInt()
    }

    private var expandWidth: Int = 0
    private var collapsedWidth: Int = minWidth

    fun bindToRecycler(recyclerView: RecyclerView) {
        recyclerView.doOnLayout {
            expandWidth = recyclerView.measuredWidth - 32.toDp(context).toInt()
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
}

