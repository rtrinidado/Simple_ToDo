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

    fun updateTaskStatus(idTask: Long, completed: Boolean) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.updateTaskStatus(idTask, completed)
        }
    }

    fun deleteTask(id: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.deleteTask(id)
        }
    }
}