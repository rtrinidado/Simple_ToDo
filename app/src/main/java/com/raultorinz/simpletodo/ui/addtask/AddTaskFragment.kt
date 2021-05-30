package com.raultorinz.simpletodo.ui.addtask

import android.app.Activity
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.room.Task
import kotlinx.android.synthetic.main.add_task_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskFragment : Fragment() {
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    private lateinit var viewModel: AddTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).setSupportActionBar(toolbarTask)
        return inflater.inflate(R.layout.add_task_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val args = AddTaskFragmentArgs.fromBundle(it)
            if (args.idTask > 0) {
                deleteTask.isEnabled = true
                CoroutineScope(Dispatchers.Main).launch {
                    val result: Task? = viewModel.showTask(args.idTask).await()
                    if (result != null) {
                        completeCheck.isChecked = result.completed
                        nameTask.text.clear()
                        nameTask.text.append(result.name)
                        nameTask.paintFlags = if (result.completed) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
                        description.text.clear()
                        description.text.append(result.description)
                        dateTask.text = result.dateTask
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddTaskViewModel::class.java)

        toolbarTask.setNavigationOnClickListener {
            unfocused()
            Navigation.findNavController(it).popBackStack()
        }

        saveTask.setOnClickListener {
            unfocused()
            viewModel.insert(
                    Task(completeCheck.isChecked, nameTask.text.toString(), description.text.toString(), dateTask.text.toString()))
            Navigation.findNavController(it).popBackStack()
        }

        deleteTask.setOnClickListener {
            unfocused()
            viewModel.deleteTask()
            Navigation.findNavController(it).popBackStack()
        }
    }

    fun unfocused() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()
    }

}