package com.teamtrack.teamtrack.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TaskScreen(navController: NavHostController, isTeamLeader: Boolean) {
    val tasks = remember { mutableStateListOf<Task>() } // Task 목록을 저장할 리스트
    val selectedTask = remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(Unit) {
        if (isTeamLeader) {
            // 팀장이면 팀원에게 배정된 모든 Task를 가져옴
            tasks.addAll(fetchTasksForTeamLeader())
        } else {
            // 팀원이면 자신에게 배정된 Task만 가져옴
            tasks.addAll(fetchTasksForTeamMember())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = if (isTeamLeader) "팀원 작업 목록" else "내 작업 목록",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tasks) { task ->
                TaskItem(task, isTeamLeader, navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, isTeamLeader: Boolean, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // 팀장 또는 팀원에 따라 다른 상세 화면으로 이동
                navController.navigate("taskDetailScreen/${task.id}")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "상태: ${task.status}", fontSize = 16.sp)
            if (isTeamLeader) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "담당자: ${task.assignedTo}", fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun AssignTaskButton(taskId: String) {
    // 팀장이 Task를 팀원에게 배정할 수 있는 버튼
    Button(onClick = { /* TODO: Task 배정 로직 추가 */ }) {
        Text("팀원에게 배정", fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTaskStatusButton(taskId: String, currentStatus: String) {
    val statusOptions = listOf("대기", "진행", "완료", "보류")
    var selectedStatus by remember { mutableStateOf(currentStatus) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = "상태 변경", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedStatus,
                onValueChange = { },
                readOnly = true,
                label = { Text(text = "상태 선택") },
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // Make sure to attach the dropdown to the text field
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                statusOptions.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(text = status) },
                        onClick = {
                            selectedStatus = status
                            expanded = false
                            // TODO: Task 상태 업데이트 로직 추가
                        }
                    )
                }
            }
        }
    }
}

// 예시 데이터 클래스
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val assignedTo: String
)

// 예시 API 호출 함수
suspend fun fetchTasksForTeamLeader(): List<Task> {
    // TODO: 서버에서 팀장이 관리하는 모든 Task를 가져오는 API 호출
    return listOf() // 예시 데이터 반환
}

suspend fun fetchTasksForTeamMember(): List<Task> {
    // TODO: 서버에서 팀원이 담당하는 Task를 가져오는 API 호출
    return listOf() // 예시 데이터 반환
}

suspend fun fetchTaskDetail(taskId: String): Task? {
    // TODO: 서버에서 Task 상세 정보를 가져오는 API 호출
    return null // 예시 데이터 반환
}
