@file:Suppress("DEPRECATION")

package com.ding_mouhamed.dm_projet.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ding_mouhamed.dm_projet.data.API
import com.ding_mouhamed.dm_projet.databinding.FragmentTaskListBinding
import com.ding_mouhamed.dm_projet.detail.DetailActivity
import kotlinx.coroutines.launch


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
    private val createTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    // dans cette callback on récupèrera la task et on l'ajoutera à la liste
//        val task = result.data?.getSerializableExtra("task") as Task?
            val task = result.data?.getSerializableExtra("task") as Task?
        taskList = taskList + task!!
        adapter.submitList(taskList)
    }

    private val viewModel: TasksListViewModel by viewModels()

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
        val intent = Intent(context, DetailActivity::class.java)
        val recyclerView = binding.reList
        val button = binding.floatingActionButton
        recyclerView.adapter = adapter
        button.setOnClickListener{
//            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
//            taskList = taskList + newTask
//            adapter.submitList(taskList)
//            startActivity(intent)
            createTask.launch(intent)
        }

        adapter.onClickDelete = {
            taskList = taskList - it
            adapter.submitList(taskList)
        }

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                // cette lambda est executée à chaque fois que la liste est mise à jour dans le VM
                // -> ici, on met à jour la liste dans l'adapter
                adapter.submitList(newList)
                taskList = newList
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        lifecycleScope.launch {
            mySuspendMethod()
        }
    }

    private suspend fun mySuspendMethod(){
//        // Ici on ne va pas gérer les cas d'erreur donc on force le crash avec "!!"
        try {
            val user = API.userWebService.fetchUser().body()!!
            binding.userTextView.text = user.name
        }
        catch (e:java.lang.NullPointerException){
            println("crash here")
        }
    }
}