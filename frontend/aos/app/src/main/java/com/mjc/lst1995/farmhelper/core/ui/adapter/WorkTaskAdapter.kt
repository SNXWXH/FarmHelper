package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.databinding.HolderTaskDetailBinding

class WorkTaskAdapter(
    private val listener: (Task) -> Unit,
) : ListAdapter<Task, WorkTaskAdapter.WorkTaskHolder>(TaskDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WorkTaskHolder = WorkTaskHolder.from(parent)

    override fun onBindViewHolder(
        holder: WorkTaskHolder,
        position: Int,
    ) {
        holder.bind(currentList[position], listener)
    }

    class WorkTaskHolder(
        private val binding: HolderTaskDetailBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            task: Task,
            listener: (Task) -> Unit,
        ) {
            binding.taskDateTV.text = task.workDate
            binding.taskContentTV.text = task.workContent
            binding.taskWeatherTV.text = task.workWeather
            binding.taskTemperatureTV.text = task.workTemperature
        }

        companion object {
            fun from(parent: ViewGroup): WorkTaskHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderTaskDetailBinding.inflate(inflater, parent, false)
                return WorkTaskHolder(binding)
            }
        }
    }

    object TaskDiffUtil : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(
            oldItem: Task,
            newItem: Task,
        ) = oldItem.workId == newItem.workId

        override fun areContentsTheSame(
            oldItem: Task,
            newItem: Task,
        ) = oldItem == newItem
    }
}
