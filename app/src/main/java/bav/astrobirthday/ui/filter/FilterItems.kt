package bav.astrobirthday.ui.filter

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.Column
import bav.astrobirthday.data.entities.PlanetFilter
import bav.astrobirthday.ui.common.Resources

sealed class FilterItems {

    class Divider : FilterItems() {
        override val typeId: Int = -1
    }

    class Header(
        @StringRes val title: Int,
    ) : FilterItems() {
        override val typeId: Int
            get() = title
    }

    class FromTo(
        val column: Column,
        val filter: PlanetFilter?,
        @StringRes val hintFrom: Int = R.string.from_hint,
        @StringRes val hintTo: Int = R.string.to_hint
    ) : FilterItems() {
        override val typeId: Int
            get() = column.ordinal
    }

    class Select<T>(
        override val typeId: Int,
        val items: List<Pair<Resources.String, T>>,
        val selection: Int,
        @LayoutRes val layoutRes: Int
    ) : FilterItems()

    abstract val typeId: Int

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilterItems>() {
            override fun areItemsTheSame(oldItem: FilterItems, newItem: FilterItems) =
                oldItem.typeId == newItem.typeId

            override fun areContentsTheSame(oldItem: FilterItems, newItem: FilterItems) =
                oldItem == newItem
        }

        inline fun <reified T> createSelect(
            items: List<Pair<Resources.String, T>>,
            selection: Int,
            @LayoutRes layoutRes: Int = R.layout.view_sort_by_item
        ): Select<T> {
            return Select(T::class.hashCode(), items, selection, layoutRes)
        }
    }
}