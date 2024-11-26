package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.Work
import com.mjc.lst1995.farmhelper.core.ui.diff.WorkDiffUtil
import com.mjc.lst1995.farmhelper.databinding.HolderWorksListTitleBinding

class WorkLinearAdapter : ListAdapter<Work, WorkLinearAdapter.WorkLinearHolder>(WorkDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WorkLinearHolder = WorkLinearHolder.from(parent)

    override fun onBindViewHolder(
        holder: WorkLinearHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class WorkLinearHolder(
        private val binding: HolderWorksListTitleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(work: Work) {
            binding.workTitleTV.text = work.cropName
            binding.workDateTV.text = work.cropDate
        }

        companion object {
            fun from(parent: ViewGroup): WorkLinearHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderWorksListTitleBinding.inflate(inflater, parent, false)
                return WorkLinearHolder(binding)
            }
        }
    }
}