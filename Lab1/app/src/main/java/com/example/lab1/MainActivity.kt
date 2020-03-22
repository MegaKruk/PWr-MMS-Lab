package com.example.lab1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputHeight = findViewById<EditText>(R.id.inputHeightCM)
        val inputMass = findViewById<EditText>(R.id.inputMassKG)
        val resultView = findViewById<TextView>(R.id.outputBMI)

        val aboutEvent = findViewById<Button>(R.id.buttonAbout)
        aboutEvent.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val imperialEvent = findViewById<Button>(R.id.buttonImperial)
        imperialEvent.setOnClickListener{
            val intent = Intent(this, ImperialActivity::class.java)
            startActivity(intent)
        }

        val buttonEvent = findViewById<Button>(R.id.buttonCount)
        buttonEvent.setOnClickListener{
            val kg = inputMass.text.toString().toDouble()
            val cm = inputHeight.text.toString().toDouble()
            if(kg < 30 || cm < 100 || kg > 300 || cm > 250)
            {
                outputInfo.text = "Wrong input!"
                outputInfo.setTextColor(Color.BLACK)
            }
            else
            {
                var bmi: Double = kg / Math.pow(cm / 100.0, 2.0)
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

