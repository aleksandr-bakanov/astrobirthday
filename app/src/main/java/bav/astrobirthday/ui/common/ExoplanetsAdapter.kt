package bav.astrobirthday.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.R
import bav.astrobirthday.common.CommonUtils
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.utils.getAgeStringShort
import bav.astrobirthday.utils.orNa
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ExoplanetsAdapter(private val itemClickListener: (PlanetDescription) -> Unit) :
    KoinComponent, PagedListAdapter<PlanetDescription, ExoplanetsAdapter.ExoplanetViewHolder>(
    DIFF_CALLBACK
) {

    private val commonUtils: CommonUtils by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoplanetViewHolder {
        return ExoplanetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_exoplanet, parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ExoplanetViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    inner class ExoplanetViewHolder(
        itemView: View,
        private val clickListener: (PlanetDescription) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val age: TextView = itemView.findViewById(R.id.age)
        private val nearestBirthday: TextView = itemView.findViewById(R.id.nearestBirthday)
        private val image: AppCompatImageView = itemView.findViewById(R.id.image)

        fun bindTo(desc: PlanetDescription) {
            name.text = desc.planet.pl_name
            age.text = getAgeStringShort(desc.ageOnPlanet, itemView.context).orNa()
            nearestBirthday.text = commonUtils.localDateToString(desc.nearestBirthday).orNa()
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

