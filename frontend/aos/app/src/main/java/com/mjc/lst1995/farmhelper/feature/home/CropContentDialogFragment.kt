package com.mjc.lst1995.farmhelper.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import coil.load
import com.mjc.lst1995.farmhelper.core.domain.model.crop.RecommendCrop
import com.mjc.lst1995.farmhelper.core.domain.model.crop.toDescription
import com.mjc.lst1995.farmhelper.databinding.FragmentCropContentDialogBinding
import com.mjc.lst1995.farmhelper.util.ImageUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CropContentDialogFragment(
    private val recommendCrop: RecommendCrop,
) : DialogFragment() {
    private var binding: FragmentCropContentDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentCropContentDialogBinding.inflate(inflater, container, false)
        setDescription(recommendCrop)
        setDismiss()
        return binding!!.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // 다이얼로그 크기 설정
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setDescription(recommendCrop: RecommendCrop) {
        binding!!.run {
            cropNameTV.text = recommendCrop.cropName
            val description = recommendCrop.toDescription()
            plantingSeasonTV.text = "심는 시기: " + description.plantingSeason
            harvestSeasonTV.text = "수확 시기: " + description.harvestSeason
            recommendCrop.imageUrl?.let {
                cropIV.load(it)
            } ?: run {
                cropIV.load(ImageUtil.getImageResource(recommendCrop.cropName))
            }
            contentTV.text = description.content
        }
    }

    private fun setDismiss() {
        binding!!.closeButton.setOnClickListener {
            dismiss()
        }
    }
}
