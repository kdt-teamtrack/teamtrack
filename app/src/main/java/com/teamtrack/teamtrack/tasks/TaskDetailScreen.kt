package com.teamtrack.teamtrack.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
fun TaskDetailScreen(navController: NavHostController, taskId: String, isTeamLeader: Boolean) {
    val task = remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId) {
        // Fetch Task detail from the server
        task.value = fetchTaskDetail(taskId)
    }

    task.value?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = it.taskName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "상세 설명: ${it.taskName}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "상태: ${it.taskStatus}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            if (isTeamLeader) {
                Text(text = "담당자: ${it.assignedUser}", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                AssignTaskButton(taskId)
            }
            Spacer(modifier = Modifier.height(16.dp))
            UpdateTaskStatusButton(taskId, it.taskStatus)
        }
    }
}

suspend fun fetchTaskDetail(taskId: String): Task? {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    return try {
        client.get("http://192.168.45.207:9292/tasks/$taskId").body()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        client.close()
    }
}
