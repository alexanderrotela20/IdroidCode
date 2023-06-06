package com.ardev.idroid.ui.projects

import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import com.ardev.builder.project.Project
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import com.ardev.idroid.databinding.ItemProjectBinding
import com.ardev.idroid.common.ext.*
import com.ardev.idroid.common.*

class ProjectsAdapter: ListAdapter<Project, ProjectHolder>(
AsyncDifferConfig.Builder(DiffCallback<Project>()).build()
    ) {

	 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): = ProjectHolder(
   parent bind ItemProjectBinding::inflate)
     

	override fun onBindViewHolder(holder: ProjectHolder, position: Int) = holder.bind(currentList[position])


  inner class ProjectHolder(binding: ItemProjectBinding) : ViewHolder(binding.root) {
        fun bind(project: Project) = with(binding) {
         	name.text = project.getRootFile().name
            
            root.setOnClickListener {
              onItemClick?.let { click ->
               click(project)
               }
            }
            root.setOnLongClickListener {
               onItemLongClick?.let { click ->
               click(project)
               }
              true
            }
        }
    }

	
	protected var onItemClick : ((Project) -> Unit)? = null
	protected var onItemLongClick : ((Project) -> Unit)? = null

    fun setOnItemClick(listener: (Project) -> Unit){
        onItemClick = listener
    }
    fun setOnItemLongClick(listener: (Project) -> Unit){
        onItemLongClick = listener
    }

    
  }