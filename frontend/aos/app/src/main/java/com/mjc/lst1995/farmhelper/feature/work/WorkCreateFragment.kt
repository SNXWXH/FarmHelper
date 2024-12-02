package com.mjc.lst1995.farmhelper.feature.work

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.mjc.lst1995.farmhelper.MainActivity
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.databinding.FragmentWorkCreateBinding

class WorkCreateFragment : BaseFragment<FragmentWorkCreateBinding>(R.layout.fragment_work_create) {
    private lateinit var activity: MainActivity

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.coverImageIV.load(it) {
                    crossfade(true)
                }
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
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
