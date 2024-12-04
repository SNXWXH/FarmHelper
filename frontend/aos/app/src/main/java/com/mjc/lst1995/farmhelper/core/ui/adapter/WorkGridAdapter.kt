package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import com.mjc.lst1995.farmhelper.core.ui.diff.WorkDiffUtil
import com.mjc.lst1995.farmhelper.databinding.HolderWorksListGridBinding

class WorkGridAdapter(
    private val listener: (Work) -> Unit,
) : ListAdapter<Work, WorkGridAdapter.WorkGridHolder>(WorkDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WorkGridHolder = WorkGridHolder.from(parent)

    override fun onBindViewHolder(
        holder: WorkGridHolder,
        position: Int,
    ) {
        holder.bind(currentList[position], listener)
    }

    class WorkGridHolder(
        private val binding: HolderWorksListGridBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            work: Work,
            listener: (Work) -> Unit,
        ) {
            binding.workTitleTv.text = work.cropName
            binding.workDateTV.text = work.cropDate
            imageLoad(work)

            binding.root.setOnClickListener {
                listener(work)
            }
        }

        private fun imageLoad(work: Work) {
            work.imageUrl?.let {
                binding.progressBar.visibility = View.VISIBLE
                binding.workTitleIV.load(it) {
                    listener(
                        onSuccess = { _, _ ->
                            binding.progressBar.visibility = View.GONE
                        },
                        onError = { _, _ ->
                            binding.progressBar.visibility = View.GONE
                        },
                    )
                }
            } ?: run {
                binding.workTitleIV.load(R.drawable.play_store_512)
                binding.progressBar.visibility = View.GONE
            }
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
