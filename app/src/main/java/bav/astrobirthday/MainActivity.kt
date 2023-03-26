package bav.astrobirthday

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bav.astrobirthday.databinding.ActivityMainBinding
import bav.astrobirthday.ui.common.NavUiConfigurator
import bav.astrobirthday.utils.enqueuePeriodicBirthdayUpdateWorker
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity(), NavUiConfigurator {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setBackgroundResource(R.drawable.stars2k)

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController()

        lifecycleScope.launch {
            navController.currentBackStackEntryFlow
                .collect { navBackStackEntry ->
                    val isBottomNavVisible = setOf(
                        R.id.nav_setup, R.id.nav_welcome
                    ).contains(navBackStackEntry.destination.id).not()
                    binding.bottomNavView.isVisible = isBottomNavVisible
                }
        }

        setupNavigation()

        enqueuePeriodicBirthdayUpdateWorker(applicationContext)

        //setupInAppUpdates()
    }

    override fun onResume() {
        super.onResume()

        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate(appUpdateManager)
                }
            }
    }

    override fun setupToolbar(toolbar: Toolbar) {
        val navController = findNavController()
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_exoplanets,
                    R.id.nav_favorites,
                    R.id.nav_settings
                )
            )
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupNavigation() {
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun setupInAppUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                when {
                    appUpdateInfo.updatePriority() < 4
                            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
                        installStateUpdatedListener = InstallStateUpdatedListener { state ->
                            when (state.installStatus()) {
                                InstallStatus.DOWNLOADED -> {
                                    appUpdateManager.unregisterListener(installStateUpdatedListener!!)
                                    installStateUpdatedListener = null
                                    popupSnackbarForCompleteUpdate(appUpdateManager)
                                }
                                else -> Unit
                            }
                        }
                        appUpdateManager.registerListener(installStateUpdatedListener!!)
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            MY_REQUEST_CODE
                        )
                    }
                }
            }
        }
    }

    private var installStateUpdatedListener: InstallStateUpdatedListener? = null

    private fun popupSnackbarForCompleteUpdate(appUpdateManager: AppUpdateManager) {
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

    @Deprecated("Deprecated in Java")
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