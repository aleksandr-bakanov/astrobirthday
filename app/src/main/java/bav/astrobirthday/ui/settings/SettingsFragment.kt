package bav.astrobirthday.ui.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import bav.astrobirthday.R
import bav.astrobirthday.ui.common.ComposeFragment
import bav.astrobirthday.ui.theme.AstroBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : ComposeFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun ComposeView.setContent() {
        setContent {
            AstroBirthdayTheme {
                SettingsScreen(viewModel)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.notificationPermissionGranted()
            } else {
                viewModel.notificationPermissionDenied()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {
                is SettingsViewModel.Event.OpenPicker -> findNavController().navigate(R.id.action_nav_settings_to_setup)
                is SettingsViewModel.Event.CheckNotificationPermission -> {
                    when {
                        ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            viewModel.notificationPermissionGranted()
                        }

                        else -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}