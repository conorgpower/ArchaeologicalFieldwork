package app.hillforts.main

import android.app.Application
import app.hillforts.models.UnifiedJSONStore
import app.hillforts.models.HillfortStore
import app.hillforts.models.UnifiedStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var unified: UnifiedStore

    override fun onCreate() {
        super.onCreate()
        unified = UnifiedJSONStore(applicationContext)
        info("Main App started")
    }
}