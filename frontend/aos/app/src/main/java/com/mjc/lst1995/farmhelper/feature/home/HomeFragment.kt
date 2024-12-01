package com.mjc.lst1995.farmhelper.feature.home

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.crop.RecommendCrop
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.RecommendCropAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var recommendCropAdapter: RecommendCropAdapter
    private val recommendCropListener: (RecommendCrop) -> Unit = { recommendCrop ->
        CropContentDialogFragment(recommendCrop).show(parentFragmentManager, "CropContentDialog")
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
        setDrawer()
        setNavItemSetting()
        setNavItemSelect()
        setRecommendedCrop()
        setDetailFragmentMove()
    }

    override fun onResume() {
        super.onResume()
        setDrawerClose()
    }

    private fun setBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setRecommendedCrop() {
        binding.run {
            recommendCropAdapter = RecommendCropAdapter(recommendCropListener)
            recommendCropRV.adapter = recommendCropAdapter
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.recommendedCrops.collect {
                    recommendCropAdapter.submitList(it)
                }
            }
        }
    }

    private fun setNavItemSetting() {
        viewModel.navigationItem.observe(viewLifecycleOwner) {
            binding.homeNV.menu.clear()
            val inflater = requireActivity().menuInflater
            inflater.inflate(it, binding.homeNV.menu)
        }
    }

    private fun setNavItemSelect() {
        binding.homeNV.setNavigationItemSelectedListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.menu_login -> navigateTo(R.layout.fragment_login)
                R.id.menu_works -> navigateTo(R.layout.fragment_works)
                R.id.menu_logout -> performLogout()
            }
            true
        }
    }

    private fun performLogout() {
        viewModel.firebaseSignOut()
        setDrawerClose()
    }

    private fun setDetailFragmentMove() {
        binding.todayWorkLogTV.setOnClickListener {
            navigateTo(R.layout.fragment_works)
        }
    }

    private fun setDrawer() {
        binding.homeTB.setOnMenuItemClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END)
            true
        }
    }

    private fun setDrawerClose() {
        binding.drawerLayout.closeDrawer(GravityCompat.END)
    }

    private fun navigateTo(
        @LayoutRes
        layoutId: Int,
    ) {
        when (layoutId) {
            R.layout.fragment_works -> findNavController().navigate(R.id.action_homeFragment_to_worksFragment)
            R.layout.fragment_login -> findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
}
