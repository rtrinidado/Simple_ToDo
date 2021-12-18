package com.raultorinz.simpletodo.data.source

import androidx.lifecycle.LiveData
import com.raultorinz.simpletodo.data.source.room.Task
import com.raultorinz.simpletodo.data.source.room.TaskDao
import com.raultorinz.simpletodo.data.source.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultTaskRepository(db: TaskRoomDatabase) : TasksRepository {
    private var taskDao: TaskDao? = db.taskDao()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override val completedTasks: LiveData<List<Task>>?
        get() = taskDao?.getAllCompletedTasks()
    override val uncompletedTasks: LiveData<List<Task>>?
        get() = taskDao?.getAllIncompletedTasks()

    override fun updateTaskStatus(idTask: Long, completed: Boolean) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.updateTaskStatus(idTask, completed)
        }
    }

    override fun deleteTask(id: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.deleteTask(id)
        }
    }

    override fun insert(task: Task) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.insert(task)
        }
    }

    override fun update(task: Task) {
        coroutineScope.launch(Dispatchers.Main) {
            taskDao?.updateTask(task)
        }
    }

    override suspend fun showTask(idTask: Long): Task? {
        return taskDao?.getTask(idTask)
    }
}