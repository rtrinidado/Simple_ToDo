package com.raultorinz.simpletodo.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.room.Task
import kotlinx.android.synthetic.main.todo_element_layout.view.*

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var taskList : List<Task>? = null

    private val italic : Typeface = Typeface.create("Sans", Typeface.ITALIC)
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var check = itemView.radioButton
        var name = itemView.toDoText

        init {
            check.setOnClickListener {
                if (check.isChecked) {
                    name.typeface = italic
                }
            }
        }
    }

    fun setTaskList(tasks: List<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_element_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskList.let {
            holder.check.isChecked = it!![position].completed
            holder.name.text = it!![position].name
        }
    }

    override fun getItemCount(): Int {
        return if (taskList == null) 0 else taskList!!.size
    }
}