package bav.astrobirthday.ui.filter

import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import bav.astrobirthday.data.entities.Column
import bav.astrobirthday.data.entities.PlanetFilter.FilterFromTo
import bav.astrobirthday.databinding.ItemFilterDividerBinding
import bav.astrobirthday.databinding.ItemFilterFromToBinding
import bav.astrobirthday.databinding.ItemFilterHeaderBinding
import bav.astrobirthday.databinding.ItemFilterSelectBinding
import bav.astrobirthday.utils.toFloatOrZero
import bav.astrobirthday.utils.toPrettyString
import bav.astrobirthday.utils.updateText
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun filterHeaderDelegate() =
    adapterDelegateViewBinding<FilterItems.Header, FilterItems, ItemFilterHeaderBinding>({ layoutInflater, root ->
        ItemFilterHeaderBinding.inflate(layoutInflater, root, false)
    }) {
        bind {
            binding.title.setText(item.title)
        }
    }

fun filterDividerDelegate() =
    adapterDelegateViewBinding<FilterItems.Divider, FilterItems, ItemFilterDividerBinding>({ layoutInflater, root ->
        ItemFilterDividerBinding.inflate(layoutInflater, root, false)
    }) {}

fun filterFromToDelegate(changeListener: (Column, FilterFromTo) -> Unit) =
    adapterDelegateViewBinding<FilterItems.FromTo, FilterItems, ItemFilterFromToBinding>({ layoutInflater, root ->
        ItemFilterFromToBinding.inflate(layoutInflater, root, false)
    }) {

        fun dispatchChange(binding: ItemFilterFromToBinding) {
            changeListener(
                item.column,
                FilterFromTo(
                    binding.fromEditText.text.toString().toFloatOrZero(),
                    binding.toEditText.text.toString().toFloatOrZero()
                )
            )
        }

        binding.fromEditText.doOnTextChanged { _, _, _, _ -> dispatchChange(binding) }
        binding.toEditText.doOnTextChanged { _, _, _, _ -> dispatchChange(binding) }

        bind {
            with(binding) {
                val filter = item.filter as? FilterFromTo
                fromInputLayout.setHint(item.hintFrom)
                toInputLayout.setHint(item.hintTo)
                fromEditText.updateText(filter?.from.toPrettyString())
                toEditText.updateText(filter?.to.toPrettyString())
            }
        }
    }

fun <T> filterSelectDelegate(
    changeListener: (T) -> Unit
) =
    adapterDelegateViewBinding<FilterItems.Select<T>, FilterItems, ItemFilterSelectBinding>({ layoutInflater, root ->
        ItemFilterSelectBinding.inflate(layoutInflater, root, false)
    }) {

        bind {
            val items = item.items
            val selection = if (item.selection == -1) 0 else item.selection
            with(binding) {
                selectEdittext.updateText(items[selection].first.resolve(context))
                selectEdittext.setAdapter(ArrayAdapter(
                    root.context,
                    item.layoutRes,
                    items.map { it.first.resolve(context) }
                ))
                selectEdittext.setOnItemClickListener { _, _, position, _ ->
                    changeListener(items[position].second)
                }
            }
        }
    }