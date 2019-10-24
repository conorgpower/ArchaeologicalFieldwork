package app.hillforts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.hillforts.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    val hillforts = ArrayList<HillfortModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            if (hillfort.title.isNotEmpty()) {
                hillforts.add(hillfort)
                info("add Button Pressed: ${hillfort}")
                for (i in hillforts.indices) {
                    info("Hillfort[$i]:${this.hillforts[i]}")
                }
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}
