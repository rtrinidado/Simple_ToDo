package com.raultorinz.simpletodo.data.source

import androidx.lifecycle.LiveData
import com.raultorinz.simpletodo.data.source.room.Task

interface TasksRepository {
    val completedTasks: LiveData<List<Task>>?
    val uncompletedTasks: LiveData<List<Task>>?

    fun updateTaskStatus(idTask: Long, completed: Boolean)

    fun deleteTask(id: Long)

    fun insert(task: Task)

    fun update(task: Task)

    suspend fun showTask(idTask: Long): Task?
}