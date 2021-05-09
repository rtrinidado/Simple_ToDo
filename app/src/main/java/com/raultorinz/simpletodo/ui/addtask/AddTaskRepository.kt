package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.room.TaskDao
import com.raultorinz.simpletodo.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskRepository(application: Application) {

    private var taskDao: TaskDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        val db = TaskRoomDatabase.getDatabase(application)
        taskDao = db.taskDao()
    }

    fun insert(task : Task) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.insert(task)
        }
    }
}