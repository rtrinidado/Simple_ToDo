package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import android.graphics.Paint
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import com.raultorinz.simpletodo.room.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AddTaskRepository(application)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var result: Task? = null

    fun insert(task: Task) {
        if (result == null)
            repository.insert(task)
        else {
            result!!.name = task.name
            result!!.description = task.description
            result!!.completed = task.completed
            repository.update(result!!)
        }
    }

    fun showTask(completeCheck: CheckBox, nameTask: EditText, description: EditText, idTask: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            result = repository.showTask(idTask).await()
            if (result != null) {
                completeCheck.isChecked = result!!.completed
                nameTask.text.clear()
                nameTask.text.append(result!!.name)
                nameTask.paintFlags = if (result!!.completed) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
                description.text.clear()
                description.text.append(result!!.description)
            }
        }
    }

}