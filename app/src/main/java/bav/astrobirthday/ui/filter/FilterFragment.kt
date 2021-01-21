package bav.astrobirthday.ui.filter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.entities.SortOrder
import bav.astrobirthday.data.entities.SortableColumn
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

    private lateinit var sortingItems: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sortingItems = SortableColumn.values().flatMap { column ->
            SortOrder.values().map { order ->
                "${getString(column.resId)} ${getString(order.resId)}"
            }
        }
    }

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

            exoplanetsViewModel.sorting.value.let {
                sortingDropdownEdittext.setText("${getString(it.column.resId)} ${getString(it.order.resId)}")
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

            (sortingDropdown.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.view_sort_by_item,
                    sortingItems
                )
            )
            sortingDropdownEdittext.doOnTextChanged { text, _, _, _ ->
                exoplanetsViewModel.changeSorting(stringToPlanetSorting(text.toString()))
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

    private fun stringToPlanetSorting(value: String): PlanetSorting {
        val pair = value.split(" ")
        val column = when (pair[0]) {
            getString(R.string.sort_by_name) -> SortableColumn.NAME
            getString(R.string.sort_by_age) -> SortableColumn.AGE
            getString(R.string.sort_by_birthday) -> SortableColumn.BIRTHDAY
            else -> SortableColumn.NAME
        }
        val order = when (pair[1]) {
            getString(R.string.sort_by_asc) -> SortOrder.ASC
            getString(R.string.sort_by_desc) -> SortOrder.DESC
            else -> SortOrder.ASC
        }
        return PlanetSorting(column, order)
    }

}