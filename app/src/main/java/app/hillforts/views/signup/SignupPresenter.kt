package app.hillforts.views.signup

import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import app.hillforts.main.MainApp
import app.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import app.hillforts.views.login.LoginView

class SignupPresenter(val view: SignupView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doSignup(user: UserModel) {
        if (!user.email.isValidEmail()) {
            Toast.makeText(view.applicationContext, "Valid Email Required", Toast.LENGTH_LONG ).show()
        }

        if (!user.password.isValidPassword()) {
            Toast.makeText(view.applicationContext, "Valid Password Required", Toast.LENGTH_LONG ).show()
        }

        if (user.email.isValidEmail() && user.password.isValidPassword()) {
            app.unified.createUser(user)
            val intent = Intent(view.applicationContext, LoginView::class.java)
            view.startActivity(intent)
            view.finish()
            view.toast("User Successfully Registered! Please Login!")
        } else {
            view.toast("Invalid Email or Password!")
        }
    }

    fun doGoToLogin() {
        view.finish()
        val intent = Intent(view.applicationContext, LoginView::class.java)
        view.startActivity(intent)
    }

    fun doShowHide() {
        if (view.showHideBtn.getText().toString().equals("Show Password")) {
            view.showHideBtn.setText("Hide Password")
            view.input_password.setTransformationMethod(null)
        } else {
            view.showHideBtn.setText("Show Password")
            view.input_password.setTransformationMethod(PasswordTransformationMethod())
        }
    }

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isValidPassword(): Boolean
            = this.isNotEmpty() && (this.length > 7)
}