package bav.astrobirthday.ui.filter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentFilterBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel
import bav.astrobirthday.utils.hideKeyboard
import bav.astrobirthday.utils.sharedGraphViewModel
import bav.astrobirthday.utils.toFloatOrZero
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : BaseFragment<FragmentFilterBinding>(FragmentFilterBinding::inflate) {

    private val exoplanetsViewModel: ExoplanetsViewModel by sharedGraphViewModel(R.id.mobile_navigation)
    private val viewModel: FilterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filter = exoplanetsViewModel.filtering.value

        with(requireBinding()) {
            viewModel.filter.let {
                distanceFromEditText.setText(it.distanceFrom.toString())
                distanceToEditText.setText(it.distanceTo.toString())
                periodFromEditText.setText(it.periodFrom.toString())
                periodToEditText.setText(it.periodTo.toString())
            }

            distanceFromEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.setDistanceMin(text.toFloatOrZero())
            }

            distanceToEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.setDistanceMax(text.toFloatOrZero())
            }

            periodFromEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.setPeriodMin(text.toFloatOrZero())
            }

            periodToEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.setPeriodMax(text.toFloatOrZero())
            }

            viewModel.filteredPlanetsCount.onEach {
                submitButton.text = getString(R.string.submit_button_title, it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            submitButton.setOnClickListener {
                exoplanetsViewModel.setFilter(viewModel.filter)
                requireActivity().hideKeyboard()
                findNavController().popBackStack()
            }
        }
    }

}