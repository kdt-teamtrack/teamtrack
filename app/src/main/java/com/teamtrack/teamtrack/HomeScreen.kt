package com.teamtrack.teamtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Welcome Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Welcome,\nBill Gates",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.minjee), // replace with your profile image resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date Section
            Text(
                text = "2024.01.10",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tasks Section
            Box(
                modifier = Modifier
                    .background(Color(0xFFF1F1F1))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "TODAY JUST DO IT",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "○ 오늘의 할 일", fontSize = 18.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Check In and Out Section
            Box(
                modifier = Modifier
                    .background(Color(0xFFF1F1F1))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "CHECK IN AND OUT",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "출근 시간: 08:48 2024.01.10 (월)", fontSize = 18.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "퇴근 시간: 06:25 2024.01.10 (월)", fontSize = 18.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Schedule Section
            Box(
                modifier = Modifier
                    .background(Color(0xFFF1F1F1))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "TODAY SCHEDULE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "| 팀국이랑 미팅 10:00 ~ 11:00", fontSize = 18.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "| 일론이랑 식사 13:00 ~ 14:00", fontSize = 18.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // The rest of your content goes here...

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, "homeScreen"),
        BottomNavItem("Schedule", Icons.Filled.DateRange, "scheduleScreen"), // 아이콘 변경
        BottomNavItem("Meeting", Icons.Filled.Person, "meetingScreen"),
        BottomNavItem("Task", Icons.AutoMirrored.Filled.List, "taskScreen")
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, color = Color.Black) },
                selected = false,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
