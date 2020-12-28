package bav.astrobirthday.ui.exoplanets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.R
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.Planet
import bav.astrobirthday.utils.planetToPlanetDescription
import kotlinx.android.synthetic.main.fragment_exoplanets.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

class ExoplanetsFragment : Fragment(R.layout.fragment_exoplanets) {

    private val viewModel: ExoplanetsViewModel by viewModel()
    private val preferences: UserPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExoplanetsAdapter()
        viewModel.planetsList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        recyclerView.adapter = adapter
    }

    inner class ExoplanetsAdapter :
        PagedListAdapter<Planet, ExoplanetsAdapter.ExoplanetViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoplanetViewHolder {
            return ExoplanetViewHolder(
                LayoutInflater.from(context).inflate(R.layout.view_item_exoplanet, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ExoplanetViewHolder, position: Int) {
            val planet: Planet? = getItem(position)
            holder.bindTo(planet)
        }

        inner class ExoplanetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val name: TextView = itemView.findViewById(R.id.name)
            private val age: TextView = itemView.findViewById(R.id.age)
            private val nearestBirthday: TextView = itemView.findViewById(R.id.nearestBirthday)
            private val image: AppCompatImageView = itemView.findViewById(R.id.image)

            fun bindTo(planet: Planet?) {
                val desc =
                    planetToPlanetDescription(planet, preferences.userBirthday ?: LocalDate.now())
                name.text = desc.planet.pl_name
                age.text = desc.ageOnPlanet.toString()
                nearestBirthday.text = desc.nearestBirthday.toString()
                image.setImageResource(desc.planetType.imageResId)
                itemView.setOnClickListener {
                    val action =
                        ExoplanetsFragmentDirections.actionNavExoplanetsToPlanetFragment(name.text.toString())
                    findNavController().navigate(action)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Planet>() {
            override fun areItemsTheSame(
                oldPlanet: Planet,
                newPlanet: Planet
            ) = oldPlanet.id == newPlanet.id

            override fun areContentsTheSame(
                oldPlanet: Planet,
                newPlanet: Planet
            ) = oldPlanet == newPlanet
        }
    }

}
