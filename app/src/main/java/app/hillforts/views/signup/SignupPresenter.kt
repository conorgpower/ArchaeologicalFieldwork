package app.hillforts.views.signup

import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import app.hillforts.main.MainApp
import app.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import app.hillforts.views.login.LoginView

class SignupPresenter(val view: SignupView) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doSignup(user: UserModel) {
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(view!!) { task ->
            if(task.isSuccessful) {
                app.unified.createUser(user)
                val intent = Intent(view.applicationContext, LoginView::class.java)
                view.startActivity(intent)
                view.finish()
            } else {
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
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
}