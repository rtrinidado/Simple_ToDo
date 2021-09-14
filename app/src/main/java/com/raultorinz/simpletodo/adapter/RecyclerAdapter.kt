package com.raultorinz.simpletodo.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.ui.main.MainFragmentDirections
import com.raultorinz.simpletodo.ui.main.MainViewModel
import kotlinx.android.synthetic.main.todo_element_layout.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RecyclerAdapter(private val viewModel: MainViewModel, private val context: Context) :
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
        taskList = orderList(tasks)
        notifyDataSetChanged()
    }

    private fun orderList(tasks: List<Task>): List<Task> {
        return tasks.sortedBy { getDate(it.dateTask ?: "") }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.todo_element_layout, parent, false)
        return ViewHolder(v, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskList.let {
            holder.check.isChecked = it!![position].completed
            holder.name.text = it[position].name
            holder.name.paintFlags = if (it[position].completed) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
            holder.date.text = it[position].dateTask
            if (it[position].dateTask.isNullOrEmpty()) {
                holder.date.visibility = View.GONE
            } else {
                holder.date.setTextColor(checkDate(it[position].dateTask!!, it[position].completed))
                holder.date.visibility = View.VISIBLE
            }
            holder.id = it[position].id
        }
    }

    override fun getItemCount(): Int {
        return if (taskList == null) 0 else taskList!!.size
    }

    private fun getDate(dateS: String): LocalDate {

        val format = context.getString(R.string.date_format)
        val local = Locale("es", "MX")
        val formatter = DateTimeFormatter.ofPattern(format, local)
        return if (dateS.isNotEmpty())
            LocalDate.parse(dateS, formatter)
        else
            LocalDate.MIN
    }

    private fun checkDate(dateS: String, checked: Boolean): Int {
        val today = LocalDate.now()
        val date = getDate(dateS)
        val comparison = today.compareTo(date)
        return when {
            checked -> context.getColor(R.color.blue_grotto)
            comparison > 0 -> context.getColor(R.color.rose_red)
            else -> Color.GRAY
        }
    }
}