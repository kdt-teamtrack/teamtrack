package com.teamtrack.teamtrack.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.teamtrack.teamtrack.R
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.teamtrack.teamtrack.data.User
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

val colorTrack = Color(0xFF33ADFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = Firebase.auth

//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
    // 하드코딩된 이메일과 비밀번호
    var email by remember { mutableStateOf("bobwhite@example.com") }
    var password by remember { mutableStateOf("123456") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top Section
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
                        painter = painterResource(id = R.drawable.logo),
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

        TopSection()
        Spacer(modifier = Modifier.height(32.dp))
        // Middle Section: 이메일과 비밀번호 입력 칸 추가
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("이메일") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("비밀번호") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(
                        onClick = { navController.navigate("signUpScreen") },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorTrack,
                            containerColor = Color.White
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.folder_icon),
                            contentDescription = null,
                            tint = colorTrack,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text("회원가입", fontSize = 16.sp, color = Color.Black)
                    }
                }
            }
        }


        // Bottom Section
        Box(
            modifier = Modifier
                .weight(.5f).padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // 로그인 성공 시 사용자의 이메일을 기반으로 서버에서 사용자 정보 가져오기
                                        val userEmail = auth.currentUser?.email
                                        if (userEmail != null) {
                                            // 코루틴을 사용하여 서버 요청 처리
                                            kotlinx.coroutines.MainScope().launch {
                                                val user = fetchUserDetailsByEmail(userEmail)
                                                if (user != null) {
                                                    // 사용자 ID 로그 출력
                                                    Log.d("LoginScreen", "Fetched User ID: ${user.id}")

                                                    // 사용자 정보를 가져왔을 경우 다음 화면으로 이동
                                                    navController.navigate("projectSelectionScreen/${user.id}")
                                                } else {
                                                    Toast.makeText(context, "사용자 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "로그인 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = colorTrack,
                        containerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().border(2.dp, colorTrack, CircleShape)
                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.folder_icon),
//                        contentDescription = null,
//                        modifier = Modifier.padding(8.dp),
//                        tint = colorTrack
//                    )
                    Text(
                        text = "로그인",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


suspend fun fetchUserDetailsByEmail(email: String): User? {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    return withContext(Dispatchers.IO) {
        try {
            val users: List<User> = client.get("http://192.168.45.25:9292/users?email=$email").body()
            // 정확히 일치하는 이메일을 가진 사용자를 반환
            users.find { it.email == email }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            client.close()
        }
    }
}
