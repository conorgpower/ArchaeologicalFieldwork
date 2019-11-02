package app.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.hillforts.R
import kotlinx.android.synthetic.main.activity_login.*
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import app.hillforts.main.MainApp
import app.hillforts.models.HillfortModel

class LoginActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var showHideBtn:Button = findViewById(R.id.showHideBtn)
        var password:EditText = findViewById(R.id.input_password)

        showHideBtn.setOnClickListener {
            if (showHideBtn.getText().toString().equals("Show Password")) {
                showHideBtn.setText("Hide Password")
                password.setTransformationMethod(null)
            } else {
                showHideBtn.setText("Show Password")
                password.setTransformationMethod(PasswordTransformationMethod())
            }
        }

        btnLogin.setOnClickListener() {
            val intent = Intent(applicationContext, HillfortListActivity::class.java)
            startActivity(intent)
            finish()
        }

        linkSignup.setOnClickListener() {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
