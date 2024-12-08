package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import com.mjc.lst1995.farmhelper.databinding.HolderTaskBinding

class TodayTaskAdapter : ListAdapter<CropTask, TodayTaskAdapter.TodayTaskHolder>(CropTaskDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TodayTaskHolder = TodayTaskHolder.from(parent)

    override fun onBindViewHolder(
        holder: TodayTaskHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class TodayTaskHolder(
        private val binding: HolderTaskBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cropTask: CropTask) {
            binding.taskTitleTV.text = cropTask.workName
            binding.taskContentTV.text = cropTask.workContent
        }

        companion object {
            fun from(parent: ViewGroup): TodayTaskHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderTaskBinding.inflate(inflater, parent, false)
                return TodayTaskHolder(binding)
            }
        }
    }

    object CropTaskDiffUtil : DiffUtil.ItemCallback<CropTask>() {
        override fun areItemsTheSame(
            oldItem: CropTask,
            newItem: CropTask,
        ) = oldItem.workContent == newItem.workContent

        override fun areContentsTheSame(
            oldItem: CropTask,
            newItem: CropTask,
        ) = oldItem == newItem
    }
}
