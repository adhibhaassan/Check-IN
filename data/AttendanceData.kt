package data
import java.time.LocalDateTime
data class AttendanceData(
    val employeeId: Int,
    val checkInDateTime: LocalDateTime,
    var checkOutDateTime: LocalDateTime? =null,
    var workingHours: Double?=null
)
