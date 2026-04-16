package com.example.sokogarden

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var signupBtn: Button
    lateinit var signinBtn: Button
    lateinit var welcomeText: TextView
    lateinit var logoutBtn: Button
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        signupBtn = findViewById(R.id.btnSignUp)
        signinBtn = findViewById(R.id.btnSignIn)
        welcomeText = findViewById(R.id.welcomeText)
        logoutBtn = findViewById(R.id.logoutBtn)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        // Setup RecyclerView
        productAdapter = ProductAdapter(emptyList())
        recyclerView.adapter = productAdapter

        // Check Login Session
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = prefs.getString("username", null)

        if (username != null) {
            // ✅ User is logged in
            welcomeText.text = "Welcome $username"
            welcomeText.visibility = View.VISIBLE
            logoutBtn.visibility = View.VISIBLE
            findViewById<View>(R.id.buttonsContainer).visibility = View.GONE
        } else {
            welcomeText.visibility = View.GONE
            logoutBtn.visibility = View.GONE
            findViewById<View>(R.id.buttonsContainer).visibility = View.VISIBLE
        }

        // Navigation
        signupBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        signinBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        // Logout logic
        logoutBtn.setOnClickListener {
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Load dynamic product data
        fetchProducts()
    }

    private fun fetchProducts() {
        val apiHelper = ApiHelper(this)
        // Updated URL
        val url = "https://bensontekes.alwaysdata.net/api/get_products"
        
        progressBar.visibility = View.VISIBLE
        
        apiHelper.get(url, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {
                progressBar.visibility = View.GONE
                result?.let {
                    // Use the helper function from ProductAdapter to parse the list
                    val productList = ProductAdapter.fromJsonArray(it)
                    productAdapter.updateList(productList)
                }
            }

            override fun onSuccess(result: JSONObject?) {
                progressBar.visibility = View.GONE
            }

            override fun onFailure(result: String?) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error loading products: $result", Toast.LENGTH_SHORT).show()
            }
        })
    }
}