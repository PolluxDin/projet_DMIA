package com.ding_mouhamed.dm_projet.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ding_mouhamed.dm_projet.data.API
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TasksListViewModel : ViewModel() {
    private val webService = API.tasksWebService

    val tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchTasks() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedTasks = response.body()!!
            tasksStateFlow.value = fetchedTasks // on modifie le flow, ce qui déclenche ses observers
        }
    }

    fun add(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task) // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val toAddTask = response.body()!!
            tasksStateFlow.value =
                tasksStateFlow.value + toAddTask
        }
    }

    fun edit(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task) // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val updatedTask = response.body()!!
            tasksStateFlow.value =
                tasksStateFlow.value.map { if (it.id == updatedTask.id) updatedTask else it }
        }
    }

    fun remove(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(task.id) // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }

            tasksStateFlow.value =
                tasksStateFlow.value - task
        }
    }
}