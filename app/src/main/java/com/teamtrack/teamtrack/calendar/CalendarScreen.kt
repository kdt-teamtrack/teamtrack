package com.teamtrack.teamtrack.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamtrack.teamtrack.R
import com.teamtrack.teamtrack.data.Meeting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var showPopup by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var meetings by remember { mutableStateOf(emptyList<Meeting>()) }

    // Load meeting data from the URL
    LaunchedEffect(Unit) {
        val meetingData = fetchMeetingData("http://192.168.45.25:9292/meeting")
        meetings = meetingData
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header with month and navigation arrows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                    contentDescription = "Previous Month"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // 중앙 정렬
                modifier = Modifier.weight(1f) // weight를 사용하여 중앙에 배치
            ) {
                Text(
                    text = currentMonth.month.getDisplayName(
                        TextStyle.FULL, Locale.ENGLISH  // Locale을 영어로 설정
                    ),
                    fontSize = 24.sp, // 더 큰 글씨 크기
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Text(
                    text = currentMonth.year.toString(),
                    fontSize = 16.sp, // 더 작은 글씨 크기
                    textAlign = TextAlign.Center,
                    color = Color.Gray // 회색으로 설정
                )
            }
            Button(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                    contentDescription = "Next Month"
                )
            }
        }

        // Calendar display with dots indicating events
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            item {
                CustomCalendar(
                    currentMonth = currentMonth, onDateLongPressed = {
                        selectedDate = it
                        showPopup = true
                    }, meetings = meetings
                )
            }

            // Events list for the selected day
            selectedDate?.let { date ->
                items(meetings.filter { it.meetingDate == date.toString() }) { meeting ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${meeting.meetingDate} - ${meeting.agenda}",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        if (showPopup && selectedDate != null) {
            AlertDialog(onDismissRequest = { showPopup = false },
                title = { Text("Event Details", fontSize = 14.sp) },
                text = {
                    val selectedMeeting = meetings.firstOrNull {
                        LocalDate.parse(
                            it.meetingDate, DateTimeFormatter.ISO_DATE_TIME
                        ) == selectedDate
                    }
                    Text(
                        text = "Selected Date: ${selectedDate.toString()}\n" + "Meeting Content: ${selectedMeeting?.content ?: "No meeting"}",
                        fontSize = 12.sp
                    )
                },
                confirmButton = {
                    Button(onClick = { showPopup = false }) {
                        Text("Close", fontSize = 12.sp)
                    }
                })
        }
    }
}

suspend fun fetchMeetingData(url: String): List<Meeting> {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()

                Log.d("fetchMeetingData", "Data fetched: $jsonString") // 데이터 로그 출력

                Json.decodeFromString<List<Meeting>>(jsonString)
            } else {
                connection.disconnect()
                Log.e(
                    "fetchMeetingData", "Failed to fetch data: ${connection.responseCode}"
                )
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("fetchMeetingData", "Exception: ${e.message}")
            emptyList()
        }
    }
}
