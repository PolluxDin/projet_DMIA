package com.ding_mouhamed.dm_projet.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ding_mouhamed.dm_projet.databinding.ItemTaskBinding


object MyTaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return true// comparaison: est-ce la même "entité" ? => même id?
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return true// comparaison: est-ce le même "contenu" ? => mêmes valeurs? (avec data class: simple égalité)
    }
}
// l'IDE va râler ici car on a pas encore implémenté les méthodes nécessaires
class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(MyTaskDiffCallback) {

//    var currentList: List<Task> = emptyList()

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        private val idView = binding.taskId
        private val titleView = binding.taskTitle
        private val descriptionView = binding.taskDescription
        fun bind(task: Task) {
            // on affichera les données ici
            idView.text = task.id
            titleView.text = task.title
            descriptionView.text = task.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bind(currentList[position])
    }

//    override fun getItemCount(): Int {
//
//        return currentList.size
//    }
}