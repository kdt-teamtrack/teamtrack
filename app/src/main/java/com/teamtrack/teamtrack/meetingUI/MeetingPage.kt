package com.teamtrack.teamtrack.meetingUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.focus.onFocusChanged
import java.time.LocalDate



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingPage(onMeetingCreated:(Meeting) -> Unit) {
    // 상태 변수들을 정의합니다. remember를 사용하여 재구성 시 상태를 유지합니다.
    var date by remember { mutableStateOf(LocalDate.now()) } // 현재 날짜로 초기화
    var agenda by remember { mutableStateOf("") } // 빈 문자열로 초기화
    var summary by remember { mutableStateOf("") } // 빈 문자열로 초기화
    var conclusion by remember { mutableStateOf("") } // 빈 문자열로 초기화

    // 참석 가능한 인원 목록
    val attendees = listOf("김평기","장기훈","하승수","엄대희","최재흥")
    // 선택된 참석자들을 저장할 Set. 초기에는 비어있음
    var selectedAttendees by remember { mutableStateOf<Set<String>>(emptySet()) }
    // 드롭다운 메뉴의 확장 상태를 제어
    var expanded by remember { mutableStateOf(false) }
    var scrollState = rememberScrollState()

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .verticalScroll(scrollState),

        ) {
        // 날짜 입력 필드 - 읽기 전용
        OutlinedTextField(
            value = date.toString(),
            onValueChange = {}, // 읽기 전용이므로 변경 불가
            label = { Text("날짜") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // 회의 주제 입력 필드
        OutlinedTextField(
            value = agenda,
            onValueChange = { agenda = it }, // 입력값으로 agenda 업데이트
            label = { Text("회의 주제") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // 참석자 선택을 위한 드롭다운 메뉴
        Box {
            // 선택된 참석자들을 표시하는 필드
            OutlinedTextField(
                value = selectedAttendees.joinToString(", "), // 선택된 참석자들을 쉼표로 구분하여 표시
                onValueChange = {}, // 읽기 전용
                readOnly = true,
                label = { Text("참석자") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .onFocusChanged { expanded = it.isFocused } // 포커스를 받으면 드롭다운 메뉴 확장
            )
            // 드롭다운 메뉴
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, // 메뉴 외부 클릭 시 닫기
                modifier = Modifier.fillMaxWidth()
            ) {
                // 각 참석 가능한 인원에 대해 항목 생성
                attendees.forEach { attendee ->
                    DropdownMenuItem(
                        text = {
                            Row {
                                // 체크박스로 선택 상태 표시
                                Checkbox(
                                    checked = selectedAttendees.contains(attendee),
                                    onCheckedChange = null // onClick에서 처리하므로 여기서는 null
                                )
                                Text(attendee)
                            }
                        },
                        onClick = {
                            // 클릭 시 선택/선택 해제 토글
                            selectedAttendees = if (selectedAttendees.contains(attendee)) {
                                selectedAttendees - attendee // 이미 선택된 경우 제거
                            } else {
                                selectedAttendees + attendee // 선택되지 않은 경우 추가
                            }
                        }
                    )
                }
            }
        }

        // 회의 요약 입력 필드
        OutlinedTextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("회의 요약") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 8.dp),

        )

        // 회의 결론 입력 필드
        OutlinedTextField(
            value = conclusion,
            onValueChange = { conclusion = it },
            label = { Text("회의 결론") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 8.dp),

        )
        Button(
            onClick = {
                val meeting = Meeting(date, agenda, selectedAttendees, summary, conclusion)
                onMeetingCreated(meeting)
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("회의 저장")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMeetingPage(){
    MeetingPage {

    }
}