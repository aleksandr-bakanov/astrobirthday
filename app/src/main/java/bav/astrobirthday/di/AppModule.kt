package bav.astrobirthday.di

import bav.astrobirthday.MainViewModel
import bav.astrobirthday.data.BirthdayUpdater
import bav.astrobirthday.data.SyncPlanetsInfo
import bav.astrobirthday.data.UserDataSource
import bav.astrobirthday.data.UserRepository
import bav.astrobirthday.data.UserRepositoryImpl
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
import bav.astrobirthday.ui.setup.DateParseUseCase
import bav.astrobirthday.ui.setup.SetupUseCase
import bav.astrobirthday.ui.setup.SetupViewModel
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<UserRepository> { UserRepositoryImpl(get()) }
    single { PlanetDb.create(androidContext()) }
    single { get<PlanetDb>().planetDao() }
    factory { UserDataSource(get()) }

    single {
        Moshi.Builder().build()
    }

    viewModel { MainViewModel(get()) }
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
    viewModel { SetupViewModel(get(), get(), get()) }

    factory { SyncPlanetsInfo(get(), get()) }
    factory { GetExoplanets(get()) }
    factory { GetFavorites(get()) }
    factory { SetupUseCase(get()) }
    factory { DateParseUseCase() }
    factory { BirthdayUpdater(androidContext()) }
}