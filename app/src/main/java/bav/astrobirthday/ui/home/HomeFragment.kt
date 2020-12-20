package bav.astrobirthday.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.R
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.ui.settings.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: возможность лайкать планеты
// TODO: В списке экзопланет справа сделать прокрутку как в контактах
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val preferences: Preferences by inject()
    private val adapter = SolarPlanetsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences.setAppBarTitle(getString(R.string.app_name))
        preferences.birthdayDate.observe(viewLifecycleOwner, {
            recycler_view.isVisible = it != null
            open_settings_button.isVisible = it == null
        })
        open_settings_button.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(parentFragmentManager, "datePicker")
        }

        recycler_view.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter

        homeViewModel.solarPlanets.observe(viewLifecycleOwner, {
            adapter.setItems(ArrayList(it))
        })
    }

    // TODO: inherit from ListAdapter
    inner class SolarPlanetsAdapter : RecyclerView.Adapter<SolarPlanetsAdapter.SolarPlanetsVH>() {

        private val items = ArrayList<PlanetDescription>()

        fun setItems(items: ArrayList<PlanetDescription>) {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarPlanetsVH {
            return SolarPlanetsVH(
                LayoutInflater.from(context).inflate(R.layout.view_grid_planet, parent, false)
            )
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: SolarPlanetsVH, position: Int) {
            holder.setData(items[position])
        }

        inner class SolarPlanetsVH(view: View) : RecyclerView.ViewHolder(view) {
            private val name: TextView = view.findViewById(R.id.name)
            private val age: TextView = view.findViewById(R.id.age)
            private val nearestBirthday: TextView = view.findViewById(R.id.nearestBirthday)
            private val image: AppCompatImageView = view.findViewById(R.id.image)

            init {
                view.findViewById<ViewGroup>(R.id.container).setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavHomeToPlanetFragment(name.text.toString())
                    findNavController().navigate(action)
                }
            }

            fun setData(planet: PlanetDescription) {
                name.text = planet.name
                age.text = planet.ageOnPlanet.toString()
                nearestBirthday.text = planet.nearestBirthday.toString()
                image.setImageResource(planet.planetType.imageResId)
            }
        }
    }

}