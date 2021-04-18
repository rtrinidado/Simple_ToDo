package com.raultorinz.simpletodo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.adapter.RecyclerAdapter
import com.raultorinz.simpletodo.model.TaskModel
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class MainFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        collapsing_toolbar.title = Calendar.getInstance().time.toString()

        layoutManager = LinearLayoutManager(context)
        toDoList.layoutManager = layoutManager

        adapter = RecyclerAdapter(viewModel.getList())
        toDoList.adapter = adapter
        addButton.setOnClickListener {
            viewModel.createElement()
            (adapter as RecyclerAdapter).notifyDataSetChanged()
        }
    }

}