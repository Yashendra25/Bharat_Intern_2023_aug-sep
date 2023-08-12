package com.yashendra.temperatorconvertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appTitleTextView: TextView = findViewById(R.id.appTitle)
        appTitleTextView.isSelected = true
        appTitleTextView.requestFocus()

        val sourceScaleSpinner: Spinner = findViewById(R.id.sourceScaleSpinner)
        val targetScaleSpinner: Spinner = findViewById(R.id.targetScaleSpinner)
        val temperatureScales = arrayOf("Celsius", "Fahrenheit", "Kelvin")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, temperatureScales)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceScaleSpinner.adapter = adapter
        targetScaleSpinner.adapter = adapter


        // Set default selections for spinners
        val defaultSourceScale = "Celsius"
        val defaultTargetScale = "Fahrenheit"

        val sourceScalePosition = adapter.getPosition(defaultSourceScale)
        val targetScalePosition = adapter.getPosition(defaultTargetScale)

        sourceScaleSpinner.setSelection(sourceScalePosition)
        targetScaleSpinner.setSelection(targetScalePosition)

        clearButton.setOnClickListener { inputTemperature.text.clear() }

        convertButton.setOnClickListener {
            // Get selected items from spinners
            val selectedSourceScale = sourceScaleSpinner.selectedItem.toString()
            val selectedTargetScale = targetScaleSpinner.selectedItem.toString()
            
            // Call a function to perform the conversion and display the result
            performTemperatureConversion(selectedSourceScale, selectedTargetScale)

        }
    }

    private fun performTemperatureConversion(selectedSourceScale: String, selectedTargetScale: String) {
        // Get temperature value from EditText
        val temperatureValue: Double = inputTemperature.text.toString().toDouble()

        // Perform conversion logic based on source and target scales
        val convertedTemperature: Double = when {
            selectedSourceScale == "Celsius" && selectedTargetScale == "Fahrenheit" -> temperatureValue * 9/5 + 32
            selectedSourceScale == "Fahrenheit" && selectedTargetScale == "Celsius" -> (temperatureValue - 32) * 5/9
            selectedSourceScale == "Celsius" && selectedTargetScale == "Kelvin" -> temperatureValue + 273.15
            selectedSourceScale == "Kelvin" && selectedTargetScale == "Celsius" -> temperatureValue - 273.15
            selectedSourceScale == "Fahrenheit" && selectedTargetScale == "Kelvin" -> (temperatureValue + 459.67) * 5/9
            selectedSourceScale == "Kelvin" && selectedTargetScale == "Fahrenheit" -> temperatureValue * 9/5 - 459.67
            else -> temperatureValue // If source and target scales are the same
        }


        // Display the result in the result TextView
        temperatureDescription.text="Temperature in $selectedTargetScale is :$convertedTemperature"
        // Set different images based on temperature and selected temperature unit
        val backgroundImage: ImageView = findViewById(R.id.backgroundImage)
        val imageResource: Int = when {
            selectedTargetScale == "Celsius" && convertedTemperature > 30 -> R.drawable.sunny
            selectedTargetScale == "Celsius" && convertedTemperature < 0 -> R.drawable.snow
            selectedTargetScale == "Fahrenheit" && convertedTemperature > 86 -> R.drawable.sunny
            selectedTargetScale == "Fahrenheit" && convertedTemperature < 32 -> R.drawable.snow
            selectedTargetScale == "Kelvin" && convertedTemperature > 303.15 -> R.drawable.sunny
            selectedTargetScale == "Kelvin" && convertedTemperature < 273.15 -> R.drawable.snow
            else -> R.drawable.sunset
        }
        backgroundImage.setImageResource(imageResource)

    }

}