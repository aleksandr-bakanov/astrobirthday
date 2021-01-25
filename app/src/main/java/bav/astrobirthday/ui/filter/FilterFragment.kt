package bav.astrobirthday.ui.filter

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import bav.astrobirthday.R
import bav.astrobirthday.databinding.FragmentFilterBinding
import bav.astrobirthday.ui.common.BaseFragment
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel
import bav.astrobirthday.ui.filter.FilterItems.Companion.DIFF_CALLBACK
import bav.astrobirthday.ui.filter.FilterViewModel.FilterEvent.ApplyChanges
import bav.astrobirthday.utils.hideKeyboard
import bav.astrobirthday.utils.setupToolbar
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class FilterFragment : BaseFragment<FragmentFilterBinding>(FragmentFilterBinding::inflate) {

    private val exoplanetsViewModel: ExoplanetsViewModel by sharedViewModel()
    private val viewModel: FilterViewModel by viewModel {
        parametersOf(
            exoplanetsViewModel.filtering.value,
            exoplanetsViewModel.sorting.value
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireBinding()) {
            setupToolbar(topAppBar)
            val filterAdapter = AsyncListDifferDelegationAdapter(
                DIFF_CALLBACK,
                filterFromToDelegate(viewModel::onFromToChanged),
                filterSelectDelegate(viewModel::onSelectSorting),
                filterHeaderDelegate(),
                filterDividerDelegate()
            )

            val actionClear = topAppBar.menu.findItem(R.id.action_clear)
            actionClear.setOnMenuItemClickListener {
                filterContainer.requestFocus()
                requireActivity().hideKeyboard()
                viewModel.clearFilter()
                true
            }

            viewModel.state.observe(viewLifecycleOwner) { state ->
                filterAdapter.items = state.adapterItems
                applyButton.text = state.applyText.resolve(requireContext())
                actionClear.isVisible = state.clearVisible
            }

            viewModel.events.observe(viewLifecycleOwner) { events ->
                events.peek { event ->
                    when (event) {
                        is ApplyChanges -> {
                            exoplanetsViewModel.updateParameters(event.sortBy, event.filterBy)
                            requireActivity().hideKeyboard()
                            findNavController().navigateUp()
                        }
                    }
                    true
                }
            }

            recyclerView.run {
                adapter = filterAdapter
                setHasFixedSize(true)
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                applyButton.bindToRecycler(this)
            }

            applyButton.setOnClickListener {
                viewModel.onApplyClicked()
            }
        }
    }
}