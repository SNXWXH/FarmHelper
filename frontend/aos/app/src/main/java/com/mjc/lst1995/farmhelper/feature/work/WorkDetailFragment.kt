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
    private lateinit var editSelectDialogFragment: EditSelectDialogFragment
    private val deleteSelectListener = { task: Task ->
        lifecycleScope.launch {
            viewModel.deleteTask(task.workId, args.work.cropId)
            editSelectDialogFragment.dismiss()
            showMessage("삭제 완료 되었습니다.")
        }
    }

    private val editSelectListener = { task: Task ->
    }

    private val longClickListener: (Task) -> Unit = { task: Task ->
        editSelectDialogFragment =
            EditSelectDialogFragment(task, editSelectListener, deleteSelectListener)
        editSelectDialogFragment.show(childFragmentManager, "selectDialog")
    }
    private val adapter = WorkTaskAdapter(longClickListener)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.taskRV.adapter = adapter
        setNavItemSelected()
        getWorkTasks()
        setSortObserver()
        getTodayTask()
        setScroll()
    }

    private fun getWorkTasks() {
        val work = args.work.cropId
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getWorkTaskDetails(work).collect {
                    val todayTask =
                        it.filter { it.workDate == binding.todayTaskInclude.taskDateTV.text }
                    if (todayTask.isEmpty()) {
                        binding.todayTaskInclude.taskContentTV.text = "오늘 작업을 추가해 주세요."
                    } else {
                        binding.todayTaskInclude.taskContentTV.text =
                            "${todayTask.size}건의 오늘 작업이 있습니다."
                    }
                    if (viewModel.sortType.value!!) {
                        adapter.submitList(it.reversed())
                        return@collect
                    }
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun setScroll() {
        binding.todayTaskInclude.root.setOnClickListener {
            val index =
                adapter.currentList.indexOfFirst { binding.todayTaskInclude.taskDateTV.text == it.workDate }
            if (index != -1) {
                binding.taskRV.scrollToPosition(index)
            }
        }
    }

    private fun getTodayTask() {
        val work = args.work.cropId
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getWorkTaskOtherDetail(work).collect {
                    binding.todayTaskInclude.taskDateTV.text = it.today
                    binding.todayTaskInclude.taskWeatherTV.text = it.weather.description
                    binding.todayTaskInclude.taskTemperatureTV.text = it.weather.temperature
                    binding.cropNameTV.text = it.cropName + "작업 내역"
                    setToolbarClientName(it.nickname)
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

    private fun setToolbarClientName(name: String) {
        binding.materialToolbar3.title = CLIENT_NAME_FORMAT.format(name)
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
        private const val CLIENT_NAME_FORMAT = "%s님 작업 목록"
    }
}
