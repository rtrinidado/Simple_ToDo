package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raultorinz.simpletodo.room.Task
import kotlinx.coroutines.*
import java.lang.Exception

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AddTaskRepository(application)
    val task = MutableLiveData<Task>()

    init {
        task.value = Task()
    }

    fun getPreviousTask(idTask: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                task.postValue(repository.showTask(idTask))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTask() {
        repository.deleteTask(task.value!!.id)
    }

    fun insertUpdateTask() {
        if (task.value?.id == 0L)
            repository.insert(task.value!!)
        else
            repository.update(task.value!!)
    }
}