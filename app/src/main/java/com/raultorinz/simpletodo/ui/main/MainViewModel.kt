package com.raultorinz.simpletodo.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raultorinz.simpletodo.room.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application)
    val completedTasks: LiveData<List<Task>>? = repository.completedTasks
    val incompletedTasks: LiveData<List<Task>>? = repository.incompletedTasks
    var date: MutableLiveData<String> = MutableLiveData()

    fun showDate() {
        val local = Locale("es", "MX")
        val time = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", local)
        date.value = time.format(formatter)
    }

    fun updateTaskStatus(idTask: Long, completed: Boolean) {
        repository.updateTaskStatus(idTask, completed)
    }

    fun deleteTask(id: Long) {
        repository.deleteTask(id)
    }
}