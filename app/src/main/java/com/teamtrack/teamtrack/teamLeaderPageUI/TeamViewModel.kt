package com.teamtrack.teamtrack.teamLeaderPageUI

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
    },
    {
        "id": 3,
        "userName": "AliceSmith",
        "teamName": "Design",
        "rank": "Lead Designer",
        "auth": "User",
        "email": "alicesmith@example.com",
        "phoneNumber": "456-789-1234",
        "createdAt": "2023-08-04T12:00:00",
        "updatedAt": "2023-08-04T12:30:00",
        "state": "Active",
        "profileImageUrl": "https://example.com/images/alicesmith.jpg"
    },
    {
        "id": 4,
        "userName": "BobJohnson",
        "teamName": "Sales",
        "rank": "Sales Director",
        "auth": "Admin",
        "email": "bobjohnson@example.com",
        "phoneNumber": "321-654-9870",
        "createdAt": "2023-08-05T15:00:00",
        "updatedAt": "2023-08-05T15:30:00",
        "state": "Active",
        "profileImageUrl": "https://example.com/images/bobjohnson.jpg"
    },
    {
        "id": 5,
        "userName": "CarolWilliams",
        "teamName": "Human Resources",
        "rank": "HR Manager",
        "auth": "User",
        "email": "carolwilliams@example.com",
        "phoneNumber": "654-321-0987",
        "createdAt": "2023-08-06T10:00:00",
        "updatedAt": "2023-08-06T10:30:00",
        "state": "Active",
        "profileImageUrl": "https://example.com/images/carolwilliams.jpg"
    }
]

        """
        _users.value = Gson().fromJson(jsonUsers, Array<User>::class.java).toList()
    }

    private fun loadTasks() {
        val jsonTasks = """
            [
    {
        "id": 1,
        "assignedUser": "JohnDoe",
        "taskName": "프로젝트 계획 수립",
        "isAssignedByLeader": true,
        "taskStatus": "진행",
        "createdAt": "2024-08-06T09:00",
        "updatedAt": "2024-08-06T12:00"
    },
    {
        "id": 2,
        "assignedUser": "JohnDoe",
        "taskName": "API 문서 작성",
        "isAssignedByLeader": false,
        "taskStatus": "진행",
        "createdAt": "2024-08-06T13:00",
        "updatedAt": "2024-08-06T14:00"
    },
    {
        "id": 3,
        "assignedUser": "JohnDoe",
        "taskName": "코드 리뷰",
        "isAssignedByLeader": true,
        "taskStatus": "대기",
        "createdAt": "2024-08-07T09:00",
        "updatedAt": "2024-08-07T09:30"
    },
    {
        "id": 4,
        "assignedUser": "JaneDoe",
        "taskName": "시장 조사",
        "isAssignedByLeader": false,
        "taskStatus": "대기",
        "createdAt": "2024-08-06T10:00",
        "updatedAt": "2024-08-06T10:30"
    },
    {
        "id": 5,
        "assignedUser": "JaneDoe",
        "taskName": "광고 캠페인 계획",
        "isAssignedByLeader": true,
        "taskStatus": "진행",
        "createdAt": "2024-08-07T10:00",
        "updatedAt": "2024-08-07T11:00"
    },
    {
        "id": 6,
        "assignedUser": "JaneDoe",
        "taskName": "소셜 미디어 분석",
        "isAssignedByLeader": false,
        "taskStatus": "완료",
        "createdAt": "2024-08-05T14:00",
        "updatedAt": "2024-08-05T15:30"
    },
    {
        "id": 7,
        "assignedUser": "AliceSmith",
        "taskName": "디자인 프로토타입 제작",
        "isAssignedByLeader": true,
        "taskStatus": "진행",
        "createdAt": "2024-08-06T11:00",
        "updatedAt": "2024-08-06T12:00"
    },
    {
        "id": 8,
        "assignedUser": "AliceSmith",
        "taskName": "사용자 피드백 수집",
        "isAssignedByLeader": false,
        "taskStatus": "대기",
        "createdAt": "2024-08-07T10:00",
        "updatedAt": "2024-08-07T10:30"
    },
    {
        "id": 9,
        "assignedUser": "AliceSmith",
        "taskName": "디자인 리뷰 회의",
        "isAssignedByLeader": true,
        "taskStatus": "진행",
        "createdAt": "2024-08-08T09:00",
        "updatedAt": "2024-08-08T10:00"
    },
    {
        "id": 10,
        "assignedUser": "BobJohnson",
        "taskName": "영업 전략 회의",
        "isAssignedByLeader": true,
        "taskStatus": "완료",
        "createdAt": "2024-08-05T14:00",
        "updatedAt": "2024-08-05T15:30"
    },
    {
        "id": 11,
        "assignedUser": "BobJohnson",
        "taskName": "고객 피드백 분석",
        "isAssignedByLeader": false,
        "taskStatus": "진행",
        "createdAt": "2024-08-06T10:00",
        "updatedAt": "2024-08-06T11:00"
    },
    {
        "id": 12,
        "assignedUser": "BobJohnson",
        "taskName": "세일즈 목표 설정",
        "isAssignedByLeader": true,
        "taskStatus": "대기",
        "createdAt": "2024-08-07T15:00",
        "updatedAt": "2024-08-07T15:30"
    },
    {
        "id": 13,
        "assignedUser": "CarolWilliams",
        "taskName": "직원 만족도 조사",
        "isAssignedByLeader": true,
        "taskStatus": "진행",
        "createdAt": "2024-08-07T10:00",
        "updatedAt": "2024-08-07T11:00"
    },
    {
        "id": 14,
        "assignedUser": "CarolWilliams",
        "taskName": "인사 정책 검토",
        "isAssignedByLeader": false,
        "taskStatus": "대기",
        "createdAt": "2024-08-08T09:00",
        "updatedAt": "2024-08-08T09:30"
    },
    {
        "id": 15,
        "assignedUser": "CarolWilliams",
        "taskName": "신입 사원 교육 계획",
        "isAssignedByLeader": true,
        "taskStatus": "진행",
        "createdAt": "2024-08-09T10:00",
        "updatedAt": "2024-08-09T11:00"
    }
]

        """
        _tasks.value = Gson().fromJson(jsonTasks, Array<Task>::class.java).toList()
    }

}