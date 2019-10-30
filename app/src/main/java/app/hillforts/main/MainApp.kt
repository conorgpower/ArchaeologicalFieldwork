package app.hillforts.main

import android.app.Application
import app.hillforts.models.HillfortJSONStore
import app.hillforts.models.HillfortMemStore
import app.hillforts.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        info("Main App started")
    }
}