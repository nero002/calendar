package com.nero.calendarcustom.calendar.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

private val rangeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

data class DateSelection(
    val startDate: LocalDate? = null, val endDate: LocalDate? = null
) {
    val daysBetween by lazy(LazyThreadSafetyMode.NONE) {
        if (startDate == null || endDate == null) null else {
            ChronoUnit.DAYS.between(startDate, endDate)
        }
    }

    val selectedDatesFormatted: String
        get() {
            if (startDate == null) return ""
            var output = startDate.format(rangeFormatter)
            if (endDate != null) {
                output += " - ${endDate.format(rangeFormatter)}"
            }
            return output
        }

    val hasSelectedDates: Boolean
        get() {
            return startDate != null || endDate != null
        }
}


object ContinuousSelectionHelper {
    fun getSelection(
        clickedDate: LocalDate,
        dateSelection: DateSelection,
    ): DateSelection {
        val (selectionStartDate, selectionEndDate) = dateSelection
        return if (selectionStartDate != null) {
            if (clickedDate < selectionStartDate || selectionEndDate != null) {
                DateSelection(startDate = clickedDate, endDate = null)
            } else if (clickedDate != selectionStartDate) {
                DateSelection(startDate = selectionStartDate, endDate = clickedDate)
            } else {
                DateSelection(startDate = clickedDate, endDate = null)
            }
        } else {
            DateSelection(startDate = clickedDate, endDate = null)
        }
    }
}