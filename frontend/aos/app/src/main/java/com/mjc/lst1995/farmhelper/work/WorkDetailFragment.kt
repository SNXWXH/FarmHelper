package com.mjc.lst1995.farmhelper.work

import android.os.Bundle
import android.view.View
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
        super.onViewCreated(view, savedInstanceState)
        binding.text.text = args.work.cropName + args.work.cropDate
    }
}
