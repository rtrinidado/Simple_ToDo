package com.raultorinz.simpletodo.ui.addtask

import android.app.Activity
import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {
    private val myCalendar: Calendar = Calendar.getInstance()

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
                        viewModel.result = result
                        completeCheck.isChecked = result.completed
                        nameTask.text.clear()
                        nameTask.text.append(result.name)
                        nameTask.paintFlags = if (result.completed) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
                        description.text.clear()
                        description.text.append(result.description)
                        dateTask.text.clear()
                        dateTask.text.append(result.dateTask)
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


        val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }

        dateTask.setOnClickListener {
            context?.let { it ->
                DatePickerDialog(it, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
    }

    private fun updateDate() {
        val format = getString(R.string.date_format)
        val local = Locale("es", "MX")
        val simpleDateFormat = SimpleDateFormat(format, local)
        dateTask.text.clear()
        dateTask.text.append(simpleDateFormat.format(myCalendar.time))
    }

    private fun unfocused() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()
    }



}