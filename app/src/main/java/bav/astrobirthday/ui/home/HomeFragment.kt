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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bav.astrobirthday.R
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.ui.settings.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: В списке экзопланет справа сделать прокрутку как в контактах
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val preferences: UserPreferences by inject()
    private val adapter = SolarPlanetsAdapter()

    private var isBirthdayAlreadySetup: Boolean = preferences.userBirthday != null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            preferences.birthdayFlow.collect {
                recycler_view.isVisible = it != null
                top_app_bar.isVisible = it != null
                open_settings_button.isVisible = it == null

                if (it != null && !isBirthdayAlreadySetup) {
                    animateBarsAppearance()
                    isBirthdayAlreadySetup = true
                }
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
                name.text = planet.planet.pl_name
                age.text = planet.ageOnPlanet.toString()
                nearestBirthday.text = planet.nearestBirthday.toString()
                image.setImageResource(planet.planetType.imageResId)
            }
        }
    }

}