package com.raultorinz.simpletodo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.room.TaskDao
import com.raultorinz.simpletodo.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepository(application: Application) {
    val allTasks : LiveData<List<Task>>?
    private var taskDao: TaskDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        val db = TaskRoomDatabase.getDatabase(application)
        taskDao = db.taskDao()
        allTasks = taskDao?.getAllTasks()
    }

    fun updateTaskStatus(name: String, completed: Boolean) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.updateTaskStatus(name, completed)
        }
    }

    fun deleteTask(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.deleteTask(name)
        }
    }
}