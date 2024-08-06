package com.teamtrack.teamtrack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingPage() {
    var date by remember { mutableStateOf(LocalDate.now()) }
    var agenda by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var conclusion by remember { mutableStateOf("") }

    val attendees = listOf("김평기","장기훈","하승수","엄대희","최재흥")
    var selectedAttendees by remember { mutableStateOf<Set<String>>(emptySet()) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        OutlinedTextField(
            value = date.toString(),
            onValueChange = {},
            label = { Text("날짜") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = agenda,
            onValueChange = { agenda = it },
            label = { Text("회의 주제") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Box {
            OutlinedTextField(
                value = selectedAttendees.joinToString(", "),
                onValueChange = {},
                readOnly = true,
                label = { Text("참석자") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    .onFocusChanged { expanded = it.isFocused }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                attendees.forEach { attendee ->
                    DropdownMenuItem(
                        text = {
                            Row {
                                Checkbox(
                                    checked = selectedAttendees.contains(attendee),
                                    onCheckedChange = null
                                )
                                Text(attendee)
                            }
                        },
                        onClick = {
                            selectedAttendees = if (selectedAttendees.contains(attendee)) {
                                selectedAttendees - attendee
                            } else {
                                selectedAttendees + attendee
                            }
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("회의 요약") },
            modifier = Modifier.fillMaxWidth().height(200.dp).padding(bottom = 8.dp),
            maxLines = 10
        )

        OutlinedTextField(
            value = conclusion,
            onValueChange = { conclusion = it },
            label = { Text("회의 결론") },
            modifier = Modifier.fillMaxWidth().height(150.dp).padding(bottom = 8.dp),
            maxLines = 7
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMeetingPage(){
    MeetingPage()
}