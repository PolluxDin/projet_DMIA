//@file:Suppress("DEPRECATION")

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

import coil.load
import android.widget.ImageView


class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()
    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TasksListViewModel by viewModels()

    private val createTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = result.data?.getSerializableExtra("Task") as Task?
            val boolean = task == null
            try {
                viewModel.add(task!!)
            }
            catch (e:java.lang.NullPointerException){
                println("crash here")
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(context, DetailActivity::class.java)
        val recyclerView = binding.reList
        val button = binding.floatingActionButton
        val imageView = binding.userImageView
        recyclerView.adapter = adapter
        button.setOnClickListener{
            createTask.launch(intent)
        }

        adapter.onClickDelete = {
            viewModel.remove(it)
        }

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                adapter.submitList(newList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        var imageView = binding.userImageView
        lifecycleScope.launch {
            mySuspendMethod()

        }
        imageView.load("https://goo.gl/gEgYUd")
    }

    private suspend fun mySuspendMethod(){
        try {
            val user = API.userWebService.fetchUser().body()!!
            binding.userTextView.text =  user.name
        }
        catch (e:java.lang.NullPointerException){
            println("crash here")
        }
    }
}