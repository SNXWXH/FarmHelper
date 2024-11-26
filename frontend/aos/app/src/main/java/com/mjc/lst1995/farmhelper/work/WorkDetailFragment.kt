package com.mjc.lst1995.farmhelper.work

import android.os.Bundle
import android.view.View
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.Work
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentWorkDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class WorkDetailFragment : BaseFragment<FragmentWorkDetailBinding>(R.layout.fragment_work_detail) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val jsonString = arguments?.getString("Work") ?: return
        val work = Json.decodeFromString<Work>(jsonString)
        binding.text.text = work.cropName
    }
}
