package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.crop.BestCrop
import com.mjc.lst1995.farmhelper.databinding.HolderBestCropBinding

class BestCropsAdapter(
    private val bestCropListener: (BestCrop) -> Unit,
) : ListAdapter<BestCrop, BestCropsAdapter.BestCropHomeHolder>(BestCropHomeDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BestCropHomeHolder = BestCropHomeHolder.from(parent)

    override fun onBindViewHolder(
        holder: BestCropHomeHolder,
        position: Int,
    ) {
        holder.bind(currentList[position], bestCropListener, position)
    }

    class BestCropHomeHolder(
        private val binding: HolderBestCropBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            bestCrop: BestCrop,
            bestCropListener: (BestCrop) -> Unit,
            position: Int,
        ) {
            binding.cropNameTV.text = bestCrop.cropName
            binding.cropRankTV.text = "${position + 1} 위"
            binding.cropCountTV.text = "심은 횟수 : ${bestCrop.count}회"
            binding.root.setOnClickListener {
                bestCropListener(bestCrop)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BestCropHomeHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderBestCropBinding.inflate(inflater, parent, false)
                return BestCropHomeHolder(binding)
            }
        }
    }

    object BestCropHomeDiffUtil : DiffUtil.ItemCallback<BestCrop>() {
        override fun areItemsTheSame(
            oldItem: BestCrop,
            newItem: BestCrop,
        ) = oldItem.cropName == newItem.cropName

        override fun areContentsTheSame(
            oldItem: BestCrop,
            newItem: BestCrop,
        ) = oldItem == newItem
    }
}
