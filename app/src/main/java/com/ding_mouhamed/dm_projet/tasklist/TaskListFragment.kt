package com.ding_mouhamed.dm_projet.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ding_mouhamed.dm_projet.databinding.FragmentTaskListBinding
import java.util.*

class TaskListFragment : Fragment() {
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter()
    private lateinit var binding: FragmentTaskListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
//        adapter.currentList = taskList
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        adapter.submitList(taskList)
//        return super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val recyclerView = view.findViewById<RecyclerView>(R.id.reList)
//        val button = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val recyclerView = binding.reList
        val button = binding.floatingActionButton
        recyclerView.adapter = adapter
        button.setOnClickListener{
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            adapter.submitList(taskList)
        }

    }
}