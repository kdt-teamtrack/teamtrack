package com.teamtrack.teamtrack.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AttendanceViewModel : ViewModel() {
    private val _attendance = MutableLiveData<AttendanceModel>()
    val attendance: LiveData<AttendanceModel> get() = _attendance

    init {
        // 임시 데이터 초기화
        _attendance.value = AttendanceModel(
            employeeName = "John Doe",
            department = "Engineering",
            officeLocation = "Seoul Gangnam-gu",
            phoneNumber = "02-6240-4882",
            employmentStartDate = "2020-01-15",
            employmentEndDate = "",
            employmentType = "Full-Time",
            workDate = "2024-08-06",
            attendanceCount = 99,
            lateCount = 2,
            earlyLeaveCount = 1,
            outCount = 0,
            absenceCount = 0,
            attendanceRate = 83.2,
            workProgressRate = 84.0
        )
    }
}
