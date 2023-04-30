package bav.astrobirthday.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import bav.astrobirthday.R
import bav.astrobirthday.databinding.ViewItemExoplanetBinding
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.utils.getAgeStringShort
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.unicodeWrap

class ExoplanetsAdapter(
    private val itemClickListener: (PlanetAndInfo) -> Unit
) : PagingDataAdapter<PlanetAndInfo, ExoplanetsAdapter.ExoplanetViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoplanetViewHolder {
        return ExoplanetViewHolder(
            ViewItemExoplanetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ExoplanetViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    inner class ExoplanetViewHolder(
        binding: ViewItemExoplanetBinding,
        private val clickListener: (PlanetAndInfo) -> Unit
    ) : BindingViewHolder<ViewItemExoplanetBinding>(binding) {

        fun bindTo(desc: PlanetAndInfo) = with(binding) {
            val context = itemView.context
            name.text = desc.planet.planetName.unicodeWrap()
            age.text = context.getAgeStringShort(desc.ageOnPlanet)
            nextBirthday.text =
                desc.nearestBirthday?.let { context.localDateToString(it) } ?: context.getString(
                    R.string.unknown_birthday_short
                )
            image.setPlanet(desc)
            itemView.setOnClickListener { clickListener(desc) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PlanetAndInfo>() {
            override fun areItemsTheSame(
                oldPlanet: PlanetAndInfo,
                newPlanet: PlanetAndInfo
            ) = oldPlanet.planet.planetName == newPlanet.planet.planetName

            override fun areContentsTheSame(
                oldPlanet: PlanetAndInfo,
                newPlanet: PlanetAndInfo
            ) = oldPlanet == newPlanet
        }
    }
}

