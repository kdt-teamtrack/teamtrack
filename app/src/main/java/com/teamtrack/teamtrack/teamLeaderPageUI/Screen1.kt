package com.teamtrack.teamtrack.teamLeaderPageUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamtrack.teamtrack.R


//팀장의 메인 페이지
@Composable
fun Screen1() {
    var rank by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var task by remember { mutableStateOf("") }
    var teamMembercount = 6

    LazyColumn(){
    items(teamMembercount){ item ->
        TeamMemberCard(rank, name, task)
    }
    }
}

@Composable
fun TeamMemberCard(
    rank: String,name:String, task: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.healin),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(text = "이름 : ${name}",color = Color.Gray)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "직급 : ${rank} ", color = Color.Gray)
                }
                Text(
                    text = "진행 업무 : ${task}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen1() {
    Screen1()
}