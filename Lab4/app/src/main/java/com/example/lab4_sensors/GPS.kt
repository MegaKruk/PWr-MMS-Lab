package com.example.lab4_sensors

import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import com.google.android.gms.location.*
import java.util.*


class GPS : AppCompatActivity()
{
    private var homeLatitude = 0.0
    private var homeLongitude = 0.0
    private var currentLocation: TextView? = null
    private var homeLocation: TextView? = null
    private var creepyPastaMammaMia: TextView? = null

    private var locationRequestCheck: LocationRequest? = null
    private var locationCallbackCheck: LocationCallback? = null
    private var fusedLocation: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gps)

        homeLocation = findViewById(R.id.textView_home)
        currentLocation = findViewById(R.id.textView_current)
        creepyPastaMammaMia = findViewById(R.id.textView_creepy)
        fusedLocation = this.let { LocationServices.getFusedLocationProviderClient(it) }

        handleGPS(this)

        reqUpdate()
        val backEvent = findViewById<Button>(R.id.button_back2)
        backEvent.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume()
    {
        super.onResume()
        if (fusedLocation != null)
        {
            reqUpdate()
        }
    }

    override fun onPause()
    {
        super.onPause()
        fusedLocation?.removeLocationUpdates(locationCallbackCheck)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1000 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            locationCallbackCheck = object : LocationCallback()
            {
                override fun onLocationResult(locResults: LocationResult)
                {
                    for (loc in locResults.locations)
                    {
                        val setLat = loc.latitude
                        val setLong = loc.longitude
                        currentLocation?.text = (String.format(Locale.US, "%s, %s", setLat, setLong))
                        if (homeLocation?.text?.isEmpty()!!)
                        {
                            homeLocation?.text = (String.format(Locale.US, "%s, %s", setLat, setLong))
                            homeLocation?.visibility = View.VISIBLE
                            homeLongitude = setLong
                            homeLatitude = setLat
                        }
                        if(kotlin.math.abs(setLat - homeLatitude) > 1 || kotlin.math.abs(setLong - homeLongitude) > 1)
                        {
                            creepyPastaMammaMia?.visibility = View.VISIBLE
                        }
                        if(creepyPastaMammaMia?.visibility == View.VISIBLE && kotlin.math.abs(setLat - homeLatitude) < 1 || kotlin.math.abs(setLong - homeLongitude) < 1)
                        {
                            creepyPastaMammaMia?.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        } else
        {
            Toast.makeText(this, "ERROR: permissions", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reqUpdate()
    {
        locationRequestCheck = LocationRequest()
        locationRequestCheck?.interval = 2000
        locationRequestCheck?.fastestInterval = 2000
        locationRequestCheck?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if(this.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocation?.requestLocationUpdates(locationRequestCheck, locationCallbackCheck, Looper.myLooper())
        }
    }

    private fun handleGPS(activity: Activity)
    {
        return if (this.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED && this.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),1000)
        }
        else if(this.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED && this.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED)
        {
            locationCallbackCheck = object : LocationCallback()
            {
                override fun onLocationResult(locationResult: LocationResult)
                {
                    for (location in locationResult.locations)
                    {
                        val setLat = location.latitude
                        val setLong = location.longitude
                        currentLocation?.text = (String.format(Locale.US, "%s, %s", setLat, setLong))
                        if (homeLocation?.text?.isEmpty()!!)
                        {
                            homeLocation?.text = (String.format(Locale.US, "%s, %s", setLat, setLong))
                            homeLocation?.visibility = View.VISIBLE
                            homeLongitude = setLong
                            homeLatitude = setLat
                        }
                        if(kotlin.math.abs(setLat - homeLatitude) > 1 || kotlin.math.abs(setLong - homeLongitude) > 1)
                        {
                            creepyPastaMammaMia?.visibility = View.VISIBLE
                        }
                        if(creepyPastaMammaMia?.visibility == View.VISIBLE && kotlin.math.abs(setLat - homeLatitude) < 1 || kotlin.math.abs(setLong - homeLongitude) < 1)
                        {
                            creepyPastaMammaMia?.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
        else
        {
            // print sth
        }
    }
}
