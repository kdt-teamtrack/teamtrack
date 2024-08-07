package com.teamtrack.teamtrack.calender

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamtrack.teamtrack.data.Meeting
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CustomCalendar(
    currentMonth: YearMonth,
    onDateLongPressed: (LocalDate) -> Unit,
    meetings: List<Meeting>
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    val meetingDates = meetings.groupBy {
        LocalDateTime.parse(it.meetingDate, DateTimeFormatter.ISO_DATE_TIME).toLocalDate()
    }

    Log.d("CustomCalendar", "Meetings: $meetings") // 미팅 데이터 로그 출력
    Log.d("CustomCalendar", "Meeting Dates: $meetingDates") // 그룹화된 미팅 데이터 로그 출력

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
                                meetingDates[currentDay]?.forEach { meeting ->
                                    Log.d("CustomCalendar", "Meeting on $currentDay: $meeting") // 각 날짜의 미팅 로그 출력
                                    Box(
                                        modifier = Modifier
                                            .background(highlightColor)
                                            .padding(1.dp)
                                    ) {
                                        Text(
                                            text = LocalDateTime.parse(
                                                meeting.meetingDate,
                                                DateTimeFormatter.ISO_DATE_TIME
                                            ).toLocalTime().toString(),
                                            fontSize = 6.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                    }
                                }
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
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))
        }
    }
}
