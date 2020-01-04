package app.hillforts.views.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import app.hillforts.R
import android.widget.EditText
import app.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.input_email
import kotlinx.android.synthetic.main.activity_signup.input_password
import kotlinx.android.synthetic.main.activity_signup.showHideBtn

class SignupView : AppCompatActivity() {

    lateinit var presenter: SignupPresenter
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        presenter = SignupPresenter(this)

        showHideBtn.setOnClickListener { presenter.doShowHide() }

        btnSignup.setOnClickListener {
            user.email = input_email.text.toString()
            user.password = input_password.text.toString()
            presenter.doSignup(user)
        }

        linkLogin.setOnClickListener { presenter.doGoToLogin() }
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
