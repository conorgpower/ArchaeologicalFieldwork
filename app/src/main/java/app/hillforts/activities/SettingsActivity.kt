package app.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import app.hillforts.R
import app.hillforts.main.MainApp
import app.hillforts.models.UserModel

class SettingsActivity : AppCompatActivity() {

    lateinit var app : MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        app = application as MainApp
        user = app.appUser

        var email: EditText = findViewById(R.id.settings_email)
        var password: EditText = findViewById(R.id.settings_password)
        var home: Button = findViewById(R.id.settings_home)
        var update: Button = findViewById(R.id.settings_update)
        var delete: Button = findViewById(R.id.settings_delete)
        var logout: Button = findViewById(R.id.settings_logout)
        var totalPosts: TextView = findViewById(R.id.settings_totalPosts)
        var totalUsers: TextView = findViewById(R.id.settings_totalUsers)
        var totalSitesVisited: TextView = findViewById(R.id.settings_totalSitesVisited)
        var userTotalPosts: TextView = findViewById(R.id.settings_userPosts)
        var userTotalSitesVisited: TextView = findViewById(R.id.settings_userSitesVisited)


        var visitCountUser = 0
        for (hillforts in user.hillforts){
            if (hillforts.dateVisited.length > 1){
                visitCountUser++
            }
        }

        var count = 0
        var visitCountTotal = 0
        for (user in  app.unified.findAllUsers()){
            count += user.hillforts.size
            for (hillforts in user.hillforts){
                if (hillforts.dateVisited.length > 1){
                    visitCountTotal++
                }
            }
        }

        email.setText(user.email)
        password.setText(user.password)
        totalPosts.setText("Total Posts: " + count)
        totalUsers.setText("Total Users: " + app.unified.findAllUsers().size)
        totalSitesVisited.setText("Total Sites Visited: " + visitCountTotal)
        userTotalPosts.setText("User Total Posts: " + user.hillforts.size)
        userTotalSitesVisited.setText("User Total Sites Visited: " + visitCountUser)

        home.setOnClickListener {
            val intent = Intent(applicationContext, HillfortListActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "User Logger Out!", Toast.LENGTH_LONG ).show()
        }

        delete.setOnClickListener {
            app.unified.deleteUser(user.copy())
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "User Deleted!", Toast.LENGTH_LONG ).show()
        }

        update.setOnClickListener {
            user.email = email.text.toString()
            user.password = password.text.toString()
            app.unified.updateUser(user.copy())
            Toast.makeText(applicationContext, "User Updated!", Toast.LENGTH_LONG ).show()
        }
    }
}
