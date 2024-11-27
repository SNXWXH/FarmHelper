package com.mjc.lst1995.farmhelper.feature.task

import android.os.Bundle
import android.view.View
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentTaskAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddFragment : BaseFragment<FragmentTaskAddBinding>(R.layout.fragment_task_add) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}
