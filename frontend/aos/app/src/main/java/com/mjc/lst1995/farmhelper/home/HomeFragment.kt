package com.mjc.lst1995.farmhelper.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjc.lst1995.farmhelper.core.ui.WeatherHomeAdapter
import com.mjc.lst1995.farmhelper.core.ui.model.Weather
import com.mjc.lst1995.farmhelper.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.materialToolbar.setOnMenuItemClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
            true
        }

        adapter = WeatherHomeAdapter()
        adapter.submitList(weathers)
        binding.run {
            weathersRV.adapter = adapter
            weathersRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        return binding.root
    }
}
