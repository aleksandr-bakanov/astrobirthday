package bav.astrobirthday.ui.setup

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.ui.common.ComposeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetupFragment : ComposeFragment() {

    private val viewModel: SetupViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent { SetupScreen(viewModel) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is SetupViewModel.Event.Close -> findNavController().popBackStack()
            }
        }
    }
}