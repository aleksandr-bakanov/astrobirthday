package bav.astrobirthday.di

import bav.astrobirthday.data.BirthdayUpdater
import bav.astrobirthday.data.SolarPlanetsDataSource
import bav.astrobirthday.data.SolarPlanetsRepositoryImpl
import bav.astrobirthday.data.SyncPlanetsInfo
import bav.astrobirthday.data.UserDataSource
import bav.astrobirthday.data.UserRepositoryImpl
import bav.astrobirthday.data.entities.PlanetFilters
import bav.astrobirthday.data.entities.PlanetSorting
import bav.astrobirthday.data.local.PlanetDb
import bav.astrobirthday.domain.SolarPlanetsRepository
import bav.astrobirthday.domain.UserRepository
import bav.astrobirthday.ui.exoplanets.ExoplanetsViewModel
import bav.astrobirthday.ui.exoplanets.GetExoplanets
import bav.astrobirthday.ui.favorites.FavoritesViewModel
import bav.astrobirthday.ui.favorites.GetFavorites
import bav.astrobirthday.ui.filter.FilterViewModel
import bav.astrobirthday.ui.home.HomeViewModel
import bav.astrobirthday.ui.planet.PlanetViewModel
import bav.astrobirthday.ui.settings.SettingsViewModel
import bav.astrobirthday.ui.setup.SetupUseCase
import bav.astrobirthday.ui.setup.SetupViewModel
import bav.astrobirthday.ui.welcome.WelcomeViewModel
import bav.astrobirthday.utils.NotificationHelper
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single(named("IoDispatcher")) {
        Dispatchers.IO
    }

    single { NotificationHelper(get(), androidContext()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<SolarPlanetsRepository> { SolarPlanetsRepositoryImpl(get(), get()) }
    single { PlanetDb.create(androidContext()) }
    single { get<PlanetDb>().planetDao() }
    factory { UserDataSource(get()) }
    factory { SolarPlanetsDataSource(get()) }

    single {
        Moshi.Builder().build()
    }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { (planetName: String) -> PlanetViewModel(get(), get(), planetName) }
    viewModel { ExoplanetsViewModel(get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { (filterBy: PlanetFilters, sortBy: PlanetSorting) ->
        FilterViewModel(
            filterBy,
            sortBy
        )
    }
    viewModel { SetupViewModel(get(), get()) }
    viewModel { WelcomeViewModel(get(), get(), get(named("IoDispatcher"))) }

    factory { SyncPlanetsInfo(get(), get(), get(), get(), androidContext()) }
    factory { GetExoplanets(get()) }
    factory { GetFavorites(get()) }
    factory { SetupUseCase(get()) }
    factory { BirthdayUpdater(androidContext()) }
}