package com.example.lab4_sensors

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class BallGame : AppCompatActivity(), SensorEventListener {
    private var xPos = 0.0
    private var yPos = 0.0
    private var xVel = 0.0
    private var yVel = 0.0
    private var xAcc = 0.0
    private var yAcc = 0.0
    private var screenWidth = 0.0
    private var screenHeight = 0.0
    private val frameDur = 5.0
    private var startTime = 0.0
    private var gameOngoing = true
    private var gameFresh = true
    private var gameRetry: Button? = null
    private var sensorGRV: Sensor? = null
    private var ball: ImageView? = null
    private var score: TextView? = null
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        sensorManager = this!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorGRV = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
        this?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        ball = findViewById(R.id.imageView_ball)
        score = findViewById(R.id.textView_time)
        gameRetry = findViewById(R.id.button_reset)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels?.toDouble()
        screenHeight = displayMetrics.heightPixels?.toDouble()
        xPos = screenWidth/2f
        yPos = screenHeight/2f

        gameRetry?.setOnClickListener {
            setUpGame()
        }
        score?.text = "Waiting for sensor to set up"

        val backEvent = findViewById<Button>(R.id.button_back)
        backEvent.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume()
    {
        super.onResume()
        sensorGRV?.also { vector ->
            sensorManager.registerListener(this, vector,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause()
    {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    {
        // dunno what to put here
    }

    override fun onSensorChanged(event: SensorEvent?)
    {
        if (gameFresh)
        {
            startTime = System.currentTimeMillis().toDouble()
            gameFresh = false
        }

        if (event != null && gameOngoing)
        {
            yAcc = event.values[0].toDouble()
            xAcc = event.values[1].toDouble()
            updateBall();
        }
    }

    private fun updateBall()
    {
        xVel += xAcc * frameDur
        yVel += yAcc * frameDur
        xPos += xVel * frameDur
        yPos += yVel * frameDur

        if (xPos > screenWidth)
        {
            xPos = screenWidth
            xVel = 0.0
            gameOverMan()

        }
        else if ( xPos < 0f)
        {
            xPos = 0.0
            xVel = 0.0
            gameOverMan()
        }

        if (yPos > screenHeight)
        {
            yPos = screenHeight
            yVel = 0.0
            gameOverMan()
        }
        else if ( yPos < 0f)
        {
            yPos = 0.0
            yVel = 0.0
            gameOverMan()
        }
        ball?.x = xPos.toFloat()
        ball?.y = yPos.toFloat()
    }

    private fun setUpGame()
    {
        gameOngoing = true
        gameFresh = true
        xPos = screenWidth/2
        yPos = screenHeight/2
    }

    private fun gameOverMan()
    {
        gameOngoing = false
        val totalElapsed = System.currentTimeMillis()
        score?.text = "Last score:  ${((totalElapsed - startTime) / 1000)} sec"
    }
}