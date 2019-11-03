package app.hillforts.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import app.hillforts.main.MainApp
import app.hillforts.models.HillfortModel
import app.hillforts.models.Location
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import app.hillforts.R
import app.hillforts.helpers.readImage
import app.hillforts.helpers.readImageFromPath
import app.hillforts.helpers.showImagePicker
import kotlinx.android.synthetic.main.activity_hillfort.editDateVisited
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import kotlinx.android.synthetic.main.activity_hillfort.view.*
import kotlinx.android.synthetic.main.card_hillfort.*
import org.jetbrains.anko.intentFor
import java.util.*



class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    var edit = false
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp

        val calander = Calendar.getInstance()
        val year = calander.get(Calendar.YEAR)
        val month = calander.get(Calendar.MONTH)
        val day = calander.get(Calendar.DAY_OF_MONTH)

        pickDate.setOnClickListener() {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                editDateVisited.setText("" + mDay + "/" + mMonth + "/" + mYear)
            }, year, month, day)
            dpd.show()
        }


        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        if (intent.hasExtra("hillfort edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable("hillfort edit")!!
            btnAdd.setText(R.string.save_hillfort)
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            locationEdit.setText("Lat: " + hillfort.lat + ", Long: " + hillfort.lng)
            editDateVisited.setText(hillfort.dateVisited)
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            if (hillfort.image != null) {
                chooseImage.setText(R.string.button_changeImage)
            }
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }


        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            hillfort.dateVisited = editDateVisited.text.toString()
            hillfort.userId = app.appUser.id
            if (hillfort.title.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                if (edit) {
                    app.unified.updateHillfort(app.appUser, hillfort.copy())
                } else {
                    app.unified.createHillfort(app.appUser, hillfort.copy())
                }
            }
            info("Add Button Pressed: $hillfort")
            setResult(RESULT_OK)
            finish()
        }

        hillfortLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat =  hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.unified.deleteHillfort(app.appUser, hillfort)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfort.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.button_changeImage)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }
        }
    }
}