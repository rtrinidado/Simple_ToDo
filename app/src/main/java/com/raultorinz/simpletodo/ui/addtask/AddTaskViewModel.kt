package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
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

    fun insert(task: Task) {
        repository.insert(task)
    }

    fun showTask(completeCheck: CheckBox, nameTask: EditText, description: EditText, name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            var result: Task = repository.showTask(name).await()
            completeCheck.isChecked = result.completed
            nameTask.text.append(result.name)
            description.text.append(result.description)
        }
    }

}