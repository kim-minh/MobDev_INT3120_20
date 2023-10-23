package com.example.week8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GreetingActivity : AppCompatActivity() {
    private var fullName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greeting)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val intent = intent
        fullName = intent.getStringExtra("fullName")
        val message = intent.getStringExtra("message")
        val messageText: TextView = findViewById(R.id.messageText)
        messageText.text = message
    }


    override fun finish() {

        val data = Intent()
        val feedback = "OK, Hello ${this.fullName}. How are you?"
        data.putExtra("feedback", feedback)
        setResult(Activity.RESULT_OK, data)
        super.finish()
    }
}