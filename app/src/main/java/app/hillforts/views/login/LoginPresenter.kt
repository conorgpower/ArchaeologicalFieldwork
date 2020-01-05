package app.hillforts.views.login

import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import app.hillforts.main.MainApp
import app.hillforts.models.HillfortFireStore
import app.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*
import app.hillforts.views.hillfortList.HillfortListView
import app.hillforts.views.signup.SignupView
import org.jetbrains.anko.toast

class LoginPresenter(val view: LoginView) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    var app: MainApp

    init {
        app = view.application as MainApp
        if(app.unified is HillfortFireStore) {
            fireStore = app.unified as HillfortFireStore
        }
    }

    fun doLogin(user: UserModel) {
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener(view!!) { task ->
            if(task.isSuccessful) {
                if(fireStore != null) {
                    fireStore!!.fetchHillforts {
                        app.appUser = user
                        val intent = Intent(view.applicationContext, HillfortListView::class.java)
                        view.startActivity(intent)
                        view.finish()
                    }
                } else {
                    app.appUser = user
                    val intent = Intent(view.applicationContext, HillfortListView::class.java)
                    view.startActivity(intent)
                    view.finish()
                }
            } else {
                view?.toast("Sign Up Failed: ${task.exception?.message}")
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