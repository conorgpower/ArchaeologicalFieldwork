package app.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import app.hillforts.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var showHideBtn: Button = findViewById(R.id.showHideBtn)
        var password: EditText = findViewById(R.id.input_password)

        showHideBtn.setOnClickListener {
            if (showHideBtn.getText().toString().equals("Show Password")) {
                showHideBtn.setText("Hide Password")
                password.setTransformationMethod(null)
            } else {
                showHideBtn.setText("Show Password")
                password.setTransformationMethod(PasswordTransformationMethod())
            }
        }

        btnSignup.setOnClickListener() {
            toast("User Successfully Registered! Please Login!")
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        linkLogin.setOnClickListener() {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
