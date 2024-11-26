package com.mjc.lst1995.farmhelper.core.ui.diff

import androidx.recyclerview.widget.DiffUtil
import com.mjc.lst1995.farmhelper.core.domain.model.Work

object WorkDiffUtil : DiffUtil.ItemCallback<Work>() {
    override fun areItemsTheSame(p0: Work, p1: Work): Boolean {
        return p0.cropName == p1.cropName
    }

    override fun areContentsTheSame(p0: Work, p1: Work): Boolean {
        return p0 == p1
    }
}