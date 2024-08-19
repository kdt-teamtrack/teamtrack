package com.teamtrack.teamtrack.project

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teamtrack.teamtrack.R
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.json
import com.teamtrack.teamtrack.data.Project


@Composable
fun ProjectSelectionScreen(
    navController: NavController,
    onProjectSelected: (Project) -> Unit,
    userId: Int
) {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }
    var projects by remember { mutableStateOf<List<Project>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val response: HttpResponse = client.get("http://192.168.45.207:9292/projects/$userId")
            if (response.status.value == 200) {
                projects = response.body<List<Project>>()
                Log.d("ProjectSelectionScreen", "Loaded projects: ${projects.size}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        // 로딩 중일 때 표시할 UI
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...", fontSize = 24.sp)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = "프로젝트를 선택하세요",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(projects) { project ->
                ProjectCard(
                    project = project,
                    onClick = {
                        onProjectSelected(project)
                        val isTeamLeader = project.leaderId.toIntOrNull() == userId
                        navController.navigate("homeScreen/$userId/$isTeamLeader")
                    },
                    userId = userId // userId 전달
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // 프로젝트 생성 카드 추가
                CreateNewProjectCard(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun ProjectCard(project: Project, onClick: () -> Unit, userId: Int) {
    val roleText = if (project.leaderId.toIntOrNull() == userId) "팀장" else "팀원"
    val roleIcon = if (project.leaderId.toIntOrNull() == userId) {
        R.drawable.ic_leader // 팀장일 경우 표시할 아이콘
    } else {
        R.drawable.ic_group // 팀원일 경우 표시할 아이콘
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = roleIcon), // 역할에 맞는 아이콘 사용
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF33ADFF))
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = project.projectName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Role: $roleText", // 역할 텍스트 표시
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Status: ${project.status}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CreateNewProjectCard(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { navController.navigate("createProjectScreen") },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 40.sp,
                color = Color.Gray
            )
        }
    }
}