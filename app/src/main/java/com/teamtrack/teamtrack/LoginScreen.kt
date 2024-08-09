package com.teamtrack.teamtrack

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    val colorTrack = Color(0xFF33ADFF)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(colorTrack)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(BorderStroke(1.dp, Color.White))
                    )
                }
            }

            // 로고와 텍스트 오버레이
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo), // replace with your logo resource
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Team Track",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }

            // 로고와 텍스트 오버레이
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo), // replace with your logo resource
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Team Track",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }
        }

    // Middle Section
    Box(modifier = Modifier.weight(.5f)) {
        Column(modifier = Modifier.fillMaxSize()) {
            val colors = listOf(Color.Black, Color.White, Color.Black, Color.White)
            val rows = 2

            for (rowIndex in 0 until rows) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    val currentColors = if (rowIndex % 2 == 0) colors else colors.reversed()

                    currentColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(color = color)
                                .height(100.dp)
                        )
                    }
                }
            }
        }
    }

    // Bottom Section
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("teamLeaderScreen") },
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF33ADFF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.minjee), // replace with your image resource
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("팀장", fontSize = 18.sp, color = Color.White)
                    }
                }
                Button(
                    onClick = { navController.navigate("homeScreen") },
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF33ADFF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.heayen), // replace with your image resource
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("팀원", fontSize = 18.sp, color = Color.Black)
                    }
                }
            }
            Button(
                onClick = { /* Navigate to sign-up screen */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("회원이 아니신가요? 회원가입", fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}
}

//@Composable
//fun LoginScreen(navController: NavHostController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Brush.verticalGradient(listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))))
//            .padding(16.dp)
//    ) {
//        // Top Section
//        Box(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth()
//                .background(Color.White, CircleShape)
//                .padding(16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.logo), // replace with your logo resource
//                    contentDescription = null,
//                    modifier = Modifier.size(60.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "Team Track",
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    color = Color.Black
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Middle Section
//        Box(
//            modifier = Modifier
//                .weight(3f)
//                .fillMaxWidth()
//                .background(Color.White, CircleShape)
//                .padding(16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(
//                    onClick = { navController.navigate("teamLeaderScreen") },
//                    modifier = Modifier
//                        .size(140.dp)
//                        .clip(CircleShape)
//                        .background(Brush.linearGradient(listOf(Color(0xFF64B5F6), Color(0xFF1E88E5))))
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.minjee), // replace with your image resource
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(60.dp)
//                                .clip(CircleShape),
//                            contentScale = ContentScale.Crop
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text("팀장", fontSize = 18.sp, color = Color.White)
//                    }
//                }
//                Button(
//                    onClick = { navController.navigate("homeScreen") },
//                    modifier = Modifier
//                        .size(140.dp)
//                        .clip(CircleShape)
//                        .background(Brush.linearGradient(listOf(Color(0xFF64B5F6), Color(0xFF1E88E5))))
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.heayen), // replace with your image resource
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(60.dp)
//                                .clip(CircleShape),
//                            contentScale = ContentScale.Crop
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text("팀원", fontSize = 18.sp, color = Color.White)
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Bottom Section
//        Box(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth()
//                .background(Color.White, CircleShape)
//                .padding(16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Button(
//                onClick = { /* Navigate to sign-up screen */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            ) {
//                Text("회원이 아니신가요? 회원가입", fontSize = 16.sp, color = Color.Black)
//            }
//        }
//    }
//}
