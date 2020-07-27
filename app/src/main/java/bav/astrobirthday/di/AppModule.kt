package bav.astrobirthday.di

import bav.astrobirthday.common.Preferences
import bav.astrobirthday.common.PreferencesImpl
import bav.astrobirthday.ui.home.HomeViewModel
import bav.astrobirthday.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Preferences> { PreferencesImpl(androidContext()) }

    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}