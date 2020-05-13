package com.example.lab4_sensors


import android.os.Bundle
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = this!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        recyclerView = findViewById(R.id.recycler)!!
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(
            deviceSensors as MutableList<Sensor>
        )
    }
}
