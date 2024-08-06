package com.teamtrack.teamtrack.meetingUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.util.Date

data class Meeting(
    val date: LocalDate,
    val agenda: String,
    val attendees: Set<String>,
    val summary: String,
    val conclusion: String
)

@Composable
fun MeetingApp() {
    var meetings by remember { mutableStateOf(listOf<Meeting>()) }
    var showMeetingList by remember { mutableStateOf(false) }

    if (showMeetingList) {
        MeetingList(meetings)
    } else {
        MeetingPage(
            onMeetingCreated = { meeting ->
                meetings = meetings + meeting
                showMeetingList = true
            }
        )
    }
}

@Composable
fun MeetingList(meetings:List<Meeting>){
    LazyColumn {
        //Meeting 리스틑 넣기
        items(meetings.sortedByDescending{it.date}){meeting->
            MeetingComponent(meeting)

        }

    }
}

@Composable
fun MeetingComponent(meeting: Meeting) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "날짜: ${meeting.date}")
            Text(text = "주제: ${meeting.agenda}")
            Text(text = "결론: ${meeting.conclusion}")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewMeetingApp(){
    MeetingApp()
}