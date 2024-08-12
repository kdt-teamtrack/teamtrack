package com.teamtrack.teamtrack.login

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.teamtrack.teamtrack.R

@Composable
fun SignUpScreen() {
    var userID by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopSection()

        Box(
            Modifier
                .fillMaxHeight(.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.logo), // replace with your logo resource
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }

        UserInputField(
            label = "EMAIL",
            value = userID,
            onValueChange = { userID = it }
        )

        UserInputField(
            label = "PASSWORD",
            value = userPassword,
            onValueChange = { userPassword = it }
        )

        Box (modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            SignUpButton(userID = userID,
                userPassword = userPassword,
                onSignUpSuccess = { }
            )
        }
    }
}

@Composable
fun TopSection() {
    val colors = listOf(
        Color.Black,
        Color.White,
        Color.Black,
        Color.White,
        Color.Black,
        Color.White
    )
    val rows = 2

    for (rowIndex in 0 until rows) {
        Row(modifier = Modifier.fillMaxWidth()) {
            val currentColors = if (rowIndex % 2 == 0) colors else colors.reversed()

            currentColors.forEach { color ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = color)
                        .height(60.dp)
                )
            }
        }
    }
}

@Composable
fun UserInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        textStyle = TextStyle(color = Color.Black),
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp, 8.dp)
    )
}

@Composable
fun SignUpButton(
    userID: String,
    userPassword: String,
    onSignUpSuccess: () -> Unit
) {
    val context = LocalContext.current
//    val auth = Firebase.auth

    Button(
        onClick = {
            if (userID.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(
                    context,
                    "이메일 / 비밀번호를 입력하세요",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, colors = ButtonDefaults.buttonColors(containerColor = colorTrack)
//        else {
//            auth.createUserWithEmailAndPassword(userID, userPassword)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d("SignUp", "createUserWithEmail:success")
//                        onSignUpSuccess()
//                    } else {
//                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(
//                            context,
//                            task.exception?.message ?: "Sign up failed",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//        }
//    },
    ) {
        Text("가입")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    MaterialTheme {
        SignUpScreen()
    }
}
