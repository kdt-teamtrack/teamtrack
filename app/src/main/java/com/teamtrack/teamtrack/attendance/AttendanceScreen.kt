package com.teamtrack.teamtrack.attendance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AttendanceScreen(navController: NavController, viewModel: AttendanceViewModel = viewModel()) {
    val attendance = viewModel.attendance.value ?: return

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = attendance.employeeName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "부서: ${attendance.department}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "위치: ${attendance.officeLocation}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "전화번호: ${attendance.phoneNumber}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "근무 기간: ${attendance.employmentStartDate} - ${attendance.employmentEndDate}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "고용 형태: ${attendance.employmentType}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "근무일: ${attendance.workDate}", fontSize = 16.sp)
                }
            }
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }

        item {
            Text(
                text = "근태 상세 정보",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "출근: ${attendance.attendanceCount}회", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "지각: ${attendance.lateCount}회", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "조퇴: ${attendance.earlyLeaveCount}회", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "외출: ${attendance.outCount}회", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "결근: ${attendance.absenceCount}회", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "출근률: ${attendance.attendanceRate}%", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "업무 진행률: ${attendance.workProgressRate}%", fontSize = 16.sp)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = { navController.navigate("qrScreen") }, // QR 스캔 화면으로 이동
                modifier = Modifier.padding(8.dp)
            ) {
                Text("출근/퇴근")
            }
        }

        item {
            Button(
                onClick = { /* 외출 버튼 로직 */ },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("외출")
            }
        }
    }
}
