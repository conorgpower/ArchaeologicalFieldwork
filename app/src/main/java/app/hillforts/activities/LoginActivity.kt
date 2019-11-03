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
import app.hillforts.models.UserModel
import app.hillforts.models.HillfortModel
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        var user = UserModel()
        var password: EditText = findViewById(R.id.input_password)
        var email: EditText = findViewById(R.id.input_email)
        var showHideBtn:Button = findViewById(R.id.showHideBtn)
        val users = app.unified.findAllUsers()

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
            user.email = email.text.toString()
            user.password = password.text.toString()
            for (i in users) {
                if (i.email == user.email && i.password == user.password) {
                    app.appUser = i
                    val intent = Intent(applicationContext, HillfortListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }

        linkSignup.setOnClickListener() {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
