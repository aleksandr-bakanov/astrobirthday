package bav.astrobirthday.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.TypedValue
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.os.ConfigurationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import bav.astrobirthday.R
import bav.astrobirthday.common.DiscoveryMethod
import bav.astrobirthday.common.DiscoveryMethod.ASTROMETRY
import bav.astrobirthday.common.DiscoveryMethod.DISK_KINEMATICS
import bav.astrobirthday.common.DiscoveryMethod.ECLIPSE_TIMING_VARIATIONS
import bav.astrobirthday.common.DiscoveryMethod.IMAGING
import bav.astrobirthday.common.DiscoveryMethod.MICROLENSING
import bav.astrobirthday.common.DiscoveryMethod.ORBITAL_BRIGHTNESS_MODULATION
import bav.astrobirthday.common.DiscoveryMethod.PULSAR_TIMING
import bav.astrobirthday.common.DiscoveryMethod.PULSATION_TIMING_VARIATIONS
import bav.astrobirthday.common.DiscoveryMethod.RADIAL_VELOCITY
import bav.astrobirthday.common.DiscoveryMethod.TRANSIT
import bav.astrobirthday.common.DiscoveryMethod.TRANSIT_TIMING_VARIATIONS
import bav.astrobirthday.data.BirthdayUpdateWorker
import bav.astrobirthday.data.entities.Config
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

fun Int.toPx(context: Context): Float {
    return (this * context.resources.displayMetrics.density)
}

fun String?.orNa(): String = if (this.isNullOrBlank()) "n/a" else this
fun Int?.orNa(): String = this?.toString() ?: "n/a"
fun Double?.orNa(): String = this?.toString() ?: "n/a"
fun CharSequence?.toFloatOrZero(): Float = this?.toString()?.toFloatOrNull() ?: 0f
fun Float?.toPrettyString() = when {
    this == null -> "0"
    this - this.toLong() == 0f -> String.format("%d", this.toLong())
    else -> String.format("%s", this)
}

fun Context.localDateToString(date: LocalDate): String {
    val dateFormat = DateTimeFormatter.ofPattern(
        "d MMM yyyy",
        ConfigurationCompat.getLocales(resources.configuration).get(0)
    )
    return dateFormat.format(date)
}

fun String.toPlanetIndex(): Int {
    return if (Config.solarPlanetNames.contains(this))
        Config.solarPlanetNames.indexOf(this) + 1
    else
        this.substringAfterLast(" ")[0] - "a"[0]
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

fun Context.getIntAttribute(id: Int): Int {
    val value = TypedValue()
    this.theme.resolveAttribute(id, value, true)
    return value.data
}

fun Drawable.setColorFilter(
    rColor: Int,
    gColor: Int,
    bColor: Int,
    alpha: Int = 255,
    mode: BlendModeCompat = BlendModeCompat.SRC_IN
) {
    colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        Color.argb(alpha, rColor, bColor, gColor), mode
    )
}

fun getMillisUntilNextMidnight(): Long {
    val c = Calendar.getInstance()
    c.add(Calendar.DAY_OF_MONTH, 1)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)
    return c.timeInMillis - System.currentTimeMillis()
}

fun enqueuePeriodicBirthdayUpdateWorker(context: Context) {
    val periodicWorkRequest =
        PeriodicWorkRequestBuilder<BirthdayUpdateWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(getMillisUntilNextMidnight(), TimeUnit.MILLISECONDS)
            .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        BirthdayUpdateWorker.UNIQUE_WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        periodicWorkRequest
    )
}