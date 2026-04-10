package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Match YOUR XML IDs
        val signupButton = findViewById<Button>(R.id.btnSignUp)
        val signinButton = findViewById<Button>(R.id.btnSignIn)

        signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        signinButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}