package com.mjc.lst1995.farmhelper.home

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.WeatherHomeAdapter
import com.mjc.lst1995.farmhelper.core.ui.model.Weather
import com.mjc.lst1995.farmhelper.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val weathers =
        listOf(
            Weather("맑음"),
            Weather("흐림"),
            Weather("비"),
            Weather("눈"),
            Weather("맑음"),
            Weather("흐림"),
            Weather("맑음"),
            Weather("흐림"),
            Weather("비"),
            Weather("눈"),
            Weather("맑음"),
            Weather("흐림"),
        )

    private lateinit var adapter: WeatherHomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialToolbar.setOnMenuItemClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
            true
        }

        adapter = WeatherHomeAdapter()
        adapter.submitList(weathers)
        binding.run {
            weathersRV.adapter = adapter
            weathersRV.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}
