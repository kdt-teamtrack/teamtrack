package com.teamtrack.teamtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teamtrack.teamtrack.attendance.AttendanceScreen
import com.teamtrack.teamtrack.attendance.QRScreen
import com.teamtrack.teamtrack.attendance.ResultScreen
import com.teamtrack.teamtrack.calendar.CalendarScreen
import com.teamtrack.teamtrack.meetingUI.MeetingAppScreen
import com.teamtrack.teamtrack.teamLeaderPageUI.TeamLeaderScreen
import com.teamtrack.teamtrack.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
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
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "loginScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("loginScreen") { LoginScreen(navController) }
                        composable("homeScreen") { HomeScreen(navController) }
                        composable("teamLeaderScreen") { TeamLeaderScreen() }
                        composable("attendanceScreen") { AttendanceScreen(navController) }
                        composable("qrScreen") { QRScreen(navController) }
                        composable("resultScreen/{result}") {
                            ResultScreen(navController)
                        }
                        composable("todoListScreen") { TodoListScreen() }
                        composable("meetingApp") { MeetingAppScreen() }
                        composable("calendarScreen") { CalendarScreen() }
                    }
                }
            }
        }
    }
}