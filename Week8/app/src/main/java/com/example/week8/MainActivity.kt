package com.example.week8

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener {
            sendMessage()
        }

        val broadcastIntent = Intent("com.example.ACTION_MY_EVENT")
        broadcastIntent.putExtra("message", "Hello, this is a broadcast event!")
        sendBroadcast(broadcastIntent)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)

        val receiver = MyReceiver()
        val intentFilter = IntentFilter("com.example.ACTION_MY_EVENT")
        registerReceiver(receiver, intentFilter)
    }

    private fun sendMessage() {
        val fullName = findViewById<EditText>(R.id.editTextText).text.toString()
        val message = "Hello, Please say hello to me!"

        val intent = Intent(this, GreetingActivity::class.java)
        intent.putExtra("fullName", fullName)
        intent.putExtra("message", message)

        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val textFeedback: TextView = findViewById(R.id.textFeedback)
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val feedback = data?.getStringExtra("feedback")
            textFeedback.text = feedback
        } else {
            textFeedback.text = "!?"
        }
    }
}