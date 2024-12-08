package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.databinding.HolderCreateTaskBinding

class RecommendTaskAdapter(
    private val recommendTaskListener: (RecommendTask) -> Unit,
) : ListAdapter<RecommendTask, RecommendTaskAdapter.RecommendTaskHolder>(RecommendTaskDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecommendTaskHolder = RecommendTaskHolder.from(parent)

    override fun onBindViewHolder(
        holder: RecommendTaskHolder,
        position: Int,
    ) {
        holder.bind(currentList[position], recommendTaskListener)
    }

    class RecommendTaskHolder(
        private val binding: HolderCreateTaskBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            recommendTask: RecommendTask,
            recommendTaskListener: (RecommendTask) -> Unit,
        ) {
            binding.taskContentTV.text = recommendTask.content
            binding.checkBox.isChecked = recommendTask.isChecked

            binding.root.setOnClickListener {
                binding.checkBox.isChecked = !binding.checkBox.isChecked
                recommendTaskListener(recommendTask)
            }
        }

        companion object {
            fun from(parent: ViewGroup): RecommendTaskHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderCreateTaskBinding.inflate(inflater, parent, false)
                return RecommendTaskHolder(binding)
            }
        }
    }

    object RecommendTaskDiffUtil : DiffUtil.ItemCallback<RecommendTask>() {
        override fun areItemsTheSame(
            oldItem: RecommendTask,
            newItem: RecommendTask,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RecommendTask,
            newItem: RecommendTask,
        ) = oldItem == newItem
    }
}
