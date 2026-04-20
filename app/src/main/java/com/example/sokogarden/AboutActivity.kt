package com.example.sokogarden

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class AboutActivity : AppCompatActivity() {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btnBack = findViewById<Button>(R.id.btnBack)
        val aboutTxt = findViewById<TextView>(R.id.aboutTxt)
        val speakBtn = findViewById<Button>(R.id.speakListen)

        // Initialize TextToSpeech
        tts = TextToSpeech(applicationContext) { status ->
            if (status != TextToSpeech.ERROR) {
                tts.language = Locale.US
            }
        }

        speakBtn.setOnClickListener {
            val text = aboutTxt.text.toString()
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}