package com.mjc.lst1995.farmhelper.core.ui.adapter

import android.widget.CalendarView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object BindingAdapters {
    @BindingAdapter("selectedDate")
    @JvmStatic
    fun setSelectedDate(
        view: CalendarView,
        selectedDate: Long,
    ) {
        if (view.date != selectedDate) {
            view.date = selectedDate
        }
    }

    @InverseBindingAdapter(attribute = "selectedDate", event = "selectedDateAttrChanged")
    @JvmStatic
    fun getSelectedDate(view: CalendarView): Long = view.date

    @BindingAdapter("selectedDateAttrChanged")
    @JvmStatic
    fun setListener(
        view: CalendarView,
        listener: InverseBindingListener?,
    ) {
        if (listener != null) {
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                // 날짜가 변경될 때마다, 새로운 값으로 listener를 호출합니다.
                val calendar = java.util.Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                listener.onChange()
            }
        }
    }
}
