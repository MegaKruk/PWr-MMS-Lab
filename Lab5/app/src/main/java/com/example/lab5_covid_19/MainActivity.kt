package com.example.lab5_covid_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countriesList = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countriesList)
        autoCompleteTextView.setAdapter(arrayAdapter)

        val searchEvent = findViewById<Button>(R.id.button_search)
        searchEvent.setOnClickListener(View.OnClickListener {
            if (validateInput() && isNetworkAvailable())
            {
                val myCountry = autoCompleteTextView.text.toString().trim().capitalize(Locale.ROOT)

                val requestAPIData = RequestData(this)
                requestAPIData.requestData(myCountry)

                val intent = Intent(this, ResultsActivity::class.java)
                startActivity(intent)
            }
            else if (!isNetworkAvailable())
            {
                snackBarMessage("ERROR: No connection!")
            }
            else
            {
                snackBarMessage("ERROR: Wrong country name!")
            }
        })
    }

    private fun validateInput(): Boolean
    {
        val countriesList = resources.getStringArray(R.array.countries).asList()
        val myCountry = autoCompleteTextView.text.toString()
        val validCountry = countriesList.find { it == myCountry.trim().capitalize(Locale.ROOT) }
        return validCountry != null
    }

    private fun isNetworkAvailable(): Boolean
    {
        return try
        {
            val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo: NetworkInfo? = null
            networkInfo = manager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
        catch (e: NullPointerException)
        {
            false
        }
    }

    private fun snackBarMessage(message: String)
    {
        val snackbar = Snackbar.make(button_search, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(resources.getColor(R.color.design_default_color_background))
        snackbar.setAction("Try again", View.OnClickListener { })

        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(resources.getColor(R.color.design_default_color_error))
        textView.textSize = 14f
        snackbar.show()
    }
}
