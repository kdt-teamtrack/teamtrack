package com.teamtrack.teamtrack.attendance

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ResultScreen(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val result = backStackEntry.value?.arguments?.getString("result") ?: "No Result"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "QR Code Result:", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = result, fontSize = 18.sp)
    }
}
