package com.mjc.lst1995.farmhelper.feature.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.RecommendTaskAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentTaskEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskEditFragment : BaseFragment<FragmentTaskEditBinding>(R.layout.fragment_task_edit) {
    private val viewModel: TaskEditViewModel by viewModels()
    private val args: TaskEditFragmentArgs by navArgs()
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
        viewModel.setTasks(args.task.workContent)
        binding.tasksRV.adapter = adapter
        setObserver()
        setEditTaskSave()
        getRecommendTasks()
        setAddTask()
    }

    private fun setAddTask() {
        binding.textInputLayout.setEndIconOnClickListener {
            lifecycleScope.launch {
                viewModel.addTask(binding.taskTIET.text.toString())
                binding.taskTIET.editableText.clear()
            }
        }
    }

    private fun setObserver() {
        viewModel.recommendTask.observe(viewLifecycleOwner) {
            if (adapter.itemCount != it.size) {
                adapter.submitList(it.toList()) {
                    binding.tasksRV.scrollToPosition(it.size - 1)
                }
                return@observe
            }
            adapter.submitList(it.toList())
        }
        viewModel.editState.observe(viewLifecycleOwner) {
            if (it) {
                showMessage("수정 완료 되었습니다.")
                findNavController().popBackStack()
            }
        }
    }

    private fun setEditTaskSave() {
        binding.taskSaveBT.setOnClickListener {
            viewModel.setEditTaskSave(args.task.workId, args.cropId, adapter.currentList)
        }
    }

    private fun getRecommendTasks() {
        binding.aiTaskCreateBT.setOnClickListener {
            viewModel.getRecommendTask(args.cropId)
        }
    }
}
