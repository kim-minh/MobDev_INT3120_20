package com.example.week2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import com.example.week6a7.R

class Bai1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bai1)
        val picker: NumberPicker = findViewById(R.id.numberPicker)
        picker.minValue = 0
        picker.maxValue = 1000
    }
}