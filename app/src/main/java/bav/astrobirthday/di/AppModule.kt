package bav.astrobirthday.di

import androidx.room.Room
import bav.astrobirthday.common.Preferences
import bav.astrobirthday.common.PreferencesImpl
import bav.astrobirthday.data.local.PlanetDb
import bav.astrobirthday.data.repository.PlanetRepository
import bav.astrobirthday.ui.home.HomeViewModel
import bav.astrobirthday.ui.settings.SettingsViewModel
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Preferences> { PreferencesImpl(androidContext()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            PlanetDb::class.java, "planet-db"
        )
            .createFromAsset("database/planet-db")
            .build()
            .planetDao()
    }

    single {
        Moshi.Builder().build()
    }

    single {
        PlanetRepository(get())
    }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
}