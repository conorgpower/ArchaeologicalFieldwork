package app.hillforts.views.login

import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import app.hillforts.main.MainApp
import app.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*
import app.hillforts.views.hillfortList.HillfortListView
import app.hillforts.views.signup.SignupView

class LoginPresenter(val view: LoginView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }


    fun getAllUsers() = app.unified.findAllUsers()

    fun doLogin(user: UserModel) {
        for (i in getAllUsers()) {
            if (i.email == user.email && i.password == user.password) {
                app.appUser = i
                val intent = Intent(view.applicationContext, HillfortListView::class.java)
                view.startActivity(intent)
                view.finish()
            } else {
                Toast.makeText(view.applicationContext, "Incorrect Email or Password!", Toast.LENGTH_LONG ).show()
            }
        }
    }

    fun doGoToRegister() {
        view.finish()
        val intent = Intent(view.applicationContext, SignupView::class.java)
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
}