package com.ding_mouhamed.dm_projet.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ding_mouhamed.dm_projet.databinding.ItemTaskBinding


object MyTaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id
    // comparaison: est-ce la même "entité" ? => même id?
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem == newItem// comparaison: est-ce le même "contenu" ? => mêmes valeurs? (avec data class: simple égalité)
    }
}
// l'IDE va râler ici car on a pas encore implémenté les méthodes nécessaires
class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(MyTaskDiffCallback) {

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        private val titleView = binding.taskTitle
        private val descriptionView = binding.taskDescription
        private val deleteButton = binding.imageDeleteButton
        private val editButton = binding.imageEditButton
        fun bind(taskItem: Task) {
            titleView.text = taskItem.title
            titleView.textSize = 20f
            descriptionView.text = taskItem.description
            descriptionView.textSize = 20f
            deleteButton.setOnClickListener { onClickDelete(taskItem) }
            editButton.setOnClickListener { onClickEdit(taskItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bind(currentList[position])
    }

    var onClickDelete : (Task) -> Unit = {}

    var onClickEdit : (Task) -> Unit = {}

}