package service
import data.AttendanceData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AttendanceService {
    private val attendanceMap = mutableMapOf<Int, MutableList<AttendanceData>>()

    fun handleCheckIn(empService: EmployeeService): String {
        print("Enter your Employee ID: ")
        val id = readlnOrNull()?.toIntOrNull()
        if (id == null || !empService.isValidId(id)) {
            return "Invalid ID."
        }
        val dateTime = getCheckInTime() ?: return "Invalid format or future date."
        return if (recordCheckIn(id, dateTime)) {
            "Check-in recorded at ${dateTime.toLocalTime()} on ${dateTime.toLocalDate()}"
        }
        else {
            "Already checked in for today."
        }
    }

    fun getCheckInTime(): LocalDateTime? {
        val input = readCheckInInput()
        return if (isInputBlank(input)) {
            LocalDateTime.now()
        } else {
            parseCheckInInput(input)
        }
    }

    fun readCheckInInput(): String {
        print("Enter check-in date and time (yyyy-MM-dd HH:mm), or press Enter for current: ")
        return readln()
    }

    fun isInputBlank(input: String): Boolean {
        return input.isBlank()
    }

    fun parseCheckInInput(input: String): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val parsed = LocalDateTime.parse(input, formatter)
            if (parsed.isAfter(LocalDateTime.now())) null else parsed
        } catch (e: DateTimeParseException) {
            null
        }
    }


    fun recordCheckIn(id: Int, dateTime: LocalDateTime): Boolean {
        val dateToCheck = dateTime.toLocalDate()
        if (hasCheckedIn(id, dateToCheck)){
            return false
        }
        else{
            attendanceMap.getOrPut(id) { mutableListOf() }.add(AttendanceData(id, dateTime))
        }
        return true
    }

    fun hasCheckedIn(id: Int, date: LocalDate): Boolean {
        return attendanceMap[id]?.any {
            it.checkInDateTime.toLocalDate() == date
        } == true
    }

    fun getIdForAttendance(empService: EmployeeService): List<AttendanceData>? {
        print("Enter Employee ID to view attendance: ")
        val id = readlnOrNull()?.toIntOrNull()
        return if (id == null || !empService.isValidId(id)){
            null
        }
        else {
            returnAttendance(id)
        }
    }

    fun returnAttendance(id: Int): List<AttendanceData> {
        return attendanceMap[id] ?: emptyList()
    }

}