package com.raultorinz.simpletodo.ui.main

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerAdapter? = null

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        collapsing_toolbar.setContentScrimColor(resources.getColor(R.color.navy_blue, null))
        viewModel.getAllTasks()?.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let { adapter?.setTaskList(it) }
        })

        layoutManager = LinearLayoutManager(context)
        toDoList.layoutManager = layoutManager
        adapter = RecyclerAdapter(viewModel, requireContext())
        toDoList.adapter = adapter

        addButton.setOnClickListener {
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
        val formatter = DateTimeFormatter.ofPattern("EEEE dd MMM", local)
        collapsing_toolbar.title = time.format(formatter)
    }
}