package com.teamtrack.teamtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teamtrack.teamtrack.attendance.AttendanceScreen
import com.teamtrack.teamtrack.attendance.QRScreen
import com.teamtrack.teamtrack.attendance.ResultScreen
import com.teamtrack.teamtrack.calendar.CalendarScreen
import com.teamtrack.teamtrack.meetingUI.MeetingApp
import com.teamtrack.teamtrack.teamLeaderPageUI.TeamLeaderScreen
import com.teamtrack.teamtrack.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "loginScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("loginScreen") { LoginScreen(navController) }
                        composable("homeScreen") { HomeScreen(navController) }
                        composable("main") { MainScreen(navController) }
                        composable("teamLeaderScreen") { TeamLeaderScreen() }
                        composable("attendanceScreen") { AttendanceScreen(navController) }
                        composable("qrScreen") { QRScreen(navController) }
                        composable("resultScreen/{result}") {
                            ResultScreen(navController)
                        }
                        composable("todoListScreen") { TodoListScreen() }
                        composable("meetingApp") { MeetingApp() }
                        composable("calendarScreen") { CalendarScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: androidx.navigation.NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("teamleaderScreen") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("팀장의 Main화면")
        }
        Button(
            onClick = { navController.navigate("attendanceScreen") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("근태관리")
        }
        Button(
            onClick = { navController.navigate("todoListScreen") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("팀원 업무관리")
        }
        Button(
            onClick = { navController.navigate("meetingApp") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("회의록")
        }
        Button(
            onClick = { navController.navigate("calendarScreen") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("달력 및 공지사항")
        }
    }
}
