package app.hillforts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.hillforts.main.MainApp
import app.hillforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import app.hillforts.R

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp

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
}