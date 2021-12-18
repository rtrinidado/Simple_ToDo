package com.raultorinz.simpletodo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raultorinz.simpletodo.data.source.DefaultTaskRepository
import com.raultorinz.simpletodo.data.source.TasksRepository
import com.raultorinz.simpletodo.data.source.room.TaskRoomDatabase
import com.raultorinz.simpletodo.ui.addtask.AddTaskFragment

class MainActivity : AppCompatActivity(), AddTaskFragment.OnFragmentInteractionListener {
    lateinit var repository: DefaultTaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = DefaultTaskRepository(TaskRoomDatabase.getDatabase(this))

        setContentView(R.layout.main_activity)
    }

    override fun onFragmentInteraction(uri: Uri) {}
}