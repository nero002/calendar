package com.nero.calendarcustom.calendar.model

import java.io.Serializable

internal class VisibleItemState(
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemScrollOffset: Int = 0,
) : Serializable
