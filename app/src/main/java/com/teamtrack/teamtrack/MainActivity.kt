package com.teamtrack.teamtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teamtrack.teamtrack.attendance.AttendanceScreen
import com.teamtrack.teamtrack.attendance.QRScreen
import com.teamtrack.teamtrack.attendance.ResultScreen
import com.teamtrack.teamtrack.calendar.CalendarScreen
import com.teamtrack.teamtrack.login.LoginScreen
import com.teamtrack.teamtrack.login.SignUpScreen
import com.teamtrack.teamtrack.meetingUI.MeetingAppScreen
import com.teamtrack.teamtrack.profile.ProfileScreen
import com.teamtrack.teamtrack.project.CreateProjectScreen
import com.teamtrack.teamtrack.project.ProjectSelectionScreen
import com.teamtrack.teamtrack.tasks.TaskDetailScreen
import com.teamtrack.teamtrack.tasks.TaskScreen
import com.teamtrack.teamtrack.teamLeaderPageUI.TeamLeaderScreen
import com.teamtrack.teamtrack.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val selectedProjectId = mutableStateOf<String?>(null)
    private val isTeamLeader = mutableStateOf(false)

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
                        if (currentRoute != "loginScreen") {
                            BottomNavigationBar(navController, isTeamLeader.value)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "loginScreen",  // 프로젝트 선택 화면으로 시작
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("loginScreen") { LoginScreen(navController) }
                        composable("projectSelectionScreen/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
                            ProjectSelectionScreen(navController = navController, onProjectSelected = {}, userId = userId)
                        }
                        composable("homeScreen/{isTeamLeader}") { backStackEntry ->
                            val isTeamLeader =
                                backStackEntry.arguments?.getString("isTeamLeader")?.toBoolean()
                                    ?: false
                            HomeScreen(navController, isTeamLeader)
                        }
                        composable("teamLeaderScreen") {
                            if (isTeamLeader.value) {
                                TeamLeaderScreen()
                            } else {
                                LoginScreen(navController)
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
                        composable("taskScreen/{isTeamLeader}") { backStackEntry ->
                            val isTeamLeader =
                                backStackEntry.arguments?.getString("isTeamLeader")?.toBoolean()
                                    ?: false
                            TaskScreen(navController, isTeamLeader)
                        }
                        composable("taskDetailScreen/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId").orEmpty()
                            val isTeamLeader = false
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

