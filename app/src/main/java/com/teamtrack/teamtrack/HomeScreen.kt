package com.teamtrack.teamtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        // Welcome Section with smaller text
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
                    text = "Bill Gates", // Replace with dynamic content later
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.minjee), // replace with your profile image resource
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
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

        // Tasks Section as Card
        InfoCard(
            title = "TODAY JUST DO IT",
            content = {
                Text(text = "○ 오늘의 할 일", fontSize = 18.sp, color = Color.Black)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Check In and Out Section as Card
        InfoCard(
            title = "CHECK IN AND OUT",
            content = {
                Text(
                    text = "출근 시간: 08:48 2024.01.10 (월)",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "퇴근 시간: 06:25 2024.01.10 (월)",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Schedule Section as Card
        InfoCard(
            title = "TODAY SCHEDULE",
            content = {
                Text(text = "| 팀국이랑 미팅 10:00 ~ 11:00", fontSize = 18.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "| 일론이랑 식사 13:00 ~ 14:00", fontSize = 18.sp, color = Color.Black)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // The rest of your content goes here...

    }
}

@Composable
fun InfoCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
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
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", iconVector = Icons.Filled.Home, route = "homeScreen"),
        BottomNavItem("Schedule", iconVector = Icons.Filled.DateRange, route = "calendarScreen"),
        BottomNavItem("Meeting", iconVector = Icons.Filled.Person, route = "meetingApp"),
        BottomNavItem(
            "QR",
            iconPainter = painterResource(R.drawable.qr_icon),
            route = "attendanceScreen"
        ) // Using Painter for custom icon
    )

    NavigationBar(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Rounded top corner
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
                            modifier = Modifier.size(24.dp) // Adjust the size to match other icons
                        )
                    }
                },
                label = { Text(item.label, color = Color.Black) },
                selected = false,
                onClick = {
                    navController.navigate(item.route)
                }
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
