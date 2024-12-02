package com.mjc.lst1995.farmhelper.feature.work

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkGridAdapter
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkLinearAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentWorksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorksFragment : BaseFragment<FragmentWorksBinding>(R.layout.fragment_works) {
    private val listener = { work: Work ->
        val action = WorksFragmentDirections.actionWorksFragmentToWorkDetailFragment(work)
        findNavController().navigate(action)
    }

    private lateinit var gridAdapter: WorkGridAdapter
    private lateinit var linearAdapter: WorkLinearAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialToolbar2.title = tempClientNameFormat.format(tempClientName)

        binding.materialToolbar2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> {
                    findNavController().navigate(R.id.action_worksFragment_to_workCreateFragment)
                }
            }
            return@setOnMenuItemClickListener true
        }

        gridAdapter = WorkGridAdapter(listener)
        linearAdapter = WorkLinearAdapter(listener)
        binding.worksGridRV.adapter = gridAdapter
        binding.worksGridRV.layoutManager = GridLayoutManager(context, 2)
        binding.worksLinearRV.adapter = linearAdapter
//        gridAdapter.submitList(works)
//        linearAdapter.submitList(works)

        binding.listIV.setOnClickListener {
            binding.worksGridRV.visibility = View.GONE
            binding.worksLinearRV.visibility = View.VISIBLE
        }
        binding.gridIV.setOnClickListener {
            binding.worksGridRV.visibility = View.VISIBLE
            binding.worksLinearRV.visibility = View.GONE
        }
    }

    companion object {
        private const val tempClientNameFormat = "%s님의 작업일지"
        private const val tempClientName = "손흥민"
    }
}
