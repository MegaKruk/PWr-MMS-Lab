package com.example.lab2_taskmanager

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class Adapter(
    var vals: MutableList<Task>,
    val res: Resources,
    private val recyclerView: RecyclerView,
    private val mainActivity: MainActivity,
    private val context: Context,
    private val view: View,
    private val inflater: LayoutInflater
): RecyclerView.Adapter<Adapter.ViewHolder>()
{

    override fun getItemCount() = vals.size;
    val formSDF: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder
    {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.task, parent, false)
        val adapter = this
        fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int) -> Unit): T
        {
            return this
        }
        return ViewHolder(itemView).listen { pos ->

        }
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, pos: Int)
    {
        holder?.titleView?.text = vals[pos].title
        holder?.dueView?.text = formSDF.format(vals[pos].date.time)
        holder?.typeView?.setImageDrawable(res.getDrawable(vals[pos].type.id))
        holder?.id = vals[pos].id
        holder?.statusView?.text = vals[pos].status
    }
    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!)
    {
        var id: Int? = null
        var titleView: TextView? = null
        var dueView: TextView? = null
        var typeView: ImageView? = null
        var statusView: TextView? = null
        init
        {
            titleView = itemView?.findViewById(R.id.itemTitle)
            dueView = itemView?.findViewById(R.id.itemDate)
            typeView = itemView?.findViewById(R.id.itemTypePiv)
            statusView = itemView?.findViewById(R.id.itemStatus)
        }
    }
}