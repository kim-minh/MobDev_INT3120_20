package com.example.Week9

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextName: EditText
    private lateinit var buttonSave: Button
    private lateinit var textViewSavedName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)
        sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        editTextName = findViewById(R.id.editTextName)
        buttonSave = findViewById(R.id.buttonSave)
        textViewSavedName = findViewById(R.id.textViewSavedName)

        loadSavedName()

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            saveName(name)
            textViewSavedName.text = "Saved Name: $name"
        }
    }

    private fun saveName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_name", name)
        editor.apply()
    }

    private fun loadSavedName() {
        val savedName = sharedPreferences.getString("user_name", "")
        textViewSavedName.text = "Saved Name: $savedName"
    }
}