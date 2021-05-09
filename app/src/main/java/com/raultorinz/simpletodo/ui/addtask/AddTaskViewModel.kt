package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raultorinz.simpletodo.room.Task

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AddTaskRepository(application)

    fun insert(task: Task) {
        repository.insert(task)
    }
}