package com.example.lab4_sensors

import android.content.Intent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class BallGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val backEvent = findViewById<Button>(R.id.button_back)
        backEvent.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}