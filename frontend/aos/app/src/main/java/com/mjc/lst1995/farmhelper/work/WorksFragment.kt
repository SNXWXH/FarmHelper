package com.mjc.lst1995.farmhelper.work

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mjc.lst1995.farmhelper.R
import com.mjc.lst1995.farmhelper.core.domain.model.Work
import com.mjc.lst1995.farmhelper.core.ui.BaseFragment
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkGridAdapter
import com.mjc.lst1995.farmhelper.core.ui.adapter.WorkLinearAdapter
import com.mjc.lst1995.farmhelper.databinding.FragmentWorksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class WorksFragment : BaseFragment<FragmentWorksBinding>(R.layout.fragment_works) {

    private val works = listOf(
        Work("감자", "감자", "2024-12-01"),
        Work("고구마", "감자", "2024-12-01."),
        Work("호박", "감자", "2024-12-01."),
    )

    private lateinit var gridAdapter: WorkGridAdapter
    private lateinit var linearAdapter: WorkLinearAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialToolbar2.title = tempClientNameFormat.format(tempClientName)

        val listener = { work: Work ->
            val action = WorksFragmentDirections.actionWorksFragmentToWorkDetailFragment(
                Json.encodeToString(work)
            )
            findNavController().navigate(action)
        }

        gridAdapter = WorkGridAdapter(listener)
        linearAdapter = WorkLinearAdapter(listener)
        binding.worksGridRV.adapter = gridAdapter
        binding.worksGridRV.layoutManager = GridLayoutManager(context, 2)
        binding.worksLinearRV.adapter = linearAdapter
        gridAdapter.submitList(works)
        linearAdapter.submitList(works)

        binding.linearChip.setOnClickListener {
            binding.worksGridRV.visibility = View.GONE
            binding.worksLinearRV.visibility = View.VISIBLE
        }
        binding.gridChip.setOnClickListener {
            binding.worksGridRV.visibility = View.VISIBLE
            binding.worksLinearRV.visibility = View.GONE
        }
    }

    companion object {
        private const val tempClientNameFormat = "%s님의 작업일지"
        private const val tempClientName = "손흥민"
    }
}
