package app.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import app.hillforts.R
import app.hillforts.main.MainApp
import app.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.info

class SignupActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp

        var user = UserModel()
        var showHideBtn: Button = findViewById(R.id.showHideBtn)
        var password: EditText = findViewById(R.id.input_password)
        var email: EditText = findViewById(R.id.input_email)

        showHideBtn.setOnClickListener {
            if (showHideBtn.getText().toString().equals("Show Password")) {
                showHideBtn.setText("Hide Password")
                password.setTransformationMethod(null)
            } else {
                showHideBtn.setText("Show Password")
                password.setTransformationMethod(PasswordTransformationMethod())
            }
        }

        btnSignup.setOnClickListener {
            if (email.text.toString().isValidEmail()) {
                email.validate("Valid email address required") { true }
            } else {
                email.validate("Valid email address required") { false }
            }

            if (password.text.toString().isValidPassword()) {
                password.validate("At least 8 character required for password") { true }
            } else {
                password.validate("At least 8 character required for password") { false }
            }

            if (email.text.toString().isValidEmail() && password.text.toString().isValidPassword()) {
                user.email = email.toString()
                user.password = password.toString()
                app.unified.createUser(user)
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
                toast("User Successfully Registered! Please Login!")
            } else {
                toast("Invalid Email or Password!")
            }
        }

        linkLogin.setOnClickListener() {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isValidPassword(): Boolean
            = this.isNotEmpty() && (this.length > 7)


    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.afterTextChanged {
            this.error = if (validator(it)) null else message
        }
        this.error = if (validator(this.text.toString())) null else message
    }
}
