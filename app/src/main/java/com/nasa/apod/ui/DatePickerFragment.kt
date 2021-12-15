package com.nasa.apod.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DatePickerFragment() : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        val datePickerDialog =  DatePickerDialog(requireContext(), parentFragment as DatePickerDialog.OnDateSetListener, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        val minDate = Calendar.getInstance()
        minDate.set(MIN_YEAR, MIN_MONTH, MIN_DAY)
        datePickerDialog.datePicker.minDate = minDate.timeInMillis
        return datePickerDialog
    }

    companion object {

        //date must be between Jun 16, 1995 and current date
        private const val MIN_YEAR = 1995
        private const val MIN_MONTH = 5 // (0-11) hence June is 5
        private const val MIN_DAY = 16
    }
}