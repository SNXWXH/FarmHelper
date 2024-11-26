package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.Work
import com.mjc.lst1995.farmhelper.databinding.HolderWorksListGridBinding

class WorkGridAdapter : ListAdapter<Work, WorkGridAdapter.WorkGridHolder>(WorkDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WorkGridHolder = WorkGridHolder.from(parent)

    override fun onBindViewHolder(
        holder: WorkGridHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class WorkGridHolder(
        private val binding: HolderWorksListGridBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(work: Work) {
            binding.workTitleTv.text = work.cropName
            binding.workDateTV.text = work.cropDate
        }


        companion object {
            fun from(parent: ViewGroup): WorkGridHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderWorksListGridBinding.inflate(inflater, parent, false)
                return WorkGridHolder(binding)
            }
        }
    }
}

object WorkDiffUtil : DiffUtil.ItemCallback<Work>() {
    override fun areItemsTheSame(p0: Work, p1: Work): Boolean {
        return p0.cropName == p1.cropName
    }

    override fun areContentsTheSame(p0: Work, p1: Work): Boolean {
        return p0 == p1
    }

}