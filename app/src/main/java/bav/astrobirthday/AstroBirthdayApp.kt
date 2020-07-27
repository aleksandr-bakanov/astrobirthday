package bav.astrobirthday

import android.app.Application
import bav.astrobirthday.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AstroBirthdayApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AstroBirthdayApp)
            modules(appModule)
        }
    }
}