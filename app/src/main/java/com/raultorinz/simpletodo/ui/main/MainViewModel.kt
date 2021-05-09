package com.raultorinz.simpletodo.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raultorinz.simpletodo.model.TaskModel
import com.raultorinz.simpletodo.room.Task
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application)
    private val allTasks : LiveData<List<Task>>? = repository.allTasks
    var taskList : ArrayList<TaskModel> = ArrayList()
    var count : Long = 0


    fun getAllTasks(): LiveData<List<Task>>? {
        return allTasks
    }
}