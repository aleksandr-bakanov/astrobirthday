package bav.astrobirthday.ui.planet

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.ui.common.PlanetView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EnlargedPlanetView: DialogFragment() {

    private lateinit var planet: PlanetDescription

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.fragment_enlarged_planet_view, null)
        val planetView = view.findViewById<PlanetView>(R.id.planet_drawable)
        planetView?.setPlanet(planet)
        val d = MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .create()
        d.window?.setBackgroundDrawableResource(android.R.color.transparent);
        return d
    }

    fun setPlanet(description: PlanetDescription) {
        planet = description
    }
}