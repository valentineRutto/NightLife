package com.valentinerutto.nightlife

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Configuration
import com.valentinerutto.nightlife.di.appModule
import com.valentinerutto.nightlife.di.databaseModule
import com.valentinerutto.nightlife.di.networkingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() , Configuration.Provider{
    companion object {
        lateinit var INSTANCE: MyApplication
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        startKoin{

            androidLogger( level = Level.DEBUG)
            androidContext(this@MyApplication)
            modules(networkingModule, databaseModule, appModule)

        }

      ///]\\\  createNotificationChannel()

    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "event_channel",
            "Event Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for booked events"
        }

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}