package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        
        val mainLayout = findViewById<android.view.View>(R.id.main)
        mainLayout?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val goToLogin = findViewById<TextView>(R.id.goToLogin)

        goToLogin?.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        //on click of the signup Button, we want to register a person
        signupBtn?.setOnClickListener {
            // Specify the API endpoint
            val api = "https://kbenkamotho.alwaysdata.net/api/signup"
            // Create a RequestParams ~ it is where we are going to hold all the data
            val data = RequestParams()
            // Add/Append the username, email, password and phone on the data
            data.put("username", username.text.toString().trim())
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())
            data.put("phone", phone.text.toString().trim())

            //import the ApiHelper Class
            val helper = ApiHelper(applicationContext)

            // Inside of the helper class, access the function post
            helper.post(api, data)
        }
    }
}
