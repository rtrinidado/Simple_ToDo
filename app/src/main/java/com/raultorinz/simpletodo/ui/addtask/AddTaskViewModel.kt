package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raultorinz.simpletodo.room.Task
import kotlinx.coroutines.*

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AddTaskRepository(application)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var result: Task? = null

    fun insert(task: Task) {
        if (result == null)
            repository.insert(task)
        else {
            task.id = result!!.id
            repository.update(task)
        }
    }

    fun showTask(idTask: Long): Deferred<Task?> =
            coroutineScope.async(Dispatchers.Default) {
                return@async repository.showTask(idTask)
            }

    fun deleteTask() {
        repository.deleteTask(result!!.id)
    }

}