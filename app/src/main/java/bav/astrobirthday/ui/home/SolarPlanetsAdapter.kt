package bav.astrobirthday.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import bav.astrobirthday.databinding.ViewGridPlanetBinding
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.common.adapter.BindingViewHolder
import bav.astrobirthday.utils.getAgeStringForMainScreen
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.orNa

class SolarPlanetsAdapter(
    private val itemClickListener: (PlanetAndInfo) -> Unit
) : ListAdapter<PlanetAndInfo, SolarPlanetsAdapter.SolarPlanetsVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarPlanetsVH {
        return SolarPlanetsVH(
            ViewGridPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: SolarPlanetsVH, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class SolarPlanetsVH(
        binding: ViewGridPlanetBinding,
        private val clickListener: (PlanetAndInfo) -> Unit
    ) : BindingViewHolder<ViewGridPlanetBinding>(binding) {

        fun bindTo(planet: PlanetAndInfo) = with(binding) {
            val context = itemView.context
            name.text = planet.planet.getPlanetName(context)
            age.text = context.getAgeStringForMainScreen(planet.ageOnPlanet)
            nextBirthday.text =
                planet.nearestBirthday?.let { context.localDateToString(it) }.orNa()
            image.setPlanet(planet)
            itemView.setOnClickListener { clickListener(planet) }
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