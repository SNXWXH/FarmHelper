package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.domain.model.crop.RecommendCrop
import com.mjc.lst1995.farmhelper.databinding.HolderRecommendedCropBinding

class RecommendCropAdapter : ListAdapter<RecommendCrop, RecommendCropAdapter.CropHomeHolder>(RecommendCropHomeDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CropHomeHolder = CropHomeHolder.from(parent)

    override fun onBindViewHolder(
        holder: CropHomeHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class CropHomeHolder(
        private val binding: HolderRecommendedCropBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendCrop: RecommendCrop) {
            binding.cropNameTV.text = recommendCrop.cropName
            recommendCrop.description
        }

        companion object {
            fun from(parent: ViewGroup): CropHomeHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderRecommendedCropBinding.inflate(inflater, parent, false)
                return CropHomeHolder(binding)
            }
        }
    }

    object RecommendCropHomeDiffUtil : DiffUtil.ItemCallback<RecommendCrop>() {
        override fun areItemsTheSame(
            oldItem: RecommendCrop,
            newItem: RecommendCrop,
        ) = oldItem.cropName == newItem.cropName

        override fun areContentsTheSame(
            oldItem: RecommendCrop,
            newItem: RecommendCrop,
        ) = oldItem == newItem
    }
}
