package com.teamtrack.teamtrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider as HorizontalDivider

@Composable
fun TaskScreen() {
    val todoItems = remember {
        mutableStateListOf(
            TodoItem(1, "할 일 1", false),
            TodoItem(2, "할 일 2", true),
            TodoItem(3, "할 일 3", false)
        )
    }
    TaskHistory(todoItems = todoItems)
}

//data class TaskInfo(val year: Int, val month: Int, val day: Int, val task: String)

@Composable
fun TaskHistory(todoItems: MutableList<TodoItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(todoItems) { info ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Text("${info.year}.${info.month}.${info.day}")
                Text(info.task)
            }
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIndivTaskHistoryScreen() {
    TaskScreen()
}