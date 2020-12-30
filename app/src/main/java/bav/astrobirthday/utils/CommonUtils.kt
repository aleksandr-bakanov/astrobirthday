package bav.astrobirthday.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import bav.astrobirthday.R
import bav.astrobirthday.common.DiscoveryMethod
import bav.astrobirthday.common.DiscoveryMethod.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Int.toDp(context: Context): Float {
    return (this / context.resources.displayMetrics.density)
}

fun String?.orNa(): String = if (this.isNullOrBlank()) "n/a" else this
fun Int?.orNa(): String = this?.toString() ?: "n/a"
fun Double?.orNa(): String = this?.toString() ?: "n/a"

fun Context.localDateToString(date: LocalDate): String {
    val dateFormat = DateTimeFormatter.ofPattern(
        "d MMM yyyy",
        ConfigurationCompat.getLocales(resources.configuration).get(0)
    )
    return dateFormat.format(date)
}

fun Context.discoveryMethodToStr(method: DiscoveryMethod?): String {
    return when (method) {
        ASTROMETRY -> getString(R.string.disc_method_astrometry)
        DISK_KINEMATICS -> getString(R.string.disc_method_disk_kinematics)
        ECLIPSE_TIMING_VARIATIONS -> getString(R.string.disc_method_eclipse_timing_variations)
        IMAGING -> getString(R.string.disc_method_imaging)
        MICROLENSING -> getString(R.string.disc_method_microlensing)
        ORBITAL_BRIGHTNESS_MODULATION -> getString(R.string.disc_method_orbital_brightness_modulation)
        PULSAR_TIMING -> getString(R.string.disc_method_pulsar_timing)
        PULSATION_TIMING_VARIATIONS -> getString(R.string.disc_method_pulsation_timing_variations)
        RADIAL_VELOCITY -> getString(R.string.disc_method_radial_velocity)
        TRANSIT -> getString(R.string.disc_method_transit)
        TRANSIT_TIMING_VARIATIONS -> getString(R.string.disc_method_transit_timing_variations)
        else -> "".orNa()
    }
}

fun Context.openUrl(url: String) {
    CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.primaryColor))
                .build()
        )
        .build()
        .launchUrl(this, Uri.parse(url))
}