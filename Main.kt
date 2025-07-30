import service.EmployeeService
import service.AttendanceService
fun main() {
    val empService = EmployeeService()
    empService.initializeManagers()
    val attService = AttendanceService()
    while (true) {
        println("\n1. Add Employee\n2. List Employees\n3. Check-In\n4. Check-Out\n5. View Attendance\n6. Exit")
        print("Enter choice: ")
        when (readln().toIntOrNull() ?: 0) {
            1 -> {
                val id = empService.getEmployeeDetails()
                if(id!=-1)
                    println("Employee added with ID: $id")
            }
            2 -> {
                val employees = empService.listEmployees()
                if (employees.isEmpty()) {
                    println("No employees found.")
                }
                else {
                    employees.forEach {
                        println("ID: ${it.id} | Name: ${it.firstName} ${it.lastName}")
                    }
                }
            }
            3 -> {
                val result = attService.handleCheckIn(empService)
                println(result)
            }
            4->{
                attService .checkOutEmployee()
            }
            5 -> {
                val records = attService.getIdForAttendance(empService)
                if (records == null){
                    println("Invalid ID.")
                }
                else if (records.isEmpty()) {
                    println("No attendance records.")
                }
                else {
                    records.forEach { println("Checked in at: ${it.checkInDateTime}") }
                }
            }
            6->break
            else -> println("Invalid choice.")
        }
    }
}

