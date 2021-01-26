package bav.astrobirthday.ui.planet

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetDescription
import bav.astrobirthday.ui.common.PlanetView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EnlargedPlanetView : DialogFragment() {

    private var planet: PlanetDescription? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.fragment_enlarged_planet_view, null)
        val planetView = view.findViewById<PlanetView>(R.id.planet_drawable)
        savedInstanceState?.let { planet = it.getParcelable("planet") }
        planet?.let { planetView?.setPlanet(it) }
        val d = MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .create()
        d.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return d
    }

    override fun onSaveInstanceState(outState: Bundle) {
        planet?.let { outState.putParcelable("planet", it) }
        super.onSaveInstanceState(outState)
    }

    fun setPlanet(description: PlanetDescription) {
        planet = description
    }
}