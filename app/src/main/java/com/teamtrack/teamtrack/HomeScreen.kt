package com.teamtrack.teamtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.teamtrack.teamtrack.project.Project

@Composable
fun HomeScreen(navController: NavHostController, isTeamLeader: Boolean) {
    // 프로젝트 리스트를 생성합니다. 실제 앱에서는 서버에서 데이터를 받아와야 할 수 있습니다.
    val projects = remember {
        listOf(
            Project("Project A", "팀장", 10),
            Project("Project B", "팀원", 15)
        )
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        item { WelcomeSection() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { DateSection() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ParticipatingProjectsCard(navController,projects) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { MyTasksCard(navController,isTeamLeader) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { CheckInOutCard() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { TodayScheduleCard() }
    }
}

@Composable
fun WelcomeSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Welcome",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Text(
                text = "Bill Gates",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.minjee), // Replace with your profile image resource
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DateSection() {
    Text(text = "2024.01.10", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
}

@Composable
fun ParticipatingProjectsCard(navController: NavHostController, projects: List<Project>) {
    InfoCard(
        title = "내가 참여 중인 프로젝트",
        modifier = Modifier.clickable { navController.navigate("projectSelectionScreen") }  // 알맞게 경로 수정
    ) {
        projects.forEach { project ->
            ProjectRow(name = project.name, role = project.role, iconId = if (project.role == "팀장") R.drawable.ic_leader else R.drawable.ic_group)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun MyTasksCard(navController: NavHostController, isTeamLeader: Boolean) { // Add isTeamLeader as a parameter
    InfoCard(
        title = "내 작업",
        modifier = Modifier.clickable {
            navController.navigate("taskScreen/${isTeamLeader}")
        }
    ) {
        TaskRow(task = "API 통합 작업", status = "진행 중")
        Spacer(modifier = Modifier.height(4.dp))
        TaskRow(task = "UI 디자인 최종화", status = "대기 중")
    }
}

@Composable
fun CheckInOutCard() {
    InfoCard(title = "CHECK IN AND OUT") {
        Text(text = "출근 시간: 08:48 2024.01.10 (월)", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "퇴근 시간: 06:25 2024.01.10 (월)", fontSize = 18.sp, color = Color.Black)
    }
}

@Composable
fun TodayScheduleCard() {
    InfoCard(title = "TODAY SCHEDULE") {
        Text(text = "| 팀국이랑 미팅 10:00 ~ 11:00", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "| 일론이랑 식사 13:00 ~ 14:00", fontSize = 18.sp, color = Color.Black)
    }
}

@Composable
fun ProjectRow(name: String, role: String, iconId: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$name: $role", fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = iconId),
            contentDescription = role,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun TaskRow(task: String, status: String) {
    Text(text = "○ $task - $status", fontSize = 18.sp, color = Color.Black)
}

@Composable
fun InfoCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
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
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, isTeamLeader: Boolean) { // Add isTeamLeader parameter
    val items = listOf(
        BottomNavItem("Home", iconVector = Icons.Filled.Home, route = "homeScreen/$isTeamLeader"),
        BottomNavItem("Schedule", iconVector = Icons.Filled.DateRange, route = "calendarScreen"),
        BottomNavItem("Meeting", iconVector = Icons.Filled.Person, route = "meetingApp"),
        BottomNavItem(
            "QR",
            iconPainter = painterResource(R.drawable.qr_icon),
            route = "attendanceScreen"
        )
    )

    NavigationBar(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = Color(0xFFF1F1F1),
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (item.iconVector != null) {
                        Icon(imageVector = item.iconVector, contentDescription = item.label)
                    } else if (item.iconPainter != null) {
                        Icon(
                            painter = item.iconPainter,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = { Text(item.label, color = Color.Black) },
                selected = false,
                onClick = { navController.navigate(item.route) } // Make sure the route includes isTeamLeader
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val iconVector: ImageVector? = null,
    val iconPainter: Painter? = null,
    val route: String
)
