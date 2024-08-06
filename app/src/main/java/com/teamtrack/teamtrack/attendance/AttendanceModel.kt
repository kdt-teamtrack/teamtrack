package com.teamtrack.teamtrack.attendance

data class AttendanceModel(
    val employeeName: String,
    val department: String,
    val officeLocation: String,
    val phoneNumber: String,
    val employmentStartDate: String,
    val employmentEndDate: String,
    val employmentType: String,
    val workDate: String,
    val attendanceCount: Int,
    val lateCount: Int,
    val earlyLeaveCount: Int,
    val outCount: Int,
    val absenceCount: Int,
    val attendanceRate: Double,
    val workProgressRate: Double
)
