package com.nero.calendarcustom.chips

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nero.calendarcustom.chips.data.DateSuggestion


@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    dateSuggestion: List<DateSuggestion> = listOf(*DateSuggestion.values()),
    selectedDate: DateSuggestion? = DateSuggestion.TODAY,
    onSelectedChanged: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        LazyRow {
            items(items = dateSuggestion) { item ->
                Chips(
                    name = item.value,
                    isSelected = selectedDate == item,
                    onSelectionChanged = {
                        onSelectedChanged(
                            it,
                        )
                    },
                ) {
                    Text(text = item.name, color = Color.White, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
