package com.teamtrack.teamtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teamtrack.teamtrack.attendance.AttendanceScreen
import com.teamtrack.teamtrack.attendance.QRScreen
import com.teamtrack.teamtrack.attendance.ResultScreen
import com.teamtrack.teamtrack.calendar.CalendarScreen
import com.teamtrack.teamtrack.data.User
import com.teamtrack.teamtrack.login.LoginScreen
import com.teamtrack.teamtrack.login.SignUpScreen
import com.teamtrack.teamtrack.meetingUI.MeetingAppScreen
import com.teamtrack.teamtrack.profile.ProfileScreen
import com.teamtrack.teamtrack.project.CreateProjectScreen
import com.teamtrack.teamtrack.project.ProjectSelectionScreen
import com.teamtrack.teamtrack.tasks.TaskDetailScreen
import com.teamtrack.teamtrack.tasks.TaskScreen
import com.teamtrack.teamtrack.ui.theme.MyApplicationTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class MainActivity : ComponentActivity() {

    private val selectedProjectId = mutableStateOf<String?>(null)
    private val isTeamLeader = mutableStateOf(false)
    private var userId by mutableStateOf(0)  // userId를 기억하기 위한 상태 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()

                // 현재 라우트에 따라 바텀 네비게이션을 조건부로 표시
                val currentRoute = currentBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // userId 가 0이 아닐 때만 BottomNavigationBar를 표시
                        if (currentRoute != "loginScreen" && userId != 0) {
                            BottomNavigationBar(navController, userId = userId, isTeamLeader = isTeamLeader.value)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "loginScreen",  // 프로젝트 선택 화면으로 시작
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("loginScreen") {
                            LoginScreen(navController) { fetchedUserId ->
                                userId = fetchedUserId  // 로그인 후 userId 설정
                                navController.navigate("projectSelectionScreen/$userId")
                            }
                        }
                        composable("projectSelectionScreen/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
                            ProjectSelectionScreen(navController = navController, onProjectSelected = {}, userId = userId)
                        }
                        composable("homeScreen/{userId}/{isTeamLeader}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
                            val isTeamLeader = backStackEntry.arguments?.getString("isTeamLeader")?.toBoolean() ?: false

                            // 상태 변수로 user 객체 관리
                            var user by remember { mutableStateOf<User?>(null) }
                            var isLoading by remember { mutableStateOf(true) }

                            // 코루틴을 사용하여 User 객체를 가져오기
                            LaunchedEffect(userId) {
                                user = fetchUserById(userId) // userId를 사용하여 User 객체를 가져옴
                                isLoading = false
                            }

                            if (isLoading) {
                                // 로딩 중일 때 표시할 UI
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(text = "Loading...")
                                }
                            } else {
                                user?.let {
                                    HomeScreen(navController, it, userId, isTeamLeader)
                                } ?: run {
                                    // user가 null일 때 오류 처리 UI
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(text = "User not found")
                                    }
                                }
                            }
                        }
                        composable("attendanceScreen") { AttendanceScreen(navController) }
                        composable("qrScreen") { QRScreen(navController) }
                        composable("resultScreen/{result}") {
                            ResultScreen(navController)
                        }
                        composable("todoListScreen") {
                            TodoListScreen()
                        }
                        composable("meetingApp") {
                            MeetingAppScreen()
                        }
                        composable("calendarScreen") { CalendarScreen() }
                        composable("createProjectScreen") {
                                CreateProjectScreen(navController)
                        }
                        composable("taskScreen/{projectId}/{isTeamLeader}") { backStackEntry ->
                            val projectId = backStackEntry.arguments?.getString("projectId")?.toIntOrNull() ?: 0
                            val isTeamLeader = backStackEntry.arguments?.getString("isTeamLeader")?.toBoolean() ?: false
                            TaskScreen(navController, projectId, isTeamLeader)
                        }

                        composable("taskDetailScreen/{taskId}/{isTeamLeader}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId").orEmpty()
                            val isTeamLeader = backStackEntry.arguments?.getString("isTeamLeader")?.toBoolean() ?: false
                            TaskDetailScreen(navController, taskId, isTeamLeader)
                        }
                        composable("signUpScreen") {
                            SignUpScreen()
                        }
                        composable("profileScreen"){
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}

suspend fun fetchUserById(userId: Int): User? {
    // 서버로부터 userId에 해당하는 User 객체를 가져오는 로직
    // 예시로 간단히 HttpClient를 사용하여 가져오는 방식으로 구현할 수 있습니다.
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    return try {
        client.get("http://192.168.45.25:9292/users/$userId").body<User>()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        client.close()
    }
}
