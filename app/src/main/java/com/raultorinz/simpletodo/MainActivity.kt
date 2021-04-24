package com.raultorinz.simpletodo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raultorinz.simpletodo.ui.addtask.AddTaskFragment
import com.raultorinz.simpletodo.ui.main.MainFragment

class MainActivity : AppCompatActivity(), AddTaskFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onFragmentInteraction(uri: Uri) {}
}