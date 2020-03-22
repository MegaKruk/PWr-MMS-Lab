package com.example.lab1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow
import kotlin.math.roundToInt

class ImperialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imperial)

        val inputHeightFeet = findViewById<EditText>(R.id.inputHeightFeet)
        val inputHeightInches = findViewById<EditText>(R.id.inputHeighInches)
        val inputMass = findViewById<EditText>(R.id.inputMassPounds)
        val resultView = findViewById<TextView>(R.id.outputBMI)

        val aboutEvent = findViewById<Button>(R.id.buttonAbout)
        aboutEvent.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val metricEvent = findViewById<Button>(R.id.buttonMetric)
        metricEvent.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonEventImperial = findViewById<Button>(R.id.buttonCountImperial)
        buttonEventImperial.setOnClickListener{
            val pounds = inputMass.text.toString().toDouble()
            val feet = inputHeightFeet.text.toString().toDouble()
            val inches = inputHeightInches.text.toString().toDouble()
            val height = (feet*12) + inches
            if(pounds < 60 || feet < 2 || pounds > 600 || feet > 10 || inches > 11)
            {
                outputInfo.text = "Wrong input!"
                outputInfo.setTextColor(Color.BLACK)
            }
            else
            {
                var bmi: Double = 703 * pounds / (height).pow(2.0)
                bmi = (bmi * 100.0).roundToInt() / 100.0;
                resultView.text = bmi.toString()

                //            Underweight = 13-18.5
                //            Normal weight = 18.5–24.9
                //            Overweight = 25–29.9
                //            Obesity = BMI of 30 or greater
                when
                {
                    bmi >= 30 -> {
                        outputInfo.text = "You are obese. You should seek help of dietician."
                        outputInfo.setTextColor(Color.RED)
                        resultView.setTextColor(Color.RED)
                    }
                    bmi >= 25 -> {
                        outputInfo.text = "You are overweight. You should eat less and exercise more."
                        outputInfo.setTextColor(Color.YELLOW)
                        resultView.setTextColor(Color.YELLOW)
                    }
                    bmi >= 18.5 -> {
                        outputInfo.text = "You have normal weight. Keep doing what you're doing. Unless it's illegal. Then you should stop."
                        outputInfo.setTextColor(Color.GREEN)
                        resultView.setTextColor(Color.GREEN)
                    }
                    bmi >= 13 -> {
                        outputInfo.text = "You are underweight. Use bigger plates and don't drink water before eating. Have some pizza."
                        outputInfo.setTextColor(Color.BLUE)
                        resultView.setTextColor(Color.BLUE)
                    }
                    else -> {
                        outputInfo.text = "wrong input (or dead)"
                        outputInfo.setTextColor(Color.BLACK)
                        resultView.setTextColor(Color.BLACK)
                    }
                }
            }
        }

    }
}
