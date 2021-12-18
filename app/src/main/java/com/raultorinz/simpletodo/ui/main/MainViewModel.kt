package com.raultorinz.simpletodo.ui.main

import androidx.lifecycle.*
import com.raultorinz.simpletodo.data.source.TasksRepository
import com.raultorinz.simpletodo.data.source.room.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(private val repository: TasksRepository) : ViewModel() {
    var completedTasks: LiveData<List<Task>>? = repository.completedTasks
    var uncompletedTasks: LiveData<List<Task>>? = repository.uncompletedTasks
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

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MainViewModel(tasksRepository) as T)
}