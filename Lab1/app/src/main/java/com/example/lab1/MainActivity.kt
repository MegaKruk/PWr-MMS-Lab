package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputHeight = findViewById<EditText>(R.id.inputHeightCM)
        val inputMass = findViewById<EditText>(R.id.inputMassKG)
        val buttonEvent = findViewById<Button>(R.id.buttonCount)
        val resultView = findViewById<TextView>(R.id.outputBMI)

        buttonEvent.setOnClickListener{
            val kg = inputMass.text.toString().toDouble()
            val cm = inputHeight.text.toString().toDouble()

            var bmi : Double = kg / Math.pow(cm / 100.0, 2.0)
            bmi = (bmi * 100.0).roundToInt() / 100.0;
            resultView.text = bmi.toString()

//            when {
//                bmi >= 30 -> helpView.text = "fat"
//                bmi >= 25 -> helpView.text = "ok"
//                bmi >= 13 -> helpView.text = "thin"
//                else -> helpView.text = "wrong input"
//            }

        }
    }
}

