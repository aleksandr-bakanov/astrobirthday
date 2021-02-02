package bav.astrobirthday.ui.planet

import bav.astrobirthday.databinding.ItemPlanetDescriptionDividerBinding
import bav.astrobirthday.databinding.ItemPlanetDescriptionHeaderBinding
import bav.astrobirthday.databinding.ItemPlanetDescriptionReferenceBinding
import bav.astrobirthday.databinding.ItemPlanetDescriptionTextBinding
import bav.astrobirthday.utils.setHtml
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun planetDescriptionDividerDelegate() =
    adapterDelegateViewBinding<PlanetItems.Divider, PlanetItems, ItemPlanetDescriptionDividerBinding>(
        { layoutInflater, root ->
            ItemPlanetDescriptionDividerBinding.inflate(layoutInflater, root, false)
        }) {}

fun planetDescriptionHeaderDelegate() =
    adapterDelegateViewBinding<PlanetItems.Header, PlanetItems, ItemPlanetDescriptionHeaderBinding>(
        { layoutInflater, root ->
            ItemPlanetDescriptionHeaderBinding.inflate(layoutInflater, root, false)
        }) {
        bind {
            binding.title.setText(item.title)
        }
    }

fun planetDescriptionTextDelegate() =
    adapterDelegateViewBinding<PlanetItems.Text, PlanetItems, ItemPlanetDescriptionTextBinding>(
        { layoutInflater, root ->
            ItemPlanetDescriptionTextBinding.inflate(layoutInflater, root, false)
        }) {
        bind {
            binding.name.setText(item.name)
            binding.value.text = item.value
        }
    }

fun planetDescriptionReferenceDelegate(clickListener: (link: String) -> Unit) =
    adapterDelegateViewBinding<PlanetItems.Reference, PlanetItems, ItemPlanetDescriptionReferenceBinding>(
        { layoutInflater, root ->
            ItemPlanetDescriptionReferenceBinding.inflate(layoutInflater, root, false)
        }) {
        bind {
            binding.value.setHtml(item.link)
            binding.root.setOnClickListener { clickListener(item.url) }
        }
    }