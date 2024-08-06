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

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.teamtrack.teamtrack.attendance.AttendanceScreen
import com.teamtrack.teamtrack.attendance.QRScreen
import com.teamtrack.teamtrack.attendance.ResultScreen
import com.teamtrack.teamtrack.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("main") { MainScreen(navController) }
                        composable("screen1") { Screen1() }
                        composable("screen2") { AttendanceScreen(navController) }
                        composable("qrScreen") { QRScreen(navController) }
                        composable("resultScreen/{result}") { backStackEntry ->
                            ResultScreen(navController)
                        }
                        composable("screen3") { Screen3() }
                        composable("screen4") { Screen4() }
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
            onClick = { navController.navigate("screen1") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Go to Screen 1")
        }
        Button(
            onClick = { navController.navigate("screen2") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Go to Screen 2")
        }
        Button(
            onClick = { navController.navigate("screen3") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Go to Screen 3")
        }
        Button(
            onClick = { navController.navigate("screen4") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Go to Screen 4")
        }
    }
}


//@Composable
//fun Screen1() {
//    Text(
//        text = "팀장의 메인 페이지",
//        modifier = Modifier.fillMaxSize()
//    )
//}

@Composable
fun Screen2() {
    Text(
        text = "2번 QR 스크린 ",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun Screen3() {
    Text(
        text = "3번팀원 Task",
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun Screen4() {
    Text(
        text = "4번 회의 페이지",
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun Screen1Preview() {
    MyApplicationTheme {
        Screen1()
    }
}

@Preview(showBackground = true)
@Composable
fun Screen2Preview() {
    MyApplicationTheme {
        Screen2()
    }
}

@Preview(showBackground = true)
@Composable
fun Screen3Preview() {
    MyApplicationTheme {
        Screen3()
    }
}

@Preview(showBackground = true)
@Composable
fun Screen4Preview() {
    MyApplicationTheme {
        Screen4()
    }
}
