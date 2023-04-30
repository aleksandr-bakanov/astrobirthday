package bav.astrobirthday.ui.planet

import bav.astrobirthday.databinding.ItemPlanetDescriptionReferenceBinding
import bav.astrobirthday.databinding.ItemPlanetDescriptionTextBinding
import bav.astrobirthday.utils.unicodeWrap
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun planetDescriptionTextDelegate() =
    adapterDelegateViewBinding<PlanetItems.Text, PlanetItems, ItemPlanetDescriptionTextBinding>(
        { layoutInflater, root ->
            ItemPlanetDescriptionTextBinding.inflate(layoutInflater, root, false)
        }) {
        bind {
            binding.name.setText(item.name)
            binding.value.text = item.value.unicodeWrap()
        }
    }

fun planetDescriptionReferenceDelegate(clickListener: (link: String) -> Unit) =
    adapterDelegateViewBinding<PlanetItems.Reference, PlanetItems, ItemPlanetDescriptionReferenceBinding>(
        { layoutInflater, root ->
            ItemPlanetDescriptionReferenceBinding.inflate(layoutInflater, root, false)
        }) {
        bind {
            binding.value.text = item.link.unicodeWrap()
            binding.root.setOnClickListener { clickListener(item.url) }
        }
    }