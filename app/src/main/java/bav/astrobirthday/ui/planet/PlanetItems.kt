package bav.astrobirthday.ui.planet

import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil

sealed class PlanetItems {

    class Reference(val link: String, val url: String) : PlanetItems() {
        override val typeId: Int = link.hashCode()
    }

    class Text(@StringRes val name: Int, val value: String) : PlanetItems() {
        override val typeId: Int = name
    }

    abstract val typeId: Int

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlanetItems>() {
            override fun areItemsTheSame(
                oldItem: PlanetItems,
                newItem: PlanetItems
            ) = oldItem.typeId == newItem.typeId

            override fun areContentsTheSame(
                oldItem: PlanetItems,
                newItem: PlanetItems
            ) = oldItem == newItem
        }
    }
}
