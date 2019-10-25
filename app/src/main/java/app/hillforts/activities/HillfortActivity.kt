package app.hillforts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import app.hillforts.main.MainApp
import app.hillforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import app.hillforts.R
import org.jetbrains.anko.startActivityForResult

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Placemark Activity started..")

        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            if (hillfort.title.isNotEmpty()) {
                app.hillforts.add(hillfort.copy())
                info("add Button Pressed: $hillfort")
                for (i in app.hillforts.indices) {
                    info("Hillfort[$i]:${app.hillforts[i]}")
                }
                setResult(RESULT_OK)
                finish()
            } else {
                toast("Please Enter a title")
            }
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
        }
        return super.onOptionsItemSelected(item)
    }
}
