package app.hillforts.main

import android.app.Application
import app.hillforts.models.HillfortMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val hillforts = HillfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Main App started")
    }
}