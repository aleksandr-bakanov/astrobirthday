package bav.astrobirthday.ui.common

abstract class ViewEvent {

    internal var handled = false

    final override fun equals(other: Any?): Boolean {
        if (other !is ViewEvent) return false

        return other.handled == handled
    }

    final override fun hashCode(): Int = handled.hashCode()
}

fun <T : ViewEvent> T.peek(block: (event: T) -> Boolean) {
    if (!this.handled) this.handled = block(this)
}