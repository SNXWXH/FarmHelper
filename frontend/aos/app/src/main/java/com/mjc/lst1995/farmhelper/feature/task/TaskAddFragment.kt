package com.mjc.lst1995.farmhelper.feature.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.RecommendTaskAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentTaskAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddFragment : BaseFragment<FragmentTaskAddBinding>(R.layout.fragment_task_add) {
    private val viewModel: TaskAddViewModel by viewModels()

    private val args: TaskAddFragmentArgs by navArgs()
    private val listener: (RecommendTask) -> Unit = {
        viewModel.updateRecommendTask(it)
    }
    private val adapter = RecommendTaskAdapter(listener)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.todayTaskRV.adapter = adapter
        getRecommendTasks()
        addCustomTask()
        setSaveTasks()
        setObserver()
    }

    private fun setObserver() {
        viewModel.recommendTask.observe(viewLifecycleOwner) {
            if (adapter.itemCount != it.size) {
                adapter.submitList(it.toList()) {
                    binding.todayTaskRV.scrollToPosition(it.size - 1)
                }
                return@observe
            }
            adapter.submitList(it.toList())
        }
        viewModel.isSaved.observe(viewLifecycleOwner) {
            if (it) {
                showMessage("작업을 저장했습니다.")
                findNavController().popBackStack()
            }
        }
    }

    private fun setSaveTasks() {
        binding.taskCompleteBT.setOnClickListener {
            viewModel.saveTask(args.cropId)
        }
    }

    private fun addCustomTask() {
        binding.addTaskIL.setEndIconOnClickListener {
            viewModel.addTask(binding.addTaskTIET.text.toString())
            binding.addTaskTIET.editableText.clear()
        }
    }

    private fun getRecommendTasks() {
        binding.aiTaskCreateBT.setOnClickListener {
            viewModel.getRecommendTask(args.cropId)
        }
    }
}
