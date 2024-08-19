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
import com.teamtrack.teamtrack.data.Task
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

@Composable
fun TaskScreen(navController: NavHostController, projectId: Int, isTeamLeader: Boolean) {
    val tasks = remember { mutableStateListOf<Task>() }
    val selectedTask = remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(Unit) {
        if (isTeamLeader) {
            tasks.addAll(fetchTasksForTeamLeader(projectId))
        } else {
            tasks.addAll(fetchTasksForTeamMember(projectId))
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
            Text(text = task.taskName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "상태: ${task.taskStatus}", fontSize = 16.sp)
            if (isTeamLeader) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "담당자: ${task.assignedUser}", fontSize = 16.sp)
                Text(text = "리더가 배정했나요?: ${if (task.isAssignedByLeader) "예" else "아니요"}", fontSize = 16.sp)
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

suspend fun fetchTasksForTeamLeader(projectId: Int): List<Task> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    return try {
        client.get("http://192.168.45.207:9292/projects/$projectId/tasks").body()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    } finally {
        client.close()
    }
}

suspend fun fetchTasksForTeamMember(projectId: Int): List<Task> {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    return try {
        client.get("http://192.168.45.207:9292/projects/$projectId/tasks").body()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    } finally {
        client.close()
    }
}
