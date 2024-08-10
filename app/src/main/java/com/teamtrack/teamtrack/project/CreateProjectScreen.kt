package com.teamtrack.teamtrack.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.LocalDate

@Composable
fun CreateProjectScreen(navController: NavHostController) {
    val projectName = remember { mutableStateOf("") }
    val projectDescription = remember { mutableStateOf("") }
    val projectStartDate = remember { mutableStateOf(LocalDate.now().toString()) }
    val projectEndDate = remember { mutableStateOf(LocalDate.now().plusDays(30).toString()) }
    val projectTeamMembers = remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        item {
            TitleText("프로젝트 생성")
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ProjectNameField(projectName)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ProjectDateField("프로젝트 시작일 (YYYY-MM-DD)", projectStartDate, ImeAction.Next)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            ProjectDateField("프로젝트 종료일 (YYYY-MM-DD)", projectEndDate, ImeAction.Done)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ProjectTeamMembersField(projectTeamMembers)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ProjectDescriptionField(projectDescription)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            CreateProjectButton(navController)
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        color = Color.Black, // 검은색으로 텍스트 색상 변경
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun ProjectNameField(projectName: MutableState<String>) {
    OutlinedTextField(
        value = projectName.value,
        onValueChange = { projectName.value = it },
        label = { Text("프로젝트 이름", color = Color.DarkGray) }, // 진한 회색으로 라벨 색상 변경
        textStyle = LocalTextStyle.current.copy(color = Color.Black), // 입력 텍스트를 검은색으로 변경
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun ProjectDateField(label: String, dateState: MutableState<String>, imeAction: ImeAction) {
    OutlinedTextField(
        value = dateState.value,
        onValueChange = { dateState.value = it },
        label = { Text(label, color = Color.DarkGray) }, // 진한 회색으로 라벨 색상 변경
        textStyle = LocalTextStyle.current.copy(color = Color.Black), // 입력 텍스트를 검은색으로 변경
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        )
    )
}

@Composable
fun ProjectDescriptionField(projectDescription: MutableState<String>) {
    OutlinedTextField(
        value = projectDescription.value,
        onValueChange = { projectDescription.value = it },
        label = { Text("프로젝트 설명", color = Color.DarkGray) }, // 진한 회색으로 라벨 색상 변경
        textStyle = LocalTextStyle.current.copy(color = Color.Black), // 입력 텍스트를 검은색으로 변경
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Default
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProjectTeamMembersField(projectTeamMembers: MutableState<String>) {
    val selectedEmails = remember { mutableStateListOf<String>() }
    val emailInput = remember { mutableStateOf("") }
    val emailSuggestions = remember { mutableStateOf(listOf<String>()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxWidth()) {
        // 선택된 이메일을 보여주는 부분
        Column(modifier = Modifier.fillMaxWidth()) {
            selectedEmails.forEach { email ->
                Text(
                    text = email,
                    color = Color.Black, // 선택된 이메일 텍스트를 검은색으로 변경
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.LightGray)
                        .padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 이메일 검색 입력 필드
        OutlinedTextField(
            value = emailInput.value,
            onValueChange = { input ->
                emailInput.value = input
                emailSuggestions.value = getMatchingEmails(input).take(5) // 최대 5개의 이메일만 표시
            },
            label = { Text("팀원 추가 (이메일)", color = Color.DarkGray) }, // 진한 회색으로 라벨 색상 변경
            textStyle = LocalTextStyle.current.copy(color = Color.Black), // 입력 텍스트를 검은색으로 변경
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide() // 키보드 숨기기
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 유사한 이메일 목록을 표시
        Column(modifier = Modifier.fillMaxWidth()) {
            emailSuggestions.value.forEach { suggestion ->
                Text(
                    text = suggestion,
                    color = Color.DarkGray, // 추천 이메일 텍스트를 진한 회색으로 변경
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // 이메일 클릭 시 선택된 이메일 목록에 추가
                            if (!selectedEmails.contains(suggestion)) {
                                selectedEmails.add(suggestion)
                            }
                            emailInput.value = ""
                            emailSuggestions.value = emptyList() // 클릭 후 추천 목록 제거
                            keyboardController?.hide() // 키보드 숨기기
                        }
                )
            }
        }
    }
}

// 예제: 로컬 이메일 목록에서 입력과 유사한 이메일을 반환하는 함수
fun getMatchingEmails(query: String): List<String> {
    val allEmails = listOf(
        "john.doe@example.com", "jane.doe@example.com", "john.smith@example.com",
        "jane.smith@example.com", "smith.john@example.com", "doe.jane@example.com"
    )

    return if (query.isNotEmpty()) {
        allEmails.filter { it.contains(query, ignoreCase = true) }
    } else {
        emptyList()
    }
}

@Composable
fun CreateProjectButton(navController: NavHostController) {
    Button(
        onClick = {
            // TODO: 서버로 프로젝트 생성 요청을 보내는 로직 추가
            navController.navigate("projectScreen")
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33ADFF))
    ) {
        Text("프로젝트 생성", color = Color.White, fontSize = 16.sp)
    }
}
