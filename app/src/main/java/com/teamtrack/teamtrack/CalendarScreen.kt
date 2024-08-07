package com.teamtrack.teamtrack

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var showPopup by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 달 이동 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Text("이전 달", fontSize = 12.sp)
            }
            Text(
                text = "${
                    currentMonth.month.getDisplayName(
                        TextStyle.FULL, Locale.getDefault()
                    )
                } ${currentMonth.year}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Button(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Text("다음 달", fontSize = 12.sp)
            }
        }

        // 달력 및 공지사항 부분
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            item {
                CustomCalendar(currentMonth = currentMonth, onDateLongPressed = {
                    selectedDate = it
                    showPopup = true
                })
            }

            // 공지사항 부분
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "공지사항 1", modifier = Modifier.padding(16.dp), fontSize = 12.sp
                    )
                }
            }
        }

        if (showPopup && selectedDate != null) {
            AlertDialog(onDismissRequest = { showPopup = false },
                title = { Text("달력 팝업", fontSize = 14.sp) },
                text = { Text("선택한 날짜: ${selectedDate.toString()}", fontSize = 12.sp) },
                confirmButton = {
                    Button(onClick = { showPopup = false }) {
                        Text("닫기", fontSize = 12.sp)
                    }
                })
        }
    }
}

@Composable
fun CustomCalendar(currentMonth: YearMonth, onDateLongPressed: (LocalDate) -> Unit) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    val sampleMeetingTimes = mapOf(
        1 to "10:00", 5 to "14:00", 12 to "11:00", 20 to "15:00"
    )
    val sampleAttendance = mapOf(
        1 to "5/5", 5 to "4/5", 12 to "5/5", 20 to "3/5"
    )

    val koreanHolidays = setOf(
        LocalDate.of(2024, 1, 1),
        LocalDate.of(2024, 2, 9),
        LocalDate.of(2024, 2, 10),
        LocalDate.of(2024, 2, 11),
        LocalDate.of(2024, 2, 12),
        LocalDate.of(2024, 3, 1),
        LocalDate.of(2024, 5, 5),
        LocalDate.of(2024, 5, 6),
        LocalDate.of(2024, 5, 15),
        LocalDate.of(2024, 6, 6),
        LocalDate.of(2024, 8, 15),
        LocalDate.of(2024, 9, 16),
        LocalDate.of(2024, 9, 17),
        LocalDate.of(2024, 9, 18),
        LocalDate.of(2024, 10, 3),
        LocalDate.of(2024, 10, 9),
        LocalDate.of(2024, 12, 25)
    )

    val pastelRed = Color(0xFFFF8080) // Darker pastel red
    val pastelCyan = Color(0xFFC1FFFF)
    val pastelWhite = Color(0xFFF0F0F0)
    val highlightColor = Color(0xFFFFB74D)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (day in daysOfWeek) {
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = when (day) {
                        "일" -> pastelRed
                        "토" -> pastelCyan
                        else -> Color.Gray
                    }
                )
            }
        }

        var currentDay = firstDayOfMonth
        while (currentDay <= lastDayOfMonth) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .background(Color.Black),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0..6) {
                    val isSunday = currentDay.dayOfWeek.value % 7 == 0
                    val isSaturday = currentDay.dayOfWeek.value % 7 == 6
                    val isHoliday = currentDay in koreanHolidays
                    val textColor = when {
                        isSunday || isHoliday -> pastelRed
                        isSaturday -> pastelCyan
                        else -> pastelWhite
                    }
                    if ((currentDay.dayOfWeek.value % 7) == i && currentDay <= lastDayOfMonth) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .padding(2.dp)
                                .clickable { onDateLongPressed(currentDay) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    text = currentDay.dayOfMonth.toString(),
                                    fontSize = 10.sp,
                                    color = textColor
                                )
                                if (sampleMeetingTimes.containsKey(currentDay.dayOfMonth)) {
                                    Box(
                                        modifier = Modifier
                                            .background(highlightColor)
                                            .padding(horizontal = 2.dp) // Adjust padding for height
                                    ) {
                                        Text(
                                            text = sampleMeetingTimes[currentDay.dayOfMonth] ?: "",
                                            fontSize = 6.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            modifier = Modifier.padding(horizontal = 4.dp) // Adjust padding for width
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "",
                                        fontSize = 6.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = sampleAttendance[currentDay.dayOfMonth] ?: "",
                                    fontSize = 6.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                        }
                        currentDay = currentDay.plusDays(1)
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .padding(2.dp)
                                .background(Color.Black)
                        ) {}
                    }
                }
            }
            Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
        }
    }
}
