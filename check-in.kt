import java.time.LocalDate
import java.time.LocalTime

data class Employee(
    val employeeId: Int,
    val firstName: String,
    val lastName: String,
    val role: String,
    val contactNumber: Long,
    val reportTo: Int?
)
data class EmployeeAttendance(
    val employeeId: Int,
    val checkInDate: LocalDate,
    val checkInTime: LocalTime
)

val employeeDetails = mutableMapOf<Int, Employee>()
val checkedInDetails = mutableMapOf<LocalDate, MutableList<EmployeeAttendance>>()
var employeeId = 1

fun addEmployee() {
    println("Enter number of users you want to add:")
    val n = try {
        readln().toInt()
    }
    catch (e: NumberFormatException) {
        println("Invalid input. Please enter a number.")
        return
    }

    for(i  in 0 until n){
        println("Enter Person ${employeeId} details:")
        print("First name: ")
        val firstName = readln()
        print("Last name: ")
        val lastName = readln()
        print("Role: ")
        val role = readln()

        var contactNumber: Long
        while (true) {
            print("Contact number: ")
            try {
                val input = readln()
                if (input.length == 10 && input.all { it.isDigit() }) {
                    contactNumber = input.toLong()
                    break
                }
            }
            catch (e: NumberFormatException) {
                println("Invalid input. Please enter a proper contact number.")
            }
        }
        print("Reporting to (Manager ID): ")
        val reportTo = readln().toInt()

        val employee = Employee(employeeId, firstName, lastName, role, contactNumber, reportTo)
        employeeDetails[employeeId] = employee
        println("Employee added with ID: $employeeId")
        employeeId++
    }
}

fun listEmployee() {
    if (employeeDetails.isEmpty()) {
        println("No employees found.")
    }
    else {
        println("List of Employees:")
        for ((employeeID, employee) in employeeDetails) {
            println("ID: employeeId | Name: ${employee.firstName} ${employee.lastName}")
        }
    }
}

fun validateId(employeeId: Int): Boolean {
    return employeeDetails.containsKey(employeeId)
}

fun hasCheckedIn(employeeId: Int): Boolean {
    val currentDate = LocalDate.now()
    return checkedInDetails[currentDate]?.any {
        it.employeeId == employeeId
    } ?: false
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
        println("Invalid ID!")
        return
    }
    if (hasCheckedIn(id)) {
        println("You have already checked in today !!")
        return
    }
    val date = LocalDate.now()
    val time = LocalTime.now()
    val attendance = EmployeeAttendance(id, date, time)
    if (checkedInDetails.containsKey(date)) {
        checkedInDetails[date]?.add(attendance)
    }
    else {
        checkedInDetails[date] = mutableListOf(attendance)
    }
    println("Check-in at $time on $date")
}

fun main() {
    while (true) {
        println("1.Add Employee\n"+"2.list Employees\n"+"3.Check In\n"+"4.Exit\n")
        println("Enter your option:")
        val choice = try {
            readln().toInt()
        }
        catch (e: NumberFormatException) {
            println("Invalid choice. Please enter a number.")
            continue
        }

        when (choice) {
            1 -> addEmployee()
            2 -> listEmployee()
            3 -> createCheckIn()
            4 -> break
            else -> println("Invalid choice. \nPlease try again.")
        }
    }
}
