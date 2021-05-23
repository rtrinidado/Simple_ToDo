package com.raultorinz.simpletodo.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raultorinz.simpletodo.room.Task

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application)
    private val allTasks: LiveData<List<Task>>? = repository.allTasks

    fun getAllTasks(): LiveData<List<Task>>? {
        return allTasks
    }

    fun updateTaskStatus(idTask: Long, completed: Boolean) {
        repository.updateTaskStatus(idTask, completed)
    }

    fun deleteTask(id: Long) {
        repository.deleteTask(id)
    }
}