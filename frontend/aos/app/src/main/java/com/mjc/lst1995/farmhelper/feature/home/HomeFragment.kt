package com.mjc.lst1995.farmhelper.feature.home

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.GravityCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.Crop
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.CropHomeAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    @Inject
    lateinit var auth: FirebaseAuth

    private val recommendedCrops =
        listOf(
            Crop("1", "감자", "", "맛있다1."),
            Crop("2", "감자", "", "맛있다2."),
            Crop("3", "감자", "", "맛있다3."),
            Crop("4", "감자", "", "맛있다4."),
            Crop("5", "감자", "", "맛있다5."),
            Crop("6", "감자", "", "맛있다6."),
            Crop("7", "감자", "", "맛있다7."),
            Crop("8", "감자", "", "맛있다.8"),
        )

    private lateinit var adapter: CropHomeAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setDrawer()
        setNavItemSetting()
        setNavItemSelect()
        setRecommendedCrop()
        setDetailFragmentMove()
    }

    private fun setRecommendedCrop() {
        binding.run {
            adapter = CropHomeAdapter()
            adapter.submitList(recommendedCrops)
            recommendCropRV.adapter = adapter
        }
    }

    private fun setNavItemSetting() {
        binding.homeNV.menu.clear()
        val inflater = requireActivity().menuInflater
        if (auth.uid != null) {
            inflater.inflate(R.menu.menu_login, binding.homeNV.menu)
            return
        }
        inflater.inflate(R.menu.menu_not_login, binding.homeNV.menu)
    }

    private fun setNavItemSelect() {
        binding.homeNV.setNavigationItemSelectedListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.menu_login -> navigateTo(R.layout.fragment_login)
                R.id.menu_works -> navigateTo(R.layout.fragment_works)
                R.id.menu_logout -> {
                    performLogout()
                    navigateTo(R.layout.fragment_home)
                }
            }
            true
        }
    }

    private fun performLogout() {
        auth.signOut()
        setNavItemSetting()
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

    private fun navigateTo(
        @LayoutRes
        layoutId: Int,
    ) {
        when (layoutId) {
            R.layout.fragment_home ->
                findNavController().navigate(
                    R.id.action_homeFragment_self,
                    null,
                    navOptions(),
                )

            R.layout.fragment_works -> findNavController().navigate(R.id.action_homeFragment_to_worksFragment)
            R.layout.fragment_login -> findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun navOptions(): NavOptions {
        val navController = findNavController()
        return NavOptions
            .Builder()
            .setPopUpTo(navController.graph.startDestinationId, inclusive = true) // 시작 지점까지 모두 팝업
            .build()
    }
}
