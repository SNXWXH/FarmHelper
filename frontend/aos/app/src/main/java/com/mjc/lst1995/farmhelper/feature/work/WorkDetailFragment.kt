package com.mjc.lst1995.farmhelper.feature.work

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkTaskAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentWorkDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkDetailFragment : BaseFragment<FragmentWorkDetailBinding>(R.layout.fragment_work_detail) {
    private val args: WorkDetailFragmentArgs by navArgs()
    private val viewModel: WorkDetailViewModel by viewModels()
    private val listener: (Task) -> Unit = {
    }
    private val adapter = WorkTaskAdapter(listener)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.taskRV.adapter = adapter
        setNavItemSelected()
        getWorkTasks()
        getNickName()
        setSortObserver()
    }

    private fun getWorkTasks() {
        val work = args.work.cropId
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getWorkTaskDetails(work).collect {
                    if (viewModel.sortType.value!!) {
                        adapter.submitList(it)
                        return@collect
                    }
                    adapter.submitList(it.reversed())
                }
            }
        }
    }

    private fun setSortObserver() {
        viewModel.sortType.observe(viewLifecycleOwner) {
            if (it) {
                binding.taskSortTV.text = "최신 순"
            } else {
                binding.taskSortTV.text = "오래된 순"
            }
            adapter.submitList(adapter.currentList.reversed()) {
                binding.taskRV.scrollToPosition(0)
            }
        }
    }

    private fun getNickName() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nickName.collect {
                    setToolbarClientName(it)
                }
            }
        }
    }

    private fun setToolbarClientName(name: String?) {
        name?.let {
            binding.materialToolbar3.title = CLIENT_NAME_FORMAT.format(name)
        }
    }

    private fun setNavItemSelected() {
        binding.materialToolbar3.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> {
                    val action =
                        WorkDetailFragmentDirections.actionWorkDetailFragmentToTaskAddFragment(args.work.cropId)
                    findNavController().navigate(action)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    companion object {
        private const val CLIENT_NAME_FORMAT = "%s님의 작업 목록"
    }
}
