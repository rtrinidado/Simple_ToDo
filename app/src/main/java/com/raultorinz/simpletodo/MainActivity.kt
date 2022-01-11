package com.raultorinz.simpletodo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raultorinz.simpletodo.ui.addtask.AddTaskFragment

class MainActivity : AppCompatActivity(), AddTaskFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onFragmentInteraction(uri: Uri) {}
}