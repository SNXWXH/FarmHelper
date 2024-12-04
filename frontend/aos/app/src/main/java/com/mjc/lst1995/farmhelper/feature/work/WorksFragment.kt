package com.mjc.lst1995.farmhelper.feature.work

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkGridAdapter
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkLinearAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentWorksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorksFragment : BaseFragment<FragmentWorksBinding>(R.layout.fragment_works) {
    private val worksViewModel: WorksViewModel by viewModels()

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
        setToolbar()
        setAdapters()
        setRecyclerView()
        setSelectRV()
        setObservers()
    }

    private fun setToolbar() {
        setMenuClickEvent()
    }

    private fun setAdapters() {
        gridAdapter = WorkGridAdapter(listener)
        linearAdapter = WorkLinearAdapter(listener)
    }

    private fun setRecyclerView() {
        binding.worksGridRV.adapter = gridAdapter
        binding.worksGridRV.layoutManager = GridLayoutManager(context, 2)
        binding.worksLinearRV.adapter = linearAdapter
    }

    private fun setSelectRV() {
        binding.listIV.setOnClickListener {
            binding.worksGridRV.visibility = View.GONE
            binding.worksLinearRV.visibility = View.VISIBLE
        }
        binding.gridIV.setOnClickListener {
            binding.worksGridRV.visibility = View.VISIBLE
            binding.worksLinearRV.visibility = View.GONE
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    worksViewModel.works.collect {
                        linearAdapter.submitList(it)
                        gridAdapter.submitList(it)
                    }
                }
                launch {
                    worksViewModel.nickName.collect {
                        setToolbarClientName(it)
                    }
                }
            }
        }
    }

    private fun setToolbarClientName(name: String?) {
        name?.let {
            binding.materialToolbar2.title = CLIENT_NAME_FORMAT.format(name)
        } ?: run {
            binding.materialToolbar2.title = getString(R.string.loading)
        }
    }

    private fun setMenuClickEvent() {
        binding.materialToolbar2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> {
                    findNavController().navigate(R.id.action_worksFragment_to_workCreateFragment)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    companion object {
        private const val CLIENT_NAME_FORMAT = "%s님의 작업일지"
    }
}
