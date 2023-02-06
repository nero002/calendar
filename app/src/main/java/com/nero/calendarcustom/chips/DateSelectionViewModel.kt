package com.nero.calendarcustom.chips

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nero.calendarcustom.chips.data.DateSuggestion


interface DateSelectionInterface {
    val selectedDate: MutableState<DateSuggestion>

}

class DateSelectionViewModel : ViewModel(), DateSelectionInterface {
    companion object {
        val composeViewModel by lazy {
            object : DateSelectionInterface {
                override val selectedDate: MutableState<DateSuggestion> =
                    mutableStateOf(DateSuggestion.TODAY)
            }
        }
    }

    override val selectedDate: MutableState<DateSuggestion> = mutableStateOf(DateSuggestion.TODAY)

}