# 🎓 Student Management System

A comprehensive **two-role desktop application** for managing university student data, built with **Java Swing** and **MySQL**.

---

## 📖 Description

This project is a complete management system for a university or college. It provides a secure, role-based login for both **Admins** (faculty, staff) and **Students**.

- **Admins** have full CRUD (Create, Read, Update, Delete) capabilities over student records, can manage course fees, approve leave requests, and enter examination marks.  
- **Students** can log in to view their personal information, check leave status, view grades, and see payment history.

This application is built entirely in **Java Swing** for the frontend and uses **MySQL** for backend data persistence.

---

## ✨ Features

### 👨‍💼 Admin Portal
- **Student Management:** Add, view, update, and delete student records.  
- **Fee Management:**
  - Set and update the fee structure for different courses.
  - Record new fee payments from students.
- **Leave Approval:** View all pending student leave requests and approve or deny them.
- **Marks Entry:** Enter semester-wise marks for 5 subjects for any student.
- **View Results:** Look up the examination results for any student.

### 🧑‍🎓 Student Portal
- **Secure Login:** Students log in using their unique Student ID and Aadhar number.  
- **Personalized Dashboard:** The dashboard greets the student by name.  
- **Profile Management:** Students can view and update their personal details (like phone or address).  
- **Leave System:**
  - Apply for leave using a simple “from” and “to” date form.  
  - View a history of all leave requests and their status (Pending, Approved, Denied).  
- **Academic Results:** View semester-wise grades and marks.  
- **Fee History:** View a complete history of all payments made.  

---

## 🛠️ Tech Stack & Libraries

| Category | Technology |
|-----------|-------------|
| **Core** | Java |
| **Frontend** | Java Swing |
| **Backend / Database** | MySQL |
| **External Libraries** | `mysql-connector-j`, `jcalendar.jar`, `rs2xml.jar` |

**Library Uses:**
- `mysql-connector-j`: Connects Java app with MySQL database.  
- `jcalendar.jar`: Provides pop-up date pickers.  
- `rs2xml.jar`: Easily populates `JTable` components from a `ResultSet`.

---

## 🚀 How to Run This Project

### 1. Prerequisites
Make sure you have the following installed:
- [Java JDK 11+](https://www.oracle.com/java/technologies/downloads/)  
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (or any MySQL client)  
- An IDE (VS Code, Eclipse, or IntelliJ IDEA)

---

### 2. Clone the Repository
```bash
git clone https://github.com/your-username/student-management-system.git
cd student-management-system
```

---
### 3. Database Setup
  1. Open MySQL Workbench and connect to your local database server.
  2. Create a new database named studentmanagement:
     ```bash
     CREATE DATABASE studentmanagement;
     USE studentmanagement;
     ```
  3. Run the following SQL scripts to create all required tables:
     
      ```bash
        -- 1. Admins Table
          CREATE TABLE login(
              username VARCHAR(30) NOT NULL PRIMARY KEY,
              password VARCHAR(60)
          );
      
        -- 2. Students Table
        CREATE TABLE student (
            studentID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            fatherName VARCHAR(100),
            dob VARCHAR(30),
            address VARCHAR(255),
            phone VARCHAR(20),
            email VARCHAR(100),
            m10 VARCHAR(10),
            m12 VARCHAR(10),
            aadhar VARCHAR(15) UNIQUE,
            course VARCHAR(50),
            department VARCHAR(50)
        );
        ALTER TABLE student AUTO_INCREMENT = 1000;
        
        -- 3. Leave Requests
        CREATE TABLE student_leave (
            leaveID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            studentID INT NOT NULL,
            from_date VARCHAR(50),
            to_date VARCHAR(50),
            status VARCHAR(50) DEFAULT 'Pending',
            FOREIGN KEY (studentID) REFERENCES student(studentID) ON DELETE CASCADE
        );
        
        -- 4. Course Fee Structure
        CREATE TABLE course_fees (
            course_name VARCHAR(50) NOT NULL PRIMARY KEY,
            total_fee INT
        );
        
        INSERT INTO course_fees (course_name, total_fee) VALUES
        ('B.Tech', 120000), ('BBA', 80000), ('BCA', 70000),
        ('B.Sc', 60000), ('M.Sc', 90000), ('MBA', 150000), ('MCA', 110000);
        
        -- 5. Fee Payments
        CREATE TABLE student_fees (
            feeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            studentID INT NOT NULL,
            course VARCHAR(50),
            semester VARCHAR(50),
            total_amount VARCHAR(20),
            paid_amount VARCHAR(20),
            payment_date VARCHAR(50),
            FOREIGN KEY (studentID) REFERENCES student(studentID) ON DELETE CASCADE
        );
        
        -- 6. Marks Table
        CREATE TABLE student_marks (
            markID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            studentID INT NOT NULL,
            semester VARCHAR(20) NOT NULL,
            subject1_name VARCHAR(50), subject1_marks VARCHAR(10),
            subject2_name VARCHAR(50), subject2_marks VARCHAR(10),
            subject3_name VARCHAR(50), subject3_marks VARCHAR(10),
            subject4_name VARCHAR(50), subject4_marks VARCHAR(10),
            subject5_name VARCHAR(50), subject5_marks VARCHAR(10),
            FOREIGN KEY (studentID) REFERENCES student(studentID) ON DELETE CASCADE
        );
      ```
      
    

    

