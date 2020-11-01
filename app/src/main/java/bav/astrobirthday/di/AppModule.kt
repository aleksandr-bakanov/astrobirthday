package bav.astrobirthday.di

import androidx.room.Room
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.common.PreferencesImpl
import bav.astrobirthday.db.PlanetDao
import bav.astrobirthday.db.PlanetDb
import bav.astrobirthday.ui.home.HomeViewModel
import bav.astrobirthday.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Preferences> { PreferencesImpl(androidContext()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            PlanetDb::class.java, "planet-db"
        ).build().planetDao()
    }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
}