package bav.astrobirthday.common

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import bav.astrobirthday.R
import bav.astrobirthday.common.DiscoveryMethod.*
import bav.astrobirthday.utils.orNa
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommonUtils(private val context: Context) {

    private val dateFormat = DateTimeFormatter.ofPattern(
        "d MMM yyyy",
        ConfigurationCompat.getLocales(context.resources.configuration).get(0)
    )

    fun localDateToString(date: LocalDate): String {
        return dateFormat.format(date)
    }

    fun discoveryMethodToStr(method: DiscoveryMethod?): String {
        return when (method) {
            ASTROMETRY -> context.getString(R.string.disc_method_astrometry)
            DISK_KINEMATICS -> context.getString(R.string.disc_method_disk_kinematics)
            ECLIPSE_TIMING_VARIATIONS -> context.getString(R.string.disc_method_eclipse_timing_variations)
            IMAGING -> context.getString(R.string.disc_method_imaging)
            MICROLENSING -> context.getString(R.string.disc_method_microlensing)
            ORBITAL_BRIGHTNESS_MODULATION -> context.getString(R.string.disc_method_orbital_brightness_modulation)
            PULSAR_TIMING -> context.getString(R.string.disc_method_pulsar_timing)
            PULSATION_TIMING_VARIATIONS -> context.getString(R.string.disc_method_pulsation_timing_variations)
            RADIAL_VELOCITY -> context.getString(R.string.disc_method_radial_velocity)
            TRANSIT -> context.getString(R.string.disc_method_transit)
            TRANSIT_TIMING_VARIATIONS -> context.getString(R.string.disc_method_transit_timing_variations)
            else -> "".orNa()
        }
    }

    fun openUrl(url: String, ctx: Context) {
        val builder = CustomTabsIntent.Builder()
        builder.setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(ctx, R.color.primaryColor))
                .build()
        )
        val intent = builder.build()
        intent.launchUrl(ctx, Uri.parse(url))
    }
}
