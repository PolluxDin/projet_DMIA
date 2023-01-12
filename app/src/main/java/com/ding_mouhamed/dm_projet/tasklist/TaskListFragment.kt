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
import com.ding_mouhamed.dm_projet.user.UserActivity
import kotlinx.coroutines.launch

import coil.load
import com.ding_mouhamed.dm_projet.R


class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()
    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TasksListViewModel by viewModels()

    private val createTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = result.data?.getSerializableExtra("Task") as Task?
            viewModel.add(task!!)
            viewModel.refresh()

    }

    private val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = result.data?.getSerializableExtra("task") as Task?
            viewModel.edit(task!!)
            viewModel.refresh()
    }

    private val imageEditor = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->


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

        val recyclerView = binding.reList
        recyclerView.adapter = adapter

        val button = binding.floatingActionButton
        val imageView = binding.userImageView

        button.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            createTask.launch(intent)
        }

        imageView.setOnClickListener{
            val intent = Intent(context, UserActivity::class.java)
            imageEditor.launch(intent)
        }

        adapter.onClickDelete = {
            viewModel.remove(it)
        }

        adapter.onClickEdit = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Task",it)
            editTask.launch(intent)
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
        lifecycleScope.launch {
            mySuspendMethod()
        }
    }

    private suspend fun mySuspendMethod(){
        try {
            val user = API.userWebService.fetchUser().body()!!
            binding.userTextView.text =  user.name
            binding.userTextView.textSize = 30f
            binding.userImageView.load(user.avatar) {
                error(R.drawable.ic_launcher_background) // image par défaut en cas d'erreur
            }
        }
        catch (e:java.lang.NullPointerException){
            println("crash here")
        }



    }
}