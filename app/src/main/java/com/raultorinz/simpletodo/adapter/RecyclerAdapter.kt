package com.raultorinz.simpletodo.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.ui.main.MainFragmentDirections
import com.raultorinz.simpletodo.ui.main.MainViewModel
import kotlinx.android.synthetic.main.todo_element_layout.view.*

class RecyclerAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var taskList: List<Task>? = null

    inner class ViewHolder(itemView: View, viewModel: MainViewModel) :
        RecyclerView.ViewHolder(itemView) {
        var check = itemView.radioButton
        var name = itemView.toDoText
        var date = itemView.toDoDate
        var delete = itemView.deleteButton
        var id: Long = 0

        init {
            itemView.setOnClickListener {
                var action = MainFragmentDirections.mainFragmentToAddTaskFragment()
                action.idTask = id
                Navigation.findNavController(it).navigate(action)
            }

            check.setOnClickListener {
                viewModel.updateTaskStatus(id, check.isChecked)
            }
            delete.setOnClickListener {
                viewModel.deleteTask(id)
            }
        }
    }

    fun setTaskList(tasks: List<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_element_layout, parent, false)
        return ViewHolder(v, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskList.let {
            holder.check.isChecked = it!![position].completed
            holder.name.text = it[position].name
            holder.name.paintFlags = if (it[position].completed) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
            holder.date.text = it[position].dateTask
            holder.date.visibility = if (it[position].dateTask.isNullOrEmpty()) View.GONE else View.VISIBLE
            holder.id = it[position].id
        }
    }

    override fun getItemCount(): Int {
        return if (taskList == null) 0 else taskList!!.size
    }
}