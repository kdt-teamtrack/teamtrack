package com.teamtrack.teamtrack.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProjectScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val projects = getProjectList() // TODO: 서버에서 데이터를 받아오는 로직으로 변경 필요

        items(projects) { project ->
            ProjectCard(project)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            CreateNewProjectCard(navController)
        }
    }
}

@Composable
fun ProjectCard(project: Project) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F1F1))
                .padding(16.dp)
        ) {
            ProjectDetails(project)
        }
    }
}

@Composable
fun ProjectDetails(project: Project) {
    Text(
        text = project.name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "역할: ${project.role}",
        fontSize = 16.sp,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "참여 인원: ${project.memberCount}명",
        fontSize = 16.sp,
        color = Color.Black
    )
}

@Composable
fun CreateNewProjectCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { navController.navigate("createProjectScreen") }, // Navigate to Project Creation Screen
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // Make the card background transparent
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

// Sample data class for Project
data class Project(
    val name: String,
    val role: String,
    val memberCount: Int
)

// Sample function to get project list
fun getProjectList(): List<Project> {
    // TODO: 서버에서 프로젝트 리스트를 받아오는 로직으로 대체
    return listOf(
        Project(name = "Project A", role = "팀장", memberCount = 5),
        Project(name = "Project B", role = "팀원", memberCount = 8),
        Project(name = "Project C", role = "팀장", memberCount = 3)
    )
}
