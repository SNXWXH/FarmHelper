package com.mjc.lst1995.farmhelper.feature.work

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentWorkDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkDetailFragment : BaseFragment<FragmentWorkDetailBinding>(R.layout.fragment_work_detail) {
    private val args: WorkDetailFragmentArgs by navArgs()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view,savedInstanceState)
        setNavItemSelected()
    }

    private fun setNavItemSelected() {
        binding.materialToolbar3.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> findNavController().navigate(R.id.action_workDetailFragment_to_taskAddFragment)
            }
            return@setOnMenuItemClickListener true
        }
    }
}
