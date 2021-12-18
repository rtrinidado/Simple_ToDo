package com.raultorinz.simpletodo.ui.addtask

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.databinding.AddTaskFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

import com.raultorinz.simpletodo.BR.addTaskVM
import com.raultorinz.simpletodo.BR.addTaskView
import com.raultorinz.simpletodo.MainActivity
import com.raultorinz.simpletodo.ui.main.MainViewModel
import com.raultorinz.simpletodo.ui.main.MainViewModelFactory

class AddTaskFragment : Fragment() {
    private val myCalendar: Calendar = Calendar.getInstance()
    private lateinit var binding: AddTaskFragmentBinding

    private val viewModel by viewModels<AddTaskViewModel> {
        AddTaskViewModelFactory((activity as MainActivity).repository)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_task_fragment, container, false)
        binding.lifecycleOwner = this
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarTask)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val args = AddTaskFragmentArgs.fromBundle(it)
            if (args.idTask > 0) {
                viewModel.getPreviousTask(args.idTask)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(addTaskVM, viewModel)
        binding.setVariable(addTaskView, this)

        binding.toolbarTask.setNavigationOnClickListener {
            unfocused()
            Navigation.findNavController(it).popBackStack()
        }

        viewModel.task.observe(viewLifecycleOwner, {
            strikeText(it.completed)
        })
    }

    fun showDatePicker() {
        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }

        context?.let {
            DatePickerDialog(
                it, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    fun deleteTask() {
        unfocused()
        viewModel.deleteTask()
        view?.let { Navigation.findNavController(it).popBackStack() }
    }

    fun saveTask() {
        unfocused()
        viewModel.insertUpdateTask()
        view?.let { Navigation.findNavController(it).popBackStack() }
    }

    fun strikeText(isChecked: Boolean) {
        binding.nameTaskEdit.paintFlags =
            if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
    }

    private fun updateDate() {
        val format = getString(R.string.date_format)
        val local = Locale("es", "MX")
        val simpleDateFormat = SimpleDateFormat(format, local)
        binding.dateTask.text?.clear()
        binding.dateTask.text?.append(simpleDateFormat.format(myCalendar.time))
    }

    fun unfocused() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()
    }
}