package com.teamtrack.teamtrack

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Screen3() {
    val todoItems = remember {
        mutableStateListOf(
            TodoItem(1, "할 일 1", false),
            TodoItem(2, "할 일 2", true),
            TodoItem(3, "할 일 3", false)
        )
    }

    memberTodo(todoItems = todoItems)
}

data class TodoItem(
    val id: Int,
    val task: String,
    val completed: Boolean
)

@Composable
fun memberTodo(todoItems: MutableList<TodoItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(todoItems) { index, item ->
            DraggableCard(
                todoItem = item,
                onMove = { fromIndex, toIndex ->
                    if (fromIndex != toIndex) {
                        val movedItem = todoItems[fromIndex]
                        todoItems.removeAt(fromIndex)
                        todoItems.add(toIndex, movedItem)
                    }
                },
                index = index,
                itemHeight = 80.dp,
                size = todoItems.size
            )
            if (index < todoItems.size - 1) {
                TimelineConnector()
            }
        }
    }
}

@Composable
fun TimelineConnector() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
            .padding(start = 64.dp)
    )
}

@Composable
fun DraggableCard(
    todoItem: TodoItem,
    onMove: (fromIndex: Int, toIndex: Int) -> Unit,
    index: Int,
    itemHeight: Dp,
    size: Int
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.toPx() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        isDragging = false
                        val toIndex =
                            (index + (offsetY / itemHeightPx).roundToInt()).coerceIn(0, size - 1)
                        onMove(index, toIndex)
                        offsetX = 0f
                        offsetY = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        TodoItemRow(todoItem)
    }
}

@Composable
fun TodoItemRow(item: TodoItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = item.completed,
            onCheckedChange = { /* 체크박스 상태 변경 로직 */ }
        )
        Text(
            text = item.task,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoListScreen() {
    Screen3()
}
