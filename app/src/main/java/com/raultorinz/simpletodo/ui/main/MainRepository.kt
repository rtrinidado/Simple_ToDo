package com.raultorinz.simpletodo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.room.TaskDao
import com.raultorinz.simpletodo.room.TaskRoomDatabase

class MainRepository(application: Application) {
    val allTasks : LiveData<List<Task>>?
    private var taskDao: TaskDao?

    init {
        val db = TaskRoomDatabase.getDatabase(application)
        taskDao = db.taskDao()
        allTasks = taskDao?.getAllTasks()
    }
}