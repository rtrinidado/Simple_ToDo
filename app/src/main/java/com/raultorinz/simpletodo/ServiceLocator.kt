package com.raultorinz.simpletodo

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.raultorinz.simpletodo.data.source.DefaultTaskRepository
import com.raultorinz.simpletodo.data.source.TasksRepository
import com.raultorinz.simpletodo.data.source.room.TaskRoomDatabase

object ServiceLocator {
    @Volatile
    var tasksRepository: TasksRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }


    private fun createTasksRepository(context: Context): TasksRepository {
        val newRepo = DefaultTaskRepository(TaskRoomDatabase.getDatabase(context))
        tasksRepository = newRepo
        return newRepo
    }
}