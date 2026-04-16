package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        
        val mainLayout = findViewById<android.view.View>(R.id.main)
        mainLayout?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signinBtn = findViewById<Button>(R.id.signinBtn)
        val goToSignUp = findViewById<TextView>(R.id.goToSignUp)

        goToSignUp?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        signinBtn?.setOnClickListener {
            // Specify the API emdpoint
            val api = "https://kbenkamotho.alwaysdata.net/api/signin"
            // Create a RequestParams that will enable you to hold the data in form of a bundle/package
            val data = RequestParams()
            //Add/append/attach the email and the password
            data.put("email", email.text.toString())
            data.put("password", password.text.toString())
            //Import the API helper
            val helper = ApiHelper(applicationContext)
            //By use of the function post_login inside of the helper class, post your data
            helper.post_login(api, data)
        }
    }
}