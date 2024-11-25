package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mjc.lst1995.farmhelper.core.ui.adapter.CropHomeAdapter.CropHomeHolder.Companion.from
import com.mjc.lst1995.farmhelper.core.domain.model.Crop
import com.mjc.lst1995.farmhelper.databinding.HolderRecommendedCropBinding

class CropHomeAdapter : ListAdapter<Crop, CropHomeAdapter.CropHomeHolder>(CropHomeDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CropHomeHolder = from(parent)

    override fun onBindViewHolder(
        holder: CropHomeHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class CropHomeHolder(
        private val binding: HolderRecommendedCropBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(crop: Crop) {
            binding.cropNameTV.text = crop.name
        }

        companion object {
            fun from(parent: ViewGroup): CropHomeHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderRecommendedCropBinding.inflate(inflater, parent, false)
                return CropHomeHolder(binding)
            }
        }
    }

    object CropHomeDiffUtil : DiffUtil.ItemCallback<Crop>() {
        override fun areItemsTheSame(
            oldItem: Crop,
            newItem: Crop,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Crop,
            newItem: Crop,
        ) = oldItem == newItem
    }
}
