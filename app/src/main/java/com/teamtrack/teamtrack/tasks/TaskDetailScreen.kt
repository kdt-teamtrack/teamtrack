package com.teamtrack.teamtrack.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TaskDetailScreen(navController: NavHostController, taskId: String, isTeamLeader: Boolean) {
    val task = remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId) {
        // Task 상세 정보 가져오기
        task.value = fetchTaskDetail(taskId)
    }

    task.value?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = it.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.description, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "상태: ${it.status}", fontSize = 18.sp)
            if (isTeamLeader) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "담당자: ${it.assignedTo}", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                AssignTaskButton(taskId)
            }
            Spacer(modifier = Modifier.height(16.dp))
            UpdateTaskStatusButton(taskId, it.status)
        }
    }
}
