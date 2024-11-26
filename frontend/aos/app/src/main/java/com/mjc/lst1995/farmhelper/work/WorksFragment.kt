package com.mjc.lst1995.farmhelper.work

import android.os.Bundle
import android.view.View
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentWorksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorksFragment : BaseFragment<FragmentWorksBinding>(R.layout.fragment_works) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialToolbar2.title = tempClientNameFormat.format(tempClientName)
        binding.linearChip.setOnClickListener {
            binding.worksGridRV.visibility = View.GONE
            binding.worksLinearRV.visibility = View.VISIBLE
        }
        binding.gridChip.setOnClickListener {
            binding.worksGridRV.visibility = View.VISIBLE
            binding.worksLinearRV.visibility = View.GONE
        }
    }

    companion object {
        private const val tempClientNameFormat = "%s님의 작업일지"
        private const val tempClientName = "손흥민"
    }
}
