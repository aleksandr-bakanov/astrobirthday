package bav.astrobirthday.common

enum class DiscoveryMethod {
    RADIAL_VELOCITY,
    IMAGING,
    ECLIPSE_TIMING_VARIATION,
    TRANSIT,
    ASTROMETRY;

    companion object {
        val values = values()
    }
}