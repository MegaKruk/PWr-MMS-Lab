package com.example.lab5_covid_19

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.results.*

class ResultsActivity : AppCompatActivity()
{
    private lateinit var totalString: String
    private lateinit var recoveredString: String
    private lateinit var deadString: String
    private lateinit var countryString: String
    private lateinit var updateString: String

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)
        setVals()
        supportActionBar?.title = countryString

        val backEvent = findViewById<Button>(R.id.button_back)
        backEvent.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setVals()
    {
        val updateText = "Last updated on: "
        updateString = updateText + intent.getStringExtra("lastUpdate").take(10)
        countryString = intent.getStringExtra("country")
        totalString = intent.getStringExtra("active")
        recoveredString = intent.getStringExtra("recovered")
        deadString = intent.getStringExtra("fatal")

        textView_country.text = countryString
        textView_update.text = updateString
        textView_confirmed.text = totalString
        textView_recovered.text = recoveredString
        textView_deaths.text = deadString
    }
}
