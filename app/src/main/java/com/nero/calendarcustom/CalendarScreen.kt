package com.nero.calendarcustom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nero.calendarcustom.calendar.core.*
import com.nero.calendarcustom.calendar.customModifier.backgroundHighlight
import com.nero.calendarcustom.calendar.data.ContinuousSelectionHelper
import com.nero.calendarcustom.calendar.data.DateSelection
import com.nero.calendarcustom.calendar.model.HorizontalCalendar
import com.nero.calendarcustom.calendar.model.rememberCalendarState
import com.nero.calendarcustom.calendar.utils.displayText
import com.nero.calendarcustom.calendar.utils.rememberFirstMostVisibleMonth
import com.nero.calendarcustom.chips.ChipGroup
import com.nero.calendarcustom.chips.data.DateSuggestion
import com.nero.calendarcustom.composables.DatesUserInput
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth


private val primaryColor = Color.Blue.copy(alpha = 0.9f)
private val selectionColor = primaryColor
private val continuousSelectionColor = Color.LightGray.copy(alpha = 0.3f)

@Composable
fun CalendarMainScreen(adjacentMonths: Long = 500) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }

    var selection by remember { mutableStateOf(DateSelection()) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)
        TopHeaderBar()
        Divider(color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

        ChipGroup(dateSuggestion = listOf(*DateSuggestion.values()))


        DatesUserInput(
            datesSelected = if (!selection.hasSelectedDates) "Select Date"
            else selection.selectedDatesFormatted
        )


        Spacer(modifier = Modifier.padding(10.dp))
        SimpleCalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        HorizontalCalendar(
            modifier = Modifier.testTag("Calendar"),
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    selection = selection,
                ) { onDateSelectionClicked ->
                    if (onDateSelectionClicked.position == DayPosition.MonthDate) {
                        selection = ContinuousSelectionHelper.getSelection(
                            clickedDate = onDateSelectionClicked.date,
                            dateSelection = selection,
                        )
                    }
                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )
    }
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    selection: DateSelection,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor: Color
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                onClick = { onClick(day) },
            )
            .backgroundHighlight(
                day = day,
                selection = selection,
                selectionColor = selectionColor,
                continuousSelectionColor = continuousSelectionColor,
            ) {
                textColor = it
            },
        contentAlignment = Alignment.Center,
    ) {
        textColor = when (day.position) {
            DayPosition.InDate, DayPosition.OutDate -> Color.Transparent
            else -> {
                Color.Black
            }
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,

            )
    }
}

@Composable
fun TopHeaderBar() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
    ) {
        val (tvText, icon) = createRefs()
        Text(
            text = "StartDate",
            modifier = Modifier.constrainAs(tvText) {
                start.linkTo(parent.start)
            },
            fontSize = 16.sp
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Close Icon",
            modifier = Modifier.constrainAs(icon) {
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview
@Composable
private fun Example1Preview() {
    CalendarMainScreen()
}
