package com.mjc.lst1995.farmhelper.feature.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.databinding.FragmentEditSelectDalogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class EditSelectDialogFragment(
    private val task: Task,
    private val editSelectListener: (Task) -> Unit,
    private val deleteSelectListener: (Task) -> Job,
) : DialogFragment() {
    var _binding: FragmentEditSelectDalogBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditSelectDalogBinding.inflate(inflater, container, false)

        binding!!.editTV.setOnClickListener {
            editSelectListener
        }

        binding!!.deleteTV.setOnClickListener {
            deleteSelectListener(task)
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
