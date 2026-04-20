package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Retrieve data from Intent
        val productName = intent.getStringExtra("product_name")
        val productCost = intent.getIntExtra("product_cost", 0)
        val productPhoto = intent.getStringExtra("product_photo")

        // Set Title and Price
        findViewById<TextView>(R.id.paymentTitle).text = "Payment for $productName"
        findViewById<TextView>(R.id.paymentAmount).text = "Amount: KSh $productCost"

        // Load Product Image using Glide
        val productImage = findViewById<ImageView>(R.id.paymentProductImage)
        val imageUrl = "https://kbenkamotho.alwaysdata.net/api/static/images/$productPhoto"
        
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(productImage)

        val phoneInput = findViewById<TextInputEditText>(R.id.phoneInput)
        val btnPay = findViewById<Button>(R.id.btnPay)

        btnPay.setOnClickListener {
            val phone = phoneInput.text.toString().trim()
            if (phone.isEmpty()) {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
            } else {
                // Trigger M-Pesa payment
                val api = "https://kbenkamotho.alwaysdata.net/api/payment"
                val params = RequestParams()
                params.put("phone", phone)
                params.put("amount", productCost)
                
                val helper = ApiHelper(this)
                helper.post(api, params)
                
                Toast.makeText(this, "Requesting M-Pesa push for KSh $productCost...", Toast.LENGTH_LONG).show()
            }
        }
    }
}