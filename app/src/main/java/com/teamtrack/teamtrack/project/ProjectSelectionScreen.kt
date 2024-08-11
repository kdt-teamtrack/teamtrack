package com.teamtrack.teamtrack.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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

@Composable
fun ProjectSelectionScreen(
    navController: NavController,
    onProjectSelected: (Project) -> Unit
) {
    val projects = remember {
        mutableStateListOf(
            Project("A 프로젝트", "팀장", 10),
            Project("B 프로젝트", "팀원", 15)
        )
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "프로젝트를 선택하세요",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        projects.forEach { project ->
            ProjectCard(
                project = project,
                onClick = {
                    onProjectSelected(project)
                    val isTeamLeader = project.role == "팀장"
                    navController.navigate("homeScreen/$isTeamLeader")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 프로젝트 생성 카드 추가
        CreateNewProjectCard(navController)
    }
}

@Composable
fun ProjectCard(project: Project, onClick: () -> Unit) {
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
                painter = painterResource(id = if (project.role == "팀장") R.drawable.ic_leader else R.drawable.ic_group),
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
                    text = project.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Role: ${project.role}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Members: ${project.memberCount}",
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
