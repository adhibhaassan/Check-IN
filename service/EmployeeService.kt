package service
import data.EmployeeData
class EmployeeService {
    private val employeeDetails = mutableMapOf<Int, EmployeeData>()
    fun initializeManagers() {
        val predefinedManagers = listOf(
            EmployeeData(1, "Alice", "Johnson", "Manager", 9876543210, 0),
            EmployeeData(2, "Bob", "Smith", "Manager", 9123456789, 0),
            EmployeeData(3, "Carol", "Taylor", "Manager", 9234567890, 0),
            EmployeeData(4, "David", "Williams", "Manager", 9345678901, 0),
            EmployeeData(5, "Eve", "Brown", "Manager", 9456789012, 0),
            EmployeeData(6, "Frank", "Jones", "Manager", 9567890123, 0),
            EmployeeData(7, "Grace", "Garcia", "Manager", 9678901234, 0),
            EmployeeData(8, "Hank", "Martinez", "Manager", 9789012345, 0),
            EmployeeData(9, "Ivy", "Davis", "Manager", 9890123456, 0),
            EmployeeData(10, "Jack", "Miller", "Manager", 9901234567, 0)
    )
    for (manager in predefinedManagers) {
        employeeDetails[manager.id] = manager
    }
}
    var employeeIdCounter = 11

    fun addEmployee(first: String, last: String, role: String, contact: Long, reportTo: Int): Int {
        val employee = EmployeeData(employeeIdCounter, first, last, role, contact, reportTo)
        employeeDetails[employeeIdCounter] = employee
        return employeeIdCounter++
    }
    fun getEmployeeDetails(): Int {
        print("First name: ")
        val firstName = readln()
        print("Last name: ")
        val lastName = readln()
        print("Role: ")
        val role = readln()
        print("Contact number (10 digits): ")
        val contactInput = readln()
        if (contactInput.length != 10 || contactInput.any { !it.isDigit() }) {
            println("Invalid contact number.\nIt must be a 10-digit number.")
            return -1
        }
        val contactNumber = contactInput.toLong()

        print("Reporting to (Manager ID): ")
        val reportToInput = readln()
        val reportTo: Int = reportToInput.toInt()
        if (!isValidManagerId(reportTo)) {
            println("Invalid manager ID.\nPlease enter an existing manager's ID.\nCheck the Employee List for more details")
            return -1
        }
        return addEmployee(firstName, lastName, role, contactNumber, reportTo)
    }
    fun listEmployees(): List<EmployeeData>{
        return employeeDetails.values.toList()
    }

    fun isValidId(id: Int): Boolean{
        return employeeDetails.containsKey(id)
    }

    private fun isValidManagerId(id: Int): Boolean {
        val manager = employeeDetails[id]
        return manager != null && manager.role.equals("Manager", ignoreCase = true)
    }


}