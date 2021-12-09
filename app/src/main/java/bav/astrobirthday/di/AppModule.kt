package bav.astrobirthday.di

import androidx.datastore.preferences.createDataStore
import bav.astrobirthday.MainActivityViewModel
import bav.astrobirthday.common.InitData
import bav.astrobirthday.common.UserPreferences
import bav.astrobirthday.common.UserPreferencesImpl
import bav.astrobirthday.data.entities.PlanetFilters
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.local.PlanetDb
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel
import bav.astrobirthday.ui.exoplanets.GetExoplanets
import bav.astrobirthday.ui.favorites.FavoritesViewModel
import bav.astrobirthday.ui.favorites.GetFavorites
import bav.astrobirthday.ui.filter.FilterViewModel
import bav.astrobirthday.ui.home.HomeViewModel
import bav.astrobirthday.ui.planet.PlanetViewModel
import bav.astrobirthday.ui.settings.SettingsViewModel
import bav.astrobirthday.ui.settings.SyncPlanetsInfo
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<UserPreferences> { UserPreferencesImpl(get()) }
    single { PlanetDb.create(androidContext()) }
    single { get<PlanetDb>().planetDao() }

    single {
        Moshi.Builder().build()
    }

    single {
        androidContext().createDataStore(name = "settings")
    }

    viewModel { MainActivityViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { (planetName: String) -> PlanetViewModel(get(), planetName) }
    viewModel { ExoplanetsViewModel(get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { (filterBy: PlanetFilters, sortBy: PlanetSorting) ->
        FilterViewModel(
            get(),
            filterBy,
            sortBy
        )
    }
    factory { SyncPlanetsInfo(get(), get()) }
    factory { GetExoplanets(get()) }
    factory { GetFavorites(get()) }
    factory { InitData(get(), get()) }
}