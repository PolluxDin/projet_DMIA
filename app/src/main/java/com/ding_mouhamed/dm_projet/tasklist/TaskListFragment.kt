package com.ding_mouhamed.dm_projet.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ding_mouhamed.dm_projet.databinding.FragmentTaskListBinding
//import com.ding_mouhamed.dm_projet.detail.DetailActivity
//import java.util.*

class TaskListFragment : Fragment() {
    private var taskList = listOf<Task>(
//        Task(id = "id_1", title = "Task 1", description = "description 1"),
//        Task(id = "id_2", title = "Task 2"),
//        Task(id = "id_3", title = "Task 3")
    )
//    private var emptyList = listOf<Task>()
    private val adapter = TaskListAdapter()
    private lateinit var binding: FragmentTaskListBinding
//    val intent = Intent(context, DetailActivity::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        adapter.submitList(taskList)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.reList
        val button = binding.floatingActionButton
        recyclerView.adapter = adapter
        button.setOnClickListener{
//            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
//            taskList = taskList + newTask
//            adapter.submitList(taskList)
//            startActivity(intent)
        }

        adapter.onClickDelete = {
            taskList = taskList - it
            adapter.submitList(taskList)
        }
    }
}