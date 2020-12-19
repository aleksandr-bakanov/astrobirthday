package bav.astrobirthday.common

enum class DiscoveryMethod {
    ASTROMETRY,
    DISK_KINEMATICS,
    ECLIPSE_TIMING_VARIATIONS,
    IMAGING,
    MICROLENSING,
    ORBITAL_BRIGHTNESS_MODULATION,
    PULSAR_TIMING,
    PULSATION_TIMING_VARIATIONS,
    RADIAL_VELOCITY,
    TRANSIT,
    TRANSIT_TIMING_VARIATIONS;

    companion object {
        val values = values()
    }
}