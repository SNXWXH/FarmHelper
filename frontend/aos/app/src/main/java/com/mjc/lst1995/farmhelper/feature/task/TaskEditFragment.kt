package com.mjc.lst1995.farmhelper.feature.task

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.RecommendTaskAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentTaskEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEditFragment : BaseFragment<FragmentTaskEditBinding>(R.layout.fragment_task_edit) {
    private val viewModel: TaskEditViewModel by viewModels()
    private val args: TaskEditFragmentArgs by navArgs()
    private val listener: (Int) -> Unit = {
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
    }

    private fun setObserver() {
        viewModel.recommendTask.observe(viewLifecycleOwner) {
            adapter.submitList(it) {
                binding.tasksRV.scrollToPosition(it.size - 1)
            }
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
            viewModel.setEditTaskSave(args.task.workId, args.cropId)
        }
    }

    private fun getRecommendTasks() {
        binding.aiTaskCreateBT.setOnClickListener {
            viewModel.getRecommendTask(args.cropId)
        }
    }
}
