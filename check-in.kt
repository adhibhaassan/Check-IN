import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Employee(
    val employeeId: Int,
    val firstName: String,
    val lastName: String,
    val role: String,
    val contactNumber: Long,
    val reportTo: Int
)

data class EmployeeAttendance(
    val employeeId: Int,
    val checkInDateTime: LocalDateTime
)

val employeeDetails = mutableMapOf<Int, Employee>()
val checkedInDetails = mutableMapOf<Int, MutableList<EmployeeAttendance>>()
var employeeId = 1

fun addEmployee() {
    while (true) {
        println("Enter Person $employeeId details:")
        print("First name: ")
        val firstName = readln()
        print("Last name: ")
        val lastName = readln()
        print("Role: ")
        val role = readln()

        var contactNumber: Long
        while (true) {
            print("Contact number (10 digits): ")
            val input = readln()
            if (input.length == 10 && input.all { it.isDigit() }) {
                contactNumber = input.toLong()
                break
            } else {
                println("Please enter a valid 10-digit number.")
            }
        }

        print("Reporting to (Manager ID): ")
        val reportTo = readln().toIntOrNull() ?: 0

        val employee = Employee(employeeId, firstName, lastName, role, contactNumber, reportTo)
        employeeDetails[employeeId] = employee
        println("Employee added with ID: $employeeId")
        employeeId++

        print("Do you want to add another employee? (y/n): ")
        val more = readln()
        if (more.lowercase() != "y") break
    }
}

fun listEmployee() {
    if (employeeDetails.isEmpty()) {
        println("No employees found.")
    } else {
        println("List of Employees:")
        for ((id, emp) in employeeDetails) {
            println("ID: $id | Name: ${emp.firstName} ${emp.lastName} | Role: ${emp.role}")
        }
    }
}
fun validateId(id: Int): Boolean {
    return employeeDetails.containsKey(id)
}
fun hasCheckedIn(employeeId: Int, date: LocalDate): Boolean {
    val attendanceList = checkedInDetails[employeeId] ?: return false
    return attendanceList.any {
        it.checkInDateTime.toLocalDate() == date
    }
}
fun createCheckIn() {
    print("Enter your Employee ID: ")
    val id = try {
        readln().toInt()
    }
    catch (e: NumberFormatException) {
        println("Invalid ID format. Please enter a numeric ID.")
        return
    }

    if (!validateId(id)) {
        println("Employee ID not found.")
        return
    }


    print("Enter check-in date and time (yyyy-MM-dd HH:mm), or press Enter to use current: ")
    val input = readln()
    val dateTime: LocalDateTime = if (input.isBlank()) {
        LocalDateTime.now()
    }
    else {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        try {
            val parsed = LocalDateTime.parse(input, formatter)
            if (parsed.isAfter(LocalDateTime.now())) {
                println("Future dates not allowed.")
                return
            }
            parsed
        }
        catch (e: DateTimeParseException) {
            println("Invalid format. Please use 'yyyy-MM-dd HH:mm'.")
            return
        }
    }
    val today = LocalDate.now()
    if (hasCheckedIn(id, today)) {
        println("You have already checked in today.")
        return
    }

    val attendance = EmployeeAttendance(id, dateTime)
    checkedInDetails.getOrPut(id) {
        mutableListOf()
    }.add(attendance)
    println("Check-in recorded at ${dateTime.toLocalTime()} on ${dateTime.toLocalDate()}")
}

fun main() {
    while (true) {
        println("1. Add Employee")
        println("2. List Employees")
        println("3. Check-In")
        println("4. Exit")
        print("Enter your choice: ")

        when (readln().toIntOrNull()) {
            1 -> addEmployee()
            2 -> listEmployee()
            3 -> createCheckIn()
            4 -> break
            else -> println("Invalid choice. Try again.")
        }
    }
}
