package com.example.lab4_sensors

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(var values: MutableList<Sensor>): RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false)
        val adapter = this
        fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int) -> Unit): T {
            return this
        }
        return ViewHolder(itemView).listen {
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView?.text = values[position].name + " " + values[position].stringType
    }

    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var titleView: TextView? = null
        init{
            titleView = itemView?.findViewById(R.id.textView_item)
        }
    }

    override fun getItemCount(): Int {
        return this.values.size
    }
}