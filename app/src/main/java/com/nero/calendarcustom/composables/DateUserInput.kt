package com.nero.calendarcustom.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nero.calendarcustom.R


val captionTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp
)

@Composable
fun DatesUserInput(
    datesSelected: String,
) {
    UserInput(
        caption = if (datesSelected.isEmpty()) "Select Date" else null,
        text = datesSelected,
        vectorImageId = R.drawable.baseline_calendar_today_24
    )
}

@Composable
fun UserInput(
    text: String,
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    tint: Color = LocalContentColor.current
) {
    BaseUserInput(
        caption = caption,
        vectorImageId = vectorImageId,
        tintIcon = { text.isNotEmpty() },
        tint = tint
    ) {
        Text(text = text, style = MaterialTheme.typography.body1.copy(color = tint))
    }
}

@Composable
private fun BaseUserInput(
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    showCaption: () -> Boolean = { true },
    tintIcon: () -> Boolean,
    tint: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Row(
            Modifier
                .padding(all = 9.dp)
                .border(
                    border = ButtonDefaults.outlinedBorder,
                    shape = RectangleShape
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (caption != null && showCaption()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = caption,
                    style = (captionTextStyle).copy(color = tint)
                )
                Spacer(Modifier.width(8.dp))
            }

            Row(
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                content()
            }
            if (vectorImageId != null) {
                Icon(
                    modifier = Modifier
                        .size(24.dp, 24.dp)
                        .padding(end = 10.dp),
                    painter = painterResource(id = vectorImageId),
                    tint = if (tintIcon()) tint else Color(0x80FFFFFF),
                    contentDescription = null
                )
            }
        }
    }
}
