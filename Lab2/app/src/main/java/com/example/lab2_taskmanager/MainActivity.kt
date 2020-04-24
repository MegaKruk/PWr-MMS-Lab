package com.example.lab2_taskmanager

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity()
{
    private var tasksList = mutableListOf<Task>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(
            tasksList,
            resources,
            recyclerView,
            this,
            recyclerView.context,
            this.window.decorView.findViewById(android.R.id.content),
            layoutInflater
        )

        val addTaskEvent = findViewById<Button>(R.id.buttonAddTask)
        addTaskEvent.setOnClickListener{
            displayPopup()
        }
    }

    private fun displayPopup()
    {
        val popupView = layoutInflater.inflate(R.layout.add_task, this.window.decorView.findViewById(android.R.id.content), false)
        val dm = recyclerView.context?.resources?.displayMetrics
        val popupWindow = PopupWindow(popupView,
            dm?.widthPixels?.times(0.8)?.toInt()!!,
            dm?.heightPixels?.times(0.8)?.toInt()!!, true)
        val radioGroup: RadioGroup = popupView.findViewById(R.id.taskType)
        val radioTODO: RadioButton = popupView.findViewById(R.id.typeTODO)
        val radioEmail: RadioButton = popupView.findViewById(R.id.typeEmail)
        val radioMeeting: RadioButton = popupView.findViewById(R.id.typeMeeting)
        val radioPhone: RadioButton = popupView.findViewById(R.id.typePhone)

        radioEmail.setOnClickListener {
        }
        radioPhone.setOnClickListener {
        }
        radioMeeting.setOnClickListener {
        }
        radioTODO.setOnClickListener {
        }
        popupWindow.showAtLocation(this.window.decorView.findViewById(android.R.id.content), Gravity.CENTER, 0, 0)

        val confirmTaskEvent: Button = popupView.findViewById(R.id.buttonConfirm)
        confirmTaskEvent.setOnClickListener {
            printToast("Got here!")
            if(checkAddTask(popupView, radioGroup))
            {
                printToast("Task added successfully")
                recyclerView.adapter?.notifyDataSetChanged()
                popupWindow.dismiss()
            }
            else
            {
                printToast("Failed to add task")
                popupWindow.dismiss()
            }
        }
        val cancelTaskEvent: Button = popupView.findViewById(R.id.buttonCancel)
        cancelTaskEvent.setOnClickListener {
            printToast("Task creation cancelled")
            popupWindow.dismiss()
        }
        popupView.isFocusable = true;
    }

    private fun printToast(text: String)
    {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun checkAddTask(popupView: View, radioGroup: RadioGroup): Boolean
    {
        var title = ""
        val dueDate: Calendar
        val type: Icon
        var description = ""
        val popupTitle: TextView = popupView.findViewById(R.id.taskTitle)

        if(popupTitle.text !== null && popupTitle.text.isNotEmpty())
        {
            title = popupTitle.text.toString()
        }
        else
        {
            printToast("Title field is empty")
            return false
        }

        val popupDescription: TextView = popupView.findViewById(R.id.taskDescription)
        popupDescription.text.toString()
        if(popupDescription.text !== null && popupDescription.text.isNotEmpty())
        {
            description = popupDescription.text.toString()
        }
        else
        {
            printToast("Description field is empty")
            return false
        }
        type = Icon.valueOf(popupView.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString().toUpperCase())
        if(type == null)
        {
            printToast("No type was chosen!")
            return false
        }
        val popupDate: TextView = popupView.findViewById(R.id.taskDate)
        if(popupDate.text !== null && popupDate.text.isNotEmpty())
        {
            val SDF = SimpleDateFormat("dd-MM-yyyy")
            var date: Date
            try
            {
                date = SDF.parse(popupDate.text.toString())
            }
            catch (e: Exception)
            {
                printToast("Wrong date format")
                return false
            }
            dueDate = Calendar.getInstance()
            dueDate.time = date
        }
        else
        {
            printToast("Due date field is empty")
            return false
        }
        tasksList.add(Task(tasksList.size, title , description, dueDate, "Pending", type))
        return true
    }
}
