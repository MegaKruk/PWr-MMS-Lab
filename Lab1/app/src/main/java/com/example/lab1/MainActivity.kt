package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

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
            val mass = massKG.text.toString()
            val height = heightCM.text.toString()
            val bmiResult = calculateBMI(mass, height)
            outputBMI.text = bmiResult.toString()
        } else
        {

        }
}
}



//    private fun calculateBMI(weight: Double, height: Double) = BigDecimal(weight / (height * height))
//        .setScale(2, RoundingMode.HALF_EVEN).toDouble()
//}

    private fun calculateBMI(height: String, weight: String): Double
    {
        val heightDouble = height.toDouble() / 100
        val weightDouble = weight.toDouble()
        //val bmiResult = String.format("%.2f", bmi)
        return weightDouble / (heightDouble * heightDouble)
        //val intent = Intent(this, ResultActivity::class.java)
        //intent.putExtra("Calculate results here", bmiResult)
        //startActivity(intent)
    }
}


