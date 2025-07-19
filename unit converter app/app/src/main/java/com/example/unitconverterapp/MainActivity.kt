package com.example.unitconverterapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputValue = findViewById<EditText>(R.id.inputValue)
        val fromUnit = findViewById<Spinner>(R.id.fromUnit)
        val toUnit = findViewById<Spinner>(R.id.toUnit)
        val convertButton = findViewById<Button>(R.id.convertButton)
        val resultView = findViewById<TextView>(R.id.resultView)

        val units = arrayOf("Metre", "Millimetre", "Mile", "Foot")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromUnit.adapter = adapter
        toUnit.adapter = adapter

        convertButton.setOnClickListener {
            val inputText = inputValue.text.toString()
            if (inputText.isEmpty()) {
                resultView.text = "Please enter a value."
                return@setOnClickListener
            }
            val value = inputText.toDoubleOrNull()
            if (value == null) {
                resultView.text = "Invalid input."
                return@setOnClickListener
            }
            val from = fromUnit.selectedItem.toString()
            val to = toUnit.selectedItem.toString()
            val result = convertLength(value, from, to)
            resultView.text = "$value $from = $result $to"
        }
    }

    private fun convertLength(value: Double, from: String, to: String): Double {
        // Convert input to metres first
        val valueInMetres = when (from) {
            "Metre" -> value
            "Millimetre" -> value / 1000.0
            "Mile" -> value * 1609.34
            "Foot" -> value * 0.3048
            else -> value
        }
        // Convert from metres to target unit
        return when (to) {
            "Metre" -> valueInMetres
            "Millimetre" -> valueInMetres * 1000.0
            "Mile" -> valueInMetres / 1609.34
            "Foot" -> valueInMetres / 0.3048
            else -> valueInMetres
        }
    }
}
