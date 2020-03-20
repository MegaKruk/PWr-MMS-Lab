package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        outputBMI.text = inputMassKG.text.div((inputHeightCM.text.div(100.0)))
//    }
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    buttonCount.setOnClickListener{
        if (heightCM.text.isNotEmpty() && massKG.text.isNotEmpty())
        {
            val mass = massKG.text.toString().toDouble()
            val height = heightCM.text.toString().toDouble()/100
            val bmi = calculateBMI(mass, height)
            outputBMI.text = bmi.toString()
        }
    }
}

    private fun calculateBMI(weight: Double, height: Double) = BigDecimal(weight / (height * height))
        .setScale(2, RoundingMode.HALF_EVEN).toDouble()
}

