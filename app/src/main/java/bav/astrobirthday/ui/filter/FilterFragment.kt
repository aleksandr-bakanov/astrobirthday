package bav.astrobirthday.ui.filter

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel
import bav.astrobirthday.ui.filter.FilterViewModel.FilterEvent.ApplyChanges
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilterFragment : ComposeFragment() {

    private val exoplanetsViewModel: ExoplanetsViewModel by sharedViewModel()
    private val viewModel: FilterViewModel by viewModel {
        parametersOf(
            exoplanetsViewModel.filtering.value,
            exoplanetsViewModel.sorting.value
        )
    }

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                FilterScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { events ->
            events.peek { event ->
                when (event) {
                    is ApplyChanges -> {
                        exoplanetsViewModel.updateParameters(event.sortBy, event.filterBy)
                    }
                    is FilterViewModel.FilterEvent.GoBack -> {
                        findNavController().navigateUp()
                    }
                }
                true
            }
        }
    }
}