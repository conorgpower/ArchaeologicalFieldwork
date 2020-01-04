package app.hillforts.views.hillfort;

import android.app.DatePickerDialog
import android.content.Intent
import org.jetbrains.anko.intentFor
import app.hillforts.helpers.showImagePicker
import app.hillforts.main.MainApp
import app.hillforts.models.Location
import app.hillforts.models.HillfortModel
import app.hillforts.views.editLocation.EditLocationView
import org.jetbrains.anko.toast
import java.util.*

class HillfortPresenter(val view: HillfortView) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var hillfort = HillfortModel()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("hillfort edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable("hillfort edit")!!
            view.showHillfort(hillfort)
        }
    }

    fun doAddOrSave(savedHillfortModel: HillfortModel) {
        if (savedHillfortModel.title.isEmpty()) {
            view.toast("Please enter a hillfort title!")
        } else {
            hillfort.title = savedHillfortModel.title
            hillfort.description = savedHillfortModel.description
            if (edit) {
                app.unified.updateHillfort(app.appUser, hillfort)
            } else {
                app.unified.createHillfort(app.appUser, hillfort)
            }
            view.finish()
        }
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.unified.deleteHillfort(app.appUser, hillfort)
        view.finish()
    }

    fun doDatePicker() {
        val calander = Calendar.getInstance()
        val year = calander.get(Calendar.YEAR)
        val month = calander.get(Calendar.MONTH)
        val day = calander.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(view, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
            hillfort.dateVisited = "" + mDay + "/" + mMonth + "/" + mYear
        }, year, month, day)
        dpd.show()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (hillfort.zoom != 0f) {
            location.lat = hillfort.lat
            location.lng = hillfort.lng
            location.zoom = hillfort.zoom
        }
        view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}
