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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.databinding.AddTaskFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

import com.raultorinz.simpletodo.BR.addTaskVM

class AddTaskFragment : Fragment() {
    private val myCalendar: Calendar = Calendar.getInstance()
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var binding: AddTaskFragmentBinding

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
                binding.deleteTask.isEnabled = true
                viewModel.getPreviousTask(args.idTask)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddTaskViewModel::class.java)
        binding.setVariable(addTaskVM, viewModel)

        binding.toolbarTask.setNavigationOnClickListener {
            unfocused()
            Navigation.findNavController(it).popBackStack()
        }

        binding.saveTask.setOnClickListener {
            unfocused()
            viewModel.insertUpdateTask()
            Navigation.findNavController(it).popBackStack()
        }

        binding.deleteTask.setOnClickListener {
            unfocused()
            viewModel.deleteTask()
            Navigation.findNavController(it).popBackStack()
        }

        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }

        binding.dateTask.setOnClickListener {
            context?.let {
                DatePickerDialog(
                    it, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        binding.completeCheck.setOnClickListener {
            strikeText(binding.completeCheck.isChecked)
        }

        viewModel.task.observe(viewLifecycleOwner, {
            strikeText(it.completed)
        })
    }

    private fun strikeText(isChecked: Boolean) {
        binding.nameTask.paintFlags =
            if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else Paint.HINTING_OFF
    }

    private fun updateDate() {
        val format = getString(R.string.date_format)
        val local = Locale("es", "MX")
        val simpleDateFormat = SimpleDateFormat(format, local)
        binding.dateTask.text.clear()
        binding.dateTask.text.append(simpleDateFormat.format(myCalendar.time))
    }

    private fun unfocused() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        view?.clearFocus()
    }
}