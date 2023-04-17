package bav.astrobirthday.data

import bav.astrobirthday.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun setBirthday(value: LocalDate) {
        userDataSource.setBirthday(value)
    }

    override val birthdayFlow: Flow<LocalDate?>
        get() = userDataSource.birthdayFlow

    override suspend fun setSortSolarPlanetsByDate(value: Boolean) {
        userDataSource.setSortSolarPlanetsByDate(value)
    }

    override val sortSolarPlanetsByDateFlow: Flow<Boolean>
        get() = userDataSource.sortSolarPlanetsByDateFlow

    override suspend fun setNotificationsEnabled(value: Boolean) {
        userDataSource.setNotificationsEnabled(value)
    }

    override val notificationsEnabledFlow: Flow<Boolean?>
        get() = userDataSource.notificationsEnabledFlow
}