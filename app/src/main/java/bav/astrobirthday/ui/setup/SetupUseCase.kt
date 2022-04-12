package bav.astrobirthday.ui.setup

import bav.astrobirthday.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class SetupUseCase(
    private val userRepository: UserRepository
) {
    val isBirthdaySet: Boolean
    get() {
        return runBlocking { userRepository.birthdayFlow.first() != null }
    }
}