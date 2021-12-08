package bav.astrobirthday

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bav.astrobirthday.MainActivityViewModel.MainViewEvent.AnimateBars
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.databinding.ActivityMainBinding
import bav.astrobirthday.ui.common.NavUiConfigurator
import bav.astrobirthday.ui.common.peek
import bav.astrobirthday.utils.enqueuePeriodicBirthdayUpdateWorker
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), NavUiConfigurator {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModel()
    private val preferences: UserPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        viewModel.state.observe(this) { state ->
            binding.bottomNavView.isVisible = state.barsVisible
        }

        viewModel.events.observe(this) { events ->
            events.peek { event ->
                when (event) {
                    is AnimateBars -> {
                        animateBarsAppearance()
                        false
                    }
                }
            }
        }

        enqueuePeriodicBirthdayUpdateWorker(applicationContext)

        setupInAppUpdates()
    }

    // Checks that the update is not stalled during 'onResume()'.
    // However, you should execute this check at all app entry points.
    override fun onResume() {
        super.onResume()

        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                val isImmediate = preferences.getImmediate()
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                    && isImmediate
                ) {
                    Timber.e(
                        "cqhg43: onResume: UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS"
                    )
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    )
                }
                // If the update is downloaded but not installed,
                // notify the user to complete the update.
                else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    Timber.e("cqhg43: onResume: InstallStatus.DOWNLOADED")
                    popupSnackbarForCompleteUpdate(appUpdateManager)
                }
            }
    }

    override fun setupToolbar(toolbar: Toolbar) {
        val navController = findNavController()
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_exoplanets, R.id.nav_favorites))
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun animateBarsAppearance() = with(binding) {
        bottomNavView.isVisible = true
        val bottomNavViewAnim = ObjectAnimator.ofFloat(
            bottomNavView,
            "translationY",
            resources.getDimension(R.dimen.navigation_bar_height),
            0f
        )
        AnimatorSet().apply {
            playTogether(bottomNavViewAnim)
            duration = 1000L
            interpolator = BounceInterpolator()
            start()
        }
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupNavigation() {
        val navController = findNavController()
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun setupInAppUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        Timber.e("cqhg43: manager created")

        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            Timber.e(
                "cqhg43 OnSuccessListener: \n" +
                        "  cqhg43 updatePriority = ${appUpdateInfo.updatePriority()}\n" +
                        "  cqhg43 installStatus = ${appUpdateInfo.installStatus()}\n" +
                        "  cqhg43 updateAvailability = ${appUpdateInfo.updateAvailability()}\n" +
                        "  cqhg43 clientVersionStalenessDays = ${appUpdateInfo.clientVersionStalenessDays()}\n" +
                        "  cqhg43 isUpdateTypeAllowed IMMEDIATE = ${
                            appUpdateInfo.isUpdateTypeAllowed(
                                AppUpdateType.IMMEDIATE
                            )
                        }\n" +
                        "  cqhg43 isUpdateTypeAllowed FLEXIBLE = ${
                            appUpdateInfo.isUpdateTypeAllowed(
                                AppUpdateType.FLEXIBLE
                            )
                        }\n" +
                        "  cqhg43 availableVersionCode = ${appUpdateInfo.availableVersionCode()}"
            )
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                when {
                    appUpdateInfo.updatePriority() < 4 /* high priority */
                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                        Timber.e("cqhg43: High priority: request immediate update")
                        // Request the update.
                        preferences.setImmediate(true)
                        appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE
                        )
                    }
                    appUpdateInfo.updatePriority() < 4
                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
                        Timber.e("cqhg43: Low priority: request flexible update")
                        // Create a listener to track request state updates.
                        installStateUpdatedListener = InstallStateUpdatedListener { state ->
                            // (Optional) Provide a download progress bar.
                            when (state.installStatus()) {
                                InstallStatus.DOWNLOADING -> {
                                    val bytesDownloaded = state.bytesDownloaded()
                                    val totalBytesToDownload = state.totalBytesToDownload()
                                    // Show update progress bar.
                                    Timber.e(
                                        "cqhg43: Downloading update InstallStatus.DOWNLOADING: $bytesDownloaded / $totalBytesToDownload bytes"
                                    )
                                }
                                InstallStatus.DOWNLOADED -> {
                                    Timber.e("cqhg43: Install update InstallStatus.DOWNLOADED")
                                    // When status updates are no longer needed, unregister the listener.
                                    appUpdateManager.unregisterListener(installStateUpdatedListener!!)
                                    installStateUpdatedListener = null
                                    // After the update is downloaded, show a notification
                                    // and request user confirmation to restart the app.
                                    popupSnackbarForCompleteUpdate(appUpdateManager)
                                }
                                else -> {
                                    Timber.e(
                                        "cqhg43: Other install status: ${state.installStatus()}"
                                    )
                                }
                            }
                        }

                        // Before starting an update, register a listener for updates.
                        appUpdateManager.registerListener(installStateUpdatedListener!!)

                        // Start an update.
                        appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.FLEXIBLE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE
                        )
                    }
                }
            } else {
                preferences.setImmediate(false)
            }
        }

        appUpdateInfoTask.addOnFailureListener {
            Timber.e("cqhg43 OnFailureListener: exception = $it")
        }

    }

    private var installStateUpdatedListener: InstallStateUpdatedListener? = null

    // Displays the snackbar notification and call to action.
    private fun popupSnackbarForCompleteUpdate(appUpdateManager: AppUpdateManager) {
        Timber.e("cqhg43 popupSnackbarForCompleteUpdate called")
        Snackbar.make(
            findViewById(R.id.activity_main_layout),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") {
                appUpdateManager.completeUpdate()
            }
            setActionTextColor(ContextCompat.getColor(context, R.color.secondaryColor))
            show()
        }
    }

    private val MY_REQUEST_CODE = 12345

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Timber.e("cqhg43: Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            } else {
                Timber.e("cqhg43: Update flow success! Result code: $resultCode")
            }
        }
    }

}