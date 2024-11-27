package com.mjc.lst1995.farmhelper.feature.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentNickNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NickNameFragment : BaseFragment<FragmentNickNameBinding>(R.layout.fragment_nick_name) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
    }

    private fun setButtonClickListener() {
        binding.nickNamePickBT.setOnClickListener {
            findNavController().navigate(R.id.action_nickNameFragment_to_homeFragment)
        }
    }
}
