package app.hillforts.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import app.hillforts.R
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.EditText
import app.hillforts.models.UserModel

class LoginView : AppCompatActivity() {

    lateinit var presenter: LoginPresenter
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)

        showHideBtn.setOnClickListener { presenter.doShowHide() }

        btnLogin.setOnClickListener {
            user.email = input_email.text.toString()
            user.password = input_password.text.toString()
            presenter.doLogin(user)
        }

        linkSignup.setOnClickListener { presenter.doGoToRegister() }
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
}
