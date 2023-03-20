package com.blueberry.kmp_apod.dates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blueberry.kmp_apod.Constants

@Composable
internal fun DatesWidget(
    modifier: Modifier = Modifier,
    dates: List<AstronomyDate>,
    onDateSelected: (AstronomyDate) -> Unit
) {
    val columnWidth by remember {
        derivedStateOf {
            80.dp
        }
    }
    Box(modifier = modifier.background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().wrapContentSize()) {
            dates.forEach { dateInfo ->
                Box(Modifier.width(columnWidth).background(
                    if (dateInfo.isSelected) Constants.getTealColor() else Color.White
                ).padding(8.dp).clickable {
                    onDateSelected(dateInfo)
                }) {
                    DateColumn(dateInfo = dateInfo)
                }
            }
        }
    }
}

@Composable
internal fun DateColumn(modifier: Modifier = Modifier, dateInfo: AstronomyDate) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val color = if(dateInfo.isSelected) Color.White else Color.Black

        Text(text = dateInfo.month, textAlign = TextAlign.Center, color = color, fontSize = 12.sp)
        Text(text = dateInfo.date, textAlign = TextAlign.Center, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = dateInfo.day, textAlign = TextAlign.Center, color = color, fontSize = 14.sp)
    }
}