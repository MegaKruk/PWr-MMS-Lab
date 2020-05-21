package com.example.lab5_covid_19

import android.app.Activity
import android.app.AlertDialog


class Loading(private var activity: Activity)
{
    private lateinit var alert: AlertDialog
    private val builder = AlertDialog.Builder(activity)

    fun startLoading()
    {
        val layoutInflater = activity.layoutInflater
        builder.setCancelable(false)
        builder.setView(layoutInflater.inflate(R.layout.loading, null))
        alert = builder.show()
    }
}