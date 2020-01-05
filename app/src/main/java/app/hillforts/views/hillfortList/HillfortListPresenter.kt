package app.hillforts.views.hillfortList

import android.content.Intent
import app.hillforts.main.MainApp
import app.hillforts.models.HillfortModel
import app.hillforts.models.UserModel
import app.hillforts.views.editLocation.HillfortMapsView
import app.hillforts.views.hillfort.HillfortView
import app.hillforts.views.login.LoginView
import app.hillforts.views.settings.SettingsView
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


class HillfortListPresenter(val view: HillfortListView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doGetHillforts() = app.unified.findAllHillforts()

    fun doAddHillfort() {
        view.startActivityForResult<HillfortView>(0)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort edit", hillfort), 0)
    }

    fun doShowHillfortsMap() {
        view.startActivity<HillfortMapsView>()
    }

    fun doShowSettings() {
        view.finish()
        val intent = Intent(view.applicationContext, SettingsView::class.java)
        view.startActivity(intent)
    }

    fun doLogout() {
        view.finish()
        FirebaseAuth.getInstance().signOut()
        app.unified.clear()
        view.startActivity<LoginView>()
    }
}