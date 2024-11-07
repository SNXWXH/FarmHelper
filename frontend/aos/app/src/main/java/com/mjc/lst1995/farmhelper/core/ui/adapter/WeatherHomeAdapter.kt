package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mjc.lst1995.farmhelper.core.ui.model.Weather
import com.mjc.lst1995.farmhelper.core.ui.viewholder.WeatherHomeHolder
import com.mjc.lst1995.farmhelper.databinding.WeatherHomeHorderBinding

class WeatherHomeAdapter : ListAdapter<Weather, WeatherHomeHolder>(WeatherHomeDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WeatherHomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WeatherHomeHorderBinding.inflate(inflater, parent, false)
        return WeatherHomeHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WeatherHomeHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    companion object {
        object WeatherHomeDiffUtil : DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(
                oldItem: Weather,
                newItem: Weather,
            ) = oldItem.information == newItem.information

            override fun areContentsTheSame(
                oldItem: Weather,
                newItem: Weather,
            ) = oldItem == newItem
        }
    }
}
