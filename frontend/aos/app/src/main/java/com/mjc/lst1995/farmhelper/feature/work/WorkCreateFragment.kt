package com.mjc.lst1995.farmhelper.feature.work

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import coil.load
import com.mjc.lst1995.farmhelper.MainActivity
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentWorkCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkCreateFragment : BaseFragment<FragmentWorkCreateBinding>(R.layout.fragment_work_create) {
    private val workCreateViewModel: WorkCreateViewModel by viewModels()
    private lateinit var activity: MainActivity

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                workCreateViewModel.setImageFile(it)
                binding.coverImageIV.load(it) {
                    crossfade(true)
                }
            } ?: run {
                workCreateViewModel.setImageFile(null)
                showMessage(getString(R.string.not_select_image_message))
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = workCreateViewModel
        pickImage()
    }

    private fun pickImage() {
        binding.coverImageIV.setOnClickListener {
            if (!permissionCheck()) {
                requestPermission()
                return@setOnClickListener
            }
            pickImageLauncher.launch("image/*")
        }
    }

    private fun permissionCheck(): Boolean {
        activity = requireActivity() as MainActivity
        return activity.permissionCheck()
    }

    private fun requestPermission() {
        activity.requestPermission()
    }
}
