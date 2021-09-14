package com.raultorinz.simpletodo.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.adapter.RecyclerAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

import com.raultorinz.simpletodo.BR.mainFragmentVM
import com.raultorinz.simpletodo.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var adapterToDo: RecyclerAdapter? = null
    private var adapterDone: RecyclerAdapter? = null
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.setVariable(mainFragmentVM, viewModel)

        binding.collapsingToolbar.setContentScrimColor(resources.getColor(R.color.navy_blue, null))

        viewModel.incompletedTasks?.observe(viewLifecycleOwner, {
            adapterToDo?.setTaskList(it)
        })

        viewModel.completedTasks?.observe(viewLifecycleOwner, {
            adapterDone?.setTaskList(it)
        })

        binding.contentMain.toDoList.layoutManager = LinearLayoutManager(context)
        binding.contentMain.doneList.layoutManager = LinearLayoutManager(context)

        adapterToDo = RecyclerAdapter(viewModel, requireContext())
        adapterDone = RecyclerAdapter(viewModel, requireContext())

        binding.contentMain.toDoList.adapter = adapterToDo
        binding.contentMain.doneList.adapter = adapterDone

        binding.addButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.mainFragment_to_addTaskFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        showDate()
    }

    private fun showDate() {
        val local = Locale("es", "MX")
        val time = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", local)
        binding.collapsingToolbar.title = time.format(formatter)
    }
}