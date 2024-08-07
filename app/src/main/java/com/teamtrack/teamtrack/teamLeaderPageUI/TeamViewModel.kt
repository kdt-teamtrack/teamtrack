package com.teamtrack.teamtrack.teamLeaderPageUI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.teamtrack.teamtrack.data.Task
import com.teamtrack.teamtrack.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeamViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    init {
        // JSON 데이터를 서버에서 받아와서 초기화하는 부분 (여기서는 예시로 하드코딩)
        loadUsers()
        loadTasks()
    }

    private fun loadUsers() {
        val jsonUsers = """
            [
              {
                "id": 1,
                "userName": "JohnDoe",
                "teamName": "Development",
                "rank": "Senior Developer",
                "auth": "Admin",
                "email": "johndoe@example.com",
                "phoneNumber": "123-456-7890",
                "createdAt": "2023-08-01T10:15:30",
                "updatedAt": "2023-08-02T11:20:35",
                "state": "Active",
                "profileImageUrl": "https://example.com/images/johndoe.jpg"
              },
              {
                "id": 2,
                "userName": "JaneDoe",
                "teamName": "Marketing",
                "rank": "Marketing Manager",
                "auth": "User",
                "email": "janedoe@example.com",
                "phoneNumber": "987-654-3210",
                "createdAt": "2023-08-03T09:00",
                "updatedAt": "2023-08-03T10:00",
                "state": "Active",
                "profileImageUrl": "https://example.com/images/janedoe.jpg"
              }
            ]
        """
        _users.value = Gson().fromJson(jsonUsers, Array<User>::class.java).toList()
    }
    private fun loadTasks() {
        val jsonTasks = """
            [
              {"id":1,"assignedUser":"JohnDoe","taskName":"프로젝트 계획 수립","isAssignedByLeader":"true","taskStatus":"진행","createdAt":"2024-08-06T09:00","updatedAt":"2024-08-06T12:00"},
              {"id":2,"assignedUser":"JaneDoe","taskName":"시장 조사","isAssignedByLeader":"false","taskStatus":"대기","createdAt":"2024-08-06T10:00","updatedAt":"2024-08-06T10:30"},
              {"id":3,"assignedUser":"EmilySmith","taskName":"클라이언트 미팅","isAssignedByLeader":"true","taskStatus":"완료","createdAt":"2024-08-05T14:00","updatedAt":"2024-08-05T15:30"},
              {"id":4,"assignedUser":"MichaelBrown","taskName":"제품 디자인 검토","isAssignedByLeader":"false","taskStatus":"보류","createdAt":"2024-08-07T11:00","updatedAt":"2024-08-07T11:30"}
            ]
        """
        _tasks.value = Gson().fromJson(jsonTasks, Array<Task>::class.java).toList()
    }

}