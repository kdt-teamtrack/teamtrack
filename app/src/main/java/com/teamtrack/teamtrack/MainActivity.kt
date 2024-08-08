package com.teamtrack.teamtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teamtrack.teamtrack.attendance.AttendanceScreen
import com.teamtrack.teamtrack.attendance.QRScreen
import com.teamtrack.teamtrack.attendance.ResultScreen
import com.teamtrack.teamtrack.calendar.CalendarScreen
import com.teamtrack.teamtrack.meetingUI.MeetingApp
import com.teamtrack.teamtrack.teamLeaderPageUI.Screen1
import com.teamtrack.teamtrack.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (currentRoute != "firstScreen") {
                            AppBar()
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "firstScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("firstScreen") { FirstScreen(navController) }
                        composable("main") { MainScreen(navController) }
                        composable("screen1") { Screen1() }
                        composable("screen2") { AttendanceScreen(navController) }
                        composable("qrScreen") { QRScreen(navController) }
                        composable("resultScreen/{result}") { backStackEntry ->
                            ResultScreen(navController)
                        }
                        composable("screen3") { TodoListScreen() }
                        composable("screen4") { MeetingApp() }
                        composable("calendarScreen") { CalendarScreen() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(text = "teamtrack") },
        navigationIcon = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.minjee), // 프로필 사진 리소스 아이디
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "사용자 닉네임", fontSize = 16.sp) // 사용자 닉네임
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        )
    )
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
            onClick = { navController.navigate("screen1") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("팀장의 Main화면")
        }
        Button(
            onClick = { navController.navigate("screen2") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("근태관리")
        }
        Button(
            onClick = { navController.navigate("screen3") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("팀원 업무관리")
        }
        Button(
            onClick = { navController.navigate("screen4") },
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
