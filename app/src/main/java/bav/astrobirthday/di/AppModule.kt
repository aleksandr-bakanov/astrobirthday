package bav.astrobirthday.di

import androidx.datastore.preferences.createDataStore
import androidx.room.Room
import bav.astrobirthday.MainActivityViewModel
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.common.UserPreferencesImpl
import bav.astrobirthday.data.local.PlanetDb
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel
import bav.astrobirthday.ui.favorites.FavoritesViewModel
import bav.astrobirthday.ui.home.HomeViewModel
import bav.astrobirthday.ui.planet.PlanetViewModel
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<UserPreferences> { UserPreferencesImpl(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            PlanetDb::class.java, "planets.db"
        )
            .createFromAsset("database/planets.db")
            .build()
            .planetDao()
    }

    single {
        Moshi.Builder().build()
    }

    single {
        androidContext().createDataStore(name = "settings")
    }

    viewModel { MainActivityViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { (planetName: String) -> PlanetViewModel(get(), get(), planetName) }
    viewModel { ExoplanetsViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
}