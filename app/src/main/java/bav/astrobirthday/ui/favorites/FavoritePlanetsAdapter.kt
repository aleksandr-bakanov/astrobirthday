package bav.astrobirthday.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import bav.astrobirthday.databinding.ViewGridFavoritePlanetBinding
import bav.astrobirthday.domain.model.PlanetAndInfo
import bav.astrobirthday.ui.common.adapter.BindingViewHolder
import bav.astrobirthday.ui.common.opengl.PlanetView3d
import bav.astrobirthday.utils.getAgeStringForMainScreen

class FavoritePlanetsAdapter(
    private val activity: FragmentActivity,
    private val itemClickListener: (PlanetAndInfo) -> Unit
) : ListAdapter<PlanetAndInfo, FavoritePlanetsAdapter.SolarPlanetsVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarPlanetsVH {
        return SolarPlanetsVH(
            ViewGridFavoritePlanetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: SolarPlanetsVH, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class SolarPlanetsVH(
        binding: ViewGridFavoritePlanetBinding,
        private val clickListener: (PlanetAndInfo) -> Unit
    ) : BindingViewHolder<ViewGridFavoritePlanetBinding>(binding) {

        fun bindTo(planet: PlanetAndInfo) = with(binding) {
            val context = itemView.context
            name.text = planet.planet.getPlanetName(context)
            age.text = context.getAgeStringForMainScreen(planet.ageOnPlanet)

//            val view = if (planet.planetType != null) {
//                val v = PlanetView(context)
//                v.setPlanet(planet)
//                v
//            } else {
//                val v = PlanetView3d(activity, planet)
//                v
//            }
            val view = PlanetView3d(activity, planet, false, false)

            planetView3d.removeAllViews()
            planetView3d.addView(view)
//            if (planetView3d.childCount == 0) {
//            }

            //image.setPlanet(planet)
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