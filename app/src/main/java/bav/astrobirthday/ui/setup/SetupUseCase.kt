package bav.astrobirthday.ui.setup

import bav.astrobirthday.data.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class SetupUseCase(
    private val userRepository: UserRepository
) {
    suspend fun isBirthdaySet(): Boolean {
        return userRepository.birthdayFlow.firstOrNull() != null
    }
}