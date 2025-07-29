# EMPLOYEE CHECK-IN

> OVERVIEW

This is a general architecture and workflow approach for building a Command Line Interface (CLI) based Employee Check-In System using Kotlin. The system is designed to track employee check-ins per day and ensure each employee checks in only once per day.

> WORKFLOW

  [Click here to open the Miro board](https://miro.com/app/board/uXjVJZ06AU4=/)

>  DATA CLASSES

**1.Employee**
  - id :Int
  - firstName :String 
  - lastName :String
  - role :String
  - contactNumber :Long
  - reportingTo: Int
    
**2.EmployeeAttendance**
  - employeeId :Int
  - checkInDateTime :LocalDateTime

> FUNCTIONS

**1.addEmployee()**
  - gets firstName,lastName,role,contactNumber and reportingTo from user and creates an id automatically.
  - stores in  employeeDetails(Map) using Employee data class.

**2.listEmployee()**
  - employeeId and employeeName will be returned.
    
**3.createCheckIn()**
  - gets user id,date and time from the user and validates id using validateId() and checks if any previous check-in has occured in that particular date using hasCheckedIn().
  - stores data in checkedInDetails map using CheckIn(Data class).

**4.validateId()**
  - checks whether id is present in employeeDetails map.

**5.hasCheckedIn()**
  - checks whether id and date is present in checkedInDetails map.

> MAP

**1.employeeDetails**
employeeDetails={
  id:{
    firstName:"    ",
    lastName:"     ",
    role:"         ",
    contactNumber:XXXXXXXXXX,
    reportTo:XXX
  }
}

**2.checkedInDetails**
checkedInDetails={
  date1:[obj1,obj2, ...]
}
