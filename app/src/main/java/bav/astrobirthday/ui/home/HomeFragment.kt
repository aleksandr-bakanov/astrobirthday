package bav.astrobirthday.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.MainActivityViewModel
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.R
import bav.astrobirthday.common.CommonUtils
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.settings.DatePickerFragment
import bav.astrobirthday.utils.getAgeStringShort
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: В списке экзопланет справа сделать прокрутку как в контактах
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()
    private val commonUtils: CommonUtils by inject()
    private val adapter = SolarPlanetsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityViewModel.state.observe(viewLifecycleOwner) { state ->
            recycler_view.isVisible = state.barsVisible
            top_app_bar.isVisible = state.barsVisible
            open_settings_button.isVisible = !state.barsVisible
        }

        mainActivityViewModel.events.observe(viewLifecycleOwner) { events ->
            events.peek { event ->
                when (event) {
                    is AnimateBars -> {
                        animateBarsAppearance()
                    }
                }
                true
            }
        }

        open_settings_button.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(parentFragmentManager, "datePicker")
        }

        recycler_view.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter

        homeViewModel.solarPlanets.observe(viewLifecycleOwner) {
            adapter.setItems(ArrayList(it))
        }

        top_app_bar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_settings -> {
                    findNavController().navigate(R.id.nav_settings)
                    true
                }
                else -> false
            }
        }
    }

    private fun animateBarsAppearance() {
        top_app_bar.isVisible = true
        val bottomNavViewAnim = ObjectAnimator.ofFloat(
            top_app_bar,
            "translationY",
            -resources.getDimension(R.dimen.navigation_bar_height),
            0f
        )
        AnimatorSet().apply {
            playTogether(bottomNavViewAnim)
            duration = 1000L
            interpolator = BounceInterpolator()
            start()
        }
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
            private val age: TextView = view.findViewById(R.id.age)
            private val nearestBirthday: TextView = view.findViewById(R.id.nearestBirthday)
            private val image: AppCompatImageView = view.findViewById(R.id.image)

            init {
                view.findViewById<ViewGroup>(R.id.container).setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavHomeToPlanetFragment(planetName)
                    findNavController().navigate(action)
                }
            }

            private var planetName: String = ""

            fun setData(planet: PlanetDescription) {
                planetName = planet.planet.pl_name.orEmpty()
                age.text = getAgeStringShort(planet.ageOnPlanet, requireContext())
                nearestBirthday.text = commonUtils.localDateToString(planet.nearestBirthday)
                image.setImageResource(planet.planetType.imageResId)
            }
        }
    }

}