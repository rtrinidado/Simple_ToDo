package com.raultorinz.simpletodo.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.model.TaskModel
import kotlinx.android.synthetic.main.todo_element_layout.view.*

class RecyclerAdapter(private val dataSet : List<TaskModel>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_element_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.check.isChecked = dataSet[position].completed
        holder.name.text = dataSet[position].name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}