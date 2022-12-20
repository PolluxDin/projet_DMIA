package com.ding_mouhamed.dm_projet.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ding_mouhamed.dm_projet.R
import java.util.*

class TaskListFragment : Fragment() {
    private var taskList = listOf("Task 1", "Task 2", "Task 3")
    private val adapter = TaskListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        adapter.currentList = taskList
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reList)
        recyclerView.adapter = adapter
    }
}