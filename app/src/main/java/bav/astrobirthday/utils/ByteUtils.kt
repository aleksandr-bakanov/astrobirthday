package bav.astrobirthday.utils

import java.security.MessageDigest

fun Any.sha1(): ByteArray {
    val messageDigest = MessageDigest.getInstance("SHA-1")
    messageDigest.update(toString().toByteArray())
    return messageDigest.digest()
}