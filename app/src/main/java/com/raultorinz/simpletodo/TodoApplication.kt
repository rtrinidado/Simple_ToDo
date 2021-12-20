package com.raultorinz.simpletodo

import android.app.Application
import com.raultorinz.simpletodo.data.source.TasksRepository

class TodoApplication : Application() {
    val repository: TasksRepository
        get() = ServiceLocator.provideTasksRepository(this)
}