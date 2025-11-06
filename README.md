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

---
