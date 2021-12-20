package com.raultorinz.simpletodo.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.adapter.RecyclerAdapter

import com.raultorinz.simpletodo.BR.mainFragmentVM
import com.raultorinz.simpletodo.BR.mainFragmentView
import com.raultorinz.simpletodo.MainActivity
import com.raultorinz.simpletodo.TodoApplication
import com.raultorinz.simpletodo.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var adapterToDo: RecyclerAdapter? = null
    private var adapterDone: RecyclerAdapter? = null
    private lateinit var binding: MainFragmentBinding

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory((requireContext().applicationContext as TodoApplication).repository)
    }

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
        binding.setVariable(mainFragmentVM, viewModel)
        binding.setVariable(mainFragmentView, this)

        binding.collapsingToolbar.setContentScrimColor(resources.getColor(R.color.navy_blue, null))

        viewModel.uncompletedTasks?.observe(viewLifecycleOwner, {
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.showDate()
    }

    fun goToAddTask() {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.mainFragment_to_addTaskFragment)
        }
    }
}