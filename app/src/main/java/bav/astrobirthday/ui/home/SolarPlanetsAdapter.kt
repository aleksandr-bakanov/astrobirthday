package bav.astrobirthday.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.databinding.ViewGridPlanetBinding
import bav.astrobirthday.ui.common.adapter.BindingViewHolder
import bav.astrobirthday.utils.getAgeStringForMainScreen
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.orNa

class SolarPlanetsAdapter(
    private val itemClickListener: (PlanetDescription) -> Unit
) : ListAdapter<PlanetDescription, SolarPlanetsAdapter.SolarPlanetsVH>(DIFF_CALLBACK) {

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
        private val clickListener: (PlanetDescription) -> Unit
    ) : BindingViewHolder<ViewGridPlanetBinding>(binding) {

        fun bindTo(planet: PlanetDescription) = with(binding) {
            val context = itemView.context
            age.text = context.getAgeStringForMainScreen(planet.ageOnPlanet)
            nearestBirthday.text =
                planet.nearestBirthday?.let { context.localDateToString(it) }.orNa()
            image.setPlanet(planet)
            itemView.setOnClickListener { clickListener(planet) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PlanetDescription>() {
            override fun areItemsTheSame(
                oldPlanet: PlanetDescription,
                newPlanet: PlanetDescription
            ) = oldPlanet.planet.id == newPlanet.planet.id

            override fun areContentsTheSame(
                oldPlanet: PlanetDescription,
                newPlanet: PlanetDescription
            ) = oldPlanet == newPlanet
        }
    }
}