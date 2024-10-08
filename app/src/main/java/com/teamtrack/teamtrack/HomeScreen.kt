package com.teamtrack.teamtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import io.ktor.serialization.kotlinx.json.json
import com.teamtrack.teamtrack.data.Project
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.teamtrack.teamtrack.data.User

@Composable
fun HomeScreen(navController: NavHostController, user: User?, userId: Int, isTeamLeader: Boolean) {
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
            val response: HttpResponse = client.get("http://192.168.45.207:9292/projects")
            if (response.status.value == 200) {
                projects = response.body()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...", fontSize = 24.sp)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                if (user != null) {
                    WelcomeSection(navController, user)
                }
            }  // 사용자 정보 전달
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DateSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { ParticipatingProjectsCard(navController, projects, userId) } // userId 추가
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { MyTasksCard(navController, projects, isTeamLeader) }  // Updated to use projects
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CheckInOutCard() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { TodayScheduleCard() }
        }
    }
}

@Composable
fun WelcomeSection(navController: NavHostController, user: User) {  // user: Int -> user: User
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
                text = user.userName,  // 사용자 이름 표시
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.minjee), // 사용자 프로필 이미지로 대체 가능
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { navController.navigate("profileScreen") },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DateSection() {
    val currentDate = remember {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        dateFormat.format(Date())
    }

    Text(text = currentDate, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
}

@Composable
fun ParticipatingProjectsCard(
    navController: NavHostController,
    projects: List<Project>,
    userId: Int
) {
    InfoCard(
        title = "내가 참여 중인 프로젝트",
        modifier = Modifier.clickable { navController.navigate("projectSelectionScreen/$userId") }
    ) {
        projects.forEach { project ->
            val isTeamLeader = project.leaderId.toIntOrNull() == userId
            val roleText = if (isTeamLeader) "팀장" else "팀원"
            val iconId = if (isTeamLeader) R.drawable.ic_leader else R.drawable.ic_group

            ProjectRow(
                name = project.projectName,
                role = roleText,
                iconId = iconId
            )
            Spacer(modifier = Modifier.height(8.dp))  // 카드 간 간격 조정
        }
    }
}

@Composable
fun ProjectRow(name: String, role: String, iconId: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)  // 카드 내부 여백 추가
    ) {
        // 아이콘 크기를 키우고, 둥글게 모양을 잡음
        Image(
            painter = painterResource(id = iconId),
            contentDescription = role,
            modifier = Modifier
                .size(48.dp)  // 아이콘 크기 증가
                .clip(CircleShape)
                .background(Color(0xFF33ADFF))  // 아이콘 배경색
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))  // 아이콘과 텍스트 간격

        Column {
            Text(
                text = name,
                fontSize = 20.sp,  // 프로젝트 이름 크기 증가
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = role,
                fontSize = 18.sp,  // 역할 텍스트 크기 증가
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MyTasksCard(navController: NavHostController, projects: List<Project>, isTeamLeader: Boolean) {
    InfoCard(
        title = "내 작업",
        modifier = Modifier.clickable {
            // Navigate to task screen using the first project's ID as an example.
            // Modify this logic as needed for your use case.
            val firstProjectId = projects.firstOrNull()?.id ?: return@clickable
            navController.navigate("taskScreen/$firstProjectId/$isTeamLeader")
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
fun BottomNavigationBar(
    navController: NavHostController,
    userId: Int,
    isTeamLeader: Boolean
) { // Add userId parameter
    val items = listOf(
        BottomNavItem(
            "Home",
            iconVector = Icons.Filled.Home,
            route = "homeScreen/$userId/$isTeamLeader"
        ),
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
