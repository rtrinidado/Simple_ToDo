package com.raultorinz.simpletodo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raultorinz.simpletodo.model.TaskModel
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    var taskList : ArrayList<TaskModel> = ArrayList()
    var count : Long = 0

    fun createElement() {
        var created = Calendar.getInstance()
        var element = TaskModel(++count, false, "Tarea ${count}", "", created.time.toString())
        taskList.add(element)
    }

    fun getList() : List<TaskModel> {
        return taskList
    }
}