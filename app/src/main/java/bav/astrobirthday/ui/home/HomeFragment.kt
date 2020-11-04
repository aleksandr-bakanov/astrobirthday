package bav.astrobirthday.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.R
import bav.astrobirthday.common.PlanetDescription
import bav.astrobirthday.db.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val adapter = SolarPlanetsAdapter()

    val moshi: Moshi by inject()
    val dao: PlanetDao by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.birthdayDate.observe(viewLifecycleOwner, Observer {
            recycler_view.isVisible = it != null
            open_settings_button.isVisible = it == null
        })
        open_settings_button.setOnClickListener {
            findNavController().navigate(R.id.nav_settings)
        }

        recycler_view.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter

        val adapter: JsonAdapter<PlanetJson> = PlanetJsonJsonAdapter(moshi)
        val db: Array<String> = resources.getStringArray(R.array.planets_array)

        lifecycleScope.launch {
            for (p in db) {
                val planet = adapter.fromJson(p)!!
                val dbp = Planet(pl_name = planet.pl_name, hostname = planet.hostname, pl_orbper = planet.pl_orbper, uid = 0)
                dao.insert(dbp)
            }
        }

        var i = 1
    }

    inner class SolarPlanetsAdapter : RecyclerView.Adapter<SolarPlanetsAdapter.SolarPlanetsVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarPlanetsVH {
            return SolarPlanetsVH(
                LayoutInflater.from(context).inflate(R.layout.view_grid_planet, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return homeViewModel.solarPlanets.value?.size ?: 0
        }

        override fun onBindViewHolder(holder: SolarPlanetsVH, position: Int) {
            homeViewModel.solarPlanets.value?.let {
                holder.setData(it[position])
            }
        }

        inner class SolarPlanetsVH(view: View) : RecyclerView.ViewHolder(view) {
            private val name: TextView = view.findViewById(R.id.name)
            private val age: TextView = view.findViewById(R.id.age)
            private val nearestBirthday: TextView = view.findViewById(R.id.nearestBirthday)
            private val image: AppCompatImageView = view.findViewById(R.id.image)

            fun setData(planet: PlanetDescription) {
                name.text = planet.name
                age.text = planet.ageOnPlanet.toString()
                nearestBirthday.text = planet.nearestBirthday.toString()
                image.setImageResource(planet.planetType.imageResId)
            }
        }
    }

}