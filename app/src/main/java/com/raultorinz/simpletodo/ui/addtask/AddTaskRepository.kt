package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.room.TaskDao
import com.raultorinz.simpletodo.room.TaskRoomDatabase
import kotlinx.coroutines.*

class AddTaskRepository(application: Application) {

    private var taskDao: TaskDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        val db = TaskRoomDatabase.getDatabase(application)
        taskDao = db.taskDao()
    }

    fun insert(task: Task) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.insert(task)
        }
    }

    fun update(task: Task) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.updateTask(task)
        }
    }

    fun deleteTask(id: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.deleteTask(id)
        }
    }

    suspend fun showTask(idTask: Long): Task? {
        return taskDao?.getTask(idTask)
    }
}