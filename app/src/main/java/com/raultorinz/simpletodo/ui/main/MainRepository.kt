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
    val completedTasks: LiveData<List<Task>>?
    val incompletedTasks: LiveData<List<Task>>?
    private var taskDao: TaskDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        val db = TaskRoomDatabase.getDatabase(application)
        taskDao = db.taskDao()
        completedTasks = taskDao?.getAllCompletedTasks()
        incompletedTasks = taskDao?.getAllIncompletedTasks()
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