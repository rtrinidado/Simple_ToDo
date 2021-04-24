package com.raultorinz.simpletodo.ui.addtask

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raultorinz.simpletodo.model.TaskModel
import com.raultorinz.simpletodo.room.Task
import com.raultorinz.simpletodo.room.TaskRoomDatabase
import kotlinx.coroutines.launch

class AddTaskViewModel : ViewModel() {
    fun insert(task: Task) {
    }
}