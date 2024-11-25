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
    }
}
