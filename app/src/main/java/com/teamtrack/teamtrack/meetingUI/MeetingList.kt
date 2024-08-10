package com.teamtrack.teamtrack.meetingUI

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamtrack.teamtrack.data.Meeting

@Composable
fun MeetingAppScreen() {
    //TODO: 서버에서 미팅리스트 가져오기.
    var meetings by remember { mutableStateOf(listOf<Meeting>()) }
    if (meetings.isNotEmpty()) {
        MeetingList(meetings)
    } else {
        NewMeetingPage(
            onMeetingCreated = { meeting ->
                meetings = meetings + meeting
            }
        )
    }
}

@Composable
fun MeetingList(meetings: List<Meeting>) {
    LazyColumn {
        //Meeting 리스틑 넣기
        items(meetings.sortedByDescending { it.meetingDate }) { meeting ->
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
            Text(text = "날짜: ${meeting.meetingDate}")
            Text(text = "주제: ${meeting.agenda}")
            Text(text = "요약: ${meeting.summary}")
            Text(text = "결론: ${meeting.content}")
            Text(text = "참석자: ${meeting.attendees}")
            Text(text = "작성일: ${meeting.createdAt}")
            Text(text = "수정일: ${meeting.updatedAt}")
            // 내용이 보이는 경우만 표시
            if (meeting.isContentVisible) {
                Text(text = "내용: ${meeting.content}")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMeetingApp() {
//    MeetingApp()
//}