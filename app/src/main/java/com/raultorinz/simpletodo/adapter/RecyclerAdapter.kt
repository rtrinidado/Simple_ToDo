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
import com.raultorinz.simpletodo.databinding.TodoElementLayoutBinding
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.ui.main.MainFragmentDirections
import com.raultorinz.simpletodo.ui.main.MainViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RecyclerAdapter(private val viewModel: MainViewModel, private val context: Context) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var taskList: List<Task>? = null

    inner class ViewHolder(val binding: TodoElementLayoutBinding, viewModel: MainViewModel) :
        RecyclerView.ViewHolder(binding.root) {
        var name = binding.toDoText

        fun bind(viewModel: MainViewModel, item: Task) {
            binding.viewModel = viewModel
            binding.task = item
            binding.adapter = this@RecyclerAdapter
            binding.executePendingBindings()
        }
    }

    fun seeTask(id: Long, view: View) {
        val action = MainFragmentDirections.mainFragmentToAddTaskFragment()
        action.idTask = id
        Navigation.findNavController(view).navigate(action)
    }

    fun setTaskList(tasks: List<Task>) {
        taskList = orderList(tasks)
        notifyDataSetChanged()
    }

    private fun orderList(tasks: List<Task>): List<Task> {
        return tasks.sortedBy { getDate(it.dateTask ?: "") }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TodoElementLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskList.let {
            holder.bind(viewModel, it?.get(position) ?: Task())
            holder.name.paintFlags =
                if (it?.get(position)?.completed == true) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
        }
    }

    override fun getItemCount(): Int {
        return taskList?.size ?: 0
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

    fun checkDate(dateS: String?, checked: Boolean): Int {
        var comparison = -1
        if (!dateS.isNullOrEmpty()) {
            val today = LocalDate.now()
            val date = getDate(dateS)
            comparison = today.compareTo(date)
        }
        return when {
            checked -> context.getColor(R.color.blue_grotto)
            comparison > 0 -> context.getColor(R.color.rose_red)
            else -> Color.GRAY
        }
    }
}