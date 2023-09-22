package bav.astrobirthday.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform