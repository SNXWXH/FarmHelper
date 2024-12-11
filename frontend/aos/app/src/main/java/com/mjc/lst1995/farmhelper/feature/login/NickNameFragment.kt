package com.mjc.lst1995.farmhelper.feature.login

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentNickNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NickNameFragment : BaseFragment<FragmentNickNameBinding>(R.layout.fragment_nick_name) {
    private val nickNameViewModel: NickNameViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = nickNameViewModel
        setObserve()
    }

    private fun setObserve() {
        nickNameViewModel.isChanged.observe(viewLifecycleOwner) { isChanged ->
            isChanged?.let {
                if (isChanged) {
                    showMessage(getString(R.string.nick_name_commit_message))
                    navigateTo(R.layout.fragment_home)
                    return@observe
                }
                showMessage(getString(R.string.nick_name_error_message))
            }
        }
    }

    private fun navigateTo(
        @LayoutRes
        layoutId: Int,
    ) {
        when (layoutId) {
            R.layout.fragment_home -> findNavController().navigate(R.id.action_nickNameFragment_to_homeFragment)
        }
    }
}
