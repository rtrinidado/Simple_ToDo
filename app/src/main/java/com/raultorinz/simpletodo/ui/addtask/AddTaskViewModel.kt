package com.raultorinz.simpletodo.ui.addtask

import android.app.Application
import androidx.lifecycle.*
import com.raultorinz.simpletodo.data.source.TasksRepository
import com.raultorinz.simpletodo.data.source.room.Task
import com.raultorinz.simpletodo.ui.main.MainViewModel
import kotlinx.coroutines.*
import java.lang.Exception

class AddTaskViewModel(private val repository: TasksRepository) : ViewModel() {
    val task = MutableLiveData<Task>()

    init {
        task.value = Task()
    }

    fun getPreviousTask(idTask: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                task.postValue(repository.showTask(idTask))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTask() {
        repository.deleteTask(task.value!!.id)
    }

    fun insertUpdateTask() {
        if (task.value?.id == 0L)
            repository.insert(task.value!!)
        else
            repository.update(task.value!!)
    }
}

@Suppress("UNCHECKED_CAST")
class AddTaskViewModelFactory(
    private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddTaskViewModel(tasksRepository) as T)
}