package bav.astrobirthday.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.databinding.ViewItemExoplanetBinding
import bav.astrobirthday.utils.getAgeStringShort
import bav.astrobirthday.utils.localDateToString
import bav.astrobirthday.utils.orNa

class ExoplanetsAdapter(
    private val itemClickListener: (PlanetDescription) -> Unit
) : PagedListAdapter<PlanetDescription, ExoplanetsAdapter.ExoplanetViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoplanetViewHolder {
        return ExoplanetViewHolder(
            ViewItemExoplanetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ExoplanetViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    inner class ExoplanetViewHolder(
        binding: ViewItemExoplanetBinding,
        private val clickListener: (PlanetDescription) -> Unit
    ) : BindingViewHolder<ViewItemExoplanetBinding>(binding) {

        fun bindTo(desc: PlanetDescription) = with(binding) {
            val context = itemView.context
            name.text = desc.planet.pl_name
            age.text = context.getAgeStringShort(desc.ageOnPlanet).orNa()
            nearestBirthday.text = context.localDateToString(desc.nearestBirthday).orNa()
            image.setImageResource(desc.planetType.imageResId)
            itemView.setOnClickListener { clickListener(desc) }
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

