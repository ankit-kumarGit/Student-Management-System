package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class UpdateStudent extends JFrame implements ActionListener {

    // Components
    JTextField textName, textFather, textAddress, textPhone, textEmail, textM10, textM12, textAadhar;
    JLabel labelStudentID, labelDept; // StudentID is now a label
    JDateChooser cdbob;
    JComboBox<String> courseBox, departmentBox;
    JButton submit, cancel;
    String studentID; // A variable to hold the studentID

     //Constructor now takes the studentID from the 'View' screen
 
    UpdateStudent(String studentID) {
        this.studentID = studentID; // Store the ID

        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);

        //  Title 
        JLabel title = new JLabel("Update Student Details");
        title.setBounds(310, 30, 500, 50);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        add(title);

        //  Column 1 

        // Name
        JLabel labelName = new JLabel("<html>Name <font color='red'>*</font></html>");
        labelName.setBounds(50, 150, 100, 30);
        labelName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelName);

        textName = new JTextField();
        textName.setBounds(200, 150, 150, 30);
        add(textName);

        // Father's Name
        JLabel labelFather = new JLabel("<html>Father's Name <font color='red'>*</font></html>");
        labelFather.setBounds(50, 200, 150, 30);
        labelFather.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelFather);

        textFather = new JTextField();
        textFather.setBounds(200, 200, 150, 30);
        add(textFather);

        // Student ID
        JLabel labelID = new JLabel("Student ID");
        labelID.setBounds(50, 250, 100, 30);
        labelID.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelID);

        labelStudentID = new JLabel(studentID); // Set the ID
        labelStudentID.setBounds(200, 250, 150, 30);
        labelStudentID.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(labelStudentID);

        // Date of Birth
        JLabel labelDob = new JLabel("<html>Date of Birth <font color='red'>*</font></html>");
        labelDob.setBounds(50, 300, 150, 30); // Y is 300
        labelDob.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelDob);

        cdbob = new JDateChooser();
        cdbob.setBounds(200, 300, 150, 30); // Y is 300
        add(cdbob);

        // Class 10 %
        JLabel labelM10 = new JLabel("<html>Class 10 % <font color='red'>*</font></html>");
        labelM10.setBounds(50, 350, 150, 30); // Y is 350
        labelM10.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelM10);

        textM10 = new JTextField();
        textM10.setBounds(200, 350, 150, 30); // Y is 350
        add(textM10);

        // Aadhar No
        JLabel labelAadhar = new JLabel("<html>Aadhar No <font color='red'>*</font></html>");
        labelAadhar.setBounds(50, 400, 150, 30); // Y is 400
        labelAadhar.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelAadhar);

        textAadhar = new JTextField();
        textAadhar.setBounds(200, 400, 150, 30); // Y is 400
        add(textAadhar);

        // Course
        JLabel labelCourse = new JLabel("Course");
        labelCourse.setBounds(50, 450, 150, 30); // Y is 450
        labelCourse.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelCourse);

        String[] courses = {"B.Tech", "BBA", "BCA", "B.Sc", "M.Sc", "MBA", "MCA"};
        courseBox = new JComboBox<>(courses);
        courseBox.setBackground(Color.WHITE);
        courseBox.setBounds(200, 450, 150, 30); // Y is 450
        courseBox.addActionListener(this);
        add(courseBox);

        // Column 2

        // Address
        JLabel labelAddress = new JLabel("<html>Address <font color='red'>*</font></html>");
        labelAddress.setBounds(450, 150, 100, 30);
        labelAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelAddress);

        textAddress = new JTextField();
        textAddress.setBounds(600, 150, 150, 30);
        add(textAddress);

        // Phone
        JLabel labelPhone = new JLabel("<html>Phone <font color='red'>*</font></html>");
        labelPhone.setBounds(450, 200, 150, 30);
        labelPhone.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelPhone);

        textPhone = new JTextField();
        textPhone.setBounds(600, 200, 150, 30);
        add(textPhone);

        // Email
        JLabel labelEmail = new JLabel("<html>Email <font color='red'>*</font></html>");
        labelEmail.setBounds(450, 250, 100, 30);
        labelEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelEmail);

        textEmail = new JTextField();
        textEmail.setBounds(600, 250, 150, 30);
        add(textEmail);

        // Class 12 %
        JLabel labelM12 = new JLabel("<html>Class 12 % <font color='red'>*</font></html>");
        labelM12.setBounds(450, 350, 150, 30); // Y is 350
        labelM12.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelM12);

        textM12 = new JTextField();
        textM12.setBounds(600, 350, 150, 30); // Y is 350
        add(textM12);

        // Department
        labelDept = new JLabel("Department");
        labelDept.setBounds(450, 450, 150, 30); // Y is 450
        labelDept.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(labelDept);

        String[] departments = {"Computer Science", "IT", "AI-ML", "Data Science", "Cyber Security","IOT", "Electronics", "Mechanical", "Civil", "Bio Technology"};
        departmentBox = new JComboBox<>(departments);
        departmentBox.setBackground(Color.WHITE);
        departmentBox.setBounds(600, 450, 150, 30); // Y is 450
        add(departmentBox);

        //  Buttons 
        submit = new JButton("Update"); // Text changed to "Update"
        submit.setBounds(250, 550, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setOpaque(true);
        submit.setBorderPainted(false);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450, 550, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        cancel.addActionListener(this);
        add(cancel);

        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            String query = "SELECT * FROM student WHERE studentID = ?";
            pstmt = c.connection.prepareStatement(query);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Populate all fields with data from the database
                textName.setText(rs.getString("name"));
                textFather.setText(rs.getString("fatherName"));
                ((JTextField) cdbob.getDateEditor().getUiComponent()).setText(rs.getString("dob"));
                textAddress.setText(rs.getString("address"));
                textPhone.setText(rs.getString("phone"));
                textEmail.setText(rs.getString("email"));
                textM10.setText(rs.getString("m10"));
                textM12.setText(rs.getString("m12"));
                textAadhar.setText(rs.getString("aadhar"));
                courseBox.setSelectedItem(rs.getString("course"));
                departmentBox.setSelectedItem(rs.getString("department"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null && c.connection != null) c.connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Set initial state of department (must be after data load)
        if (!courseBox.getSelectedItem().equals("B.Tech")) {
            labelDept.setEnabled(false);
            departmentBox.setEnabled(false);
        }

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            
            // Get all data from fields (same as AddStudent)
            String name = textName.getText();
            String fatherName = textFather.getText();
            String dob = ((JTextField) cdbob.getDateEditor().getUiComponent()).getText();
            String address = textAddress.getText();
            String phone = textPhone.getText();
            String email = textEmail.getText();
            String m10 = textM10.getText();
            String m12 = textM12.getText();
            String aadhar = textAadhar.getText();
            String course = (String) courseBox.getSelectedItem();
            
            String department;
            if (departmentBox.isEnabled()) {
                department = (String) departmentBox.getSelectedItem();
            } else {
                department = "N/A";
            }

            // Validation (same as AddStudent)
            if (name.isEmpty() || fatherName.isEmpty() || dob.isEmpty() || address.isEmpty() || 
                phone.isEmpty() || email.isEmpty() || m10.isEmpty() || m12.isEmpty() || aadhar.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all mandatory fields (marked with *).");
                return;
            }
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Invalid Phone Number: Must be exactly 10 digits.");
                return;
            }
            if (!aadhar.matches("\\d{12}")) {
                JOptionPane.showMessageDialog(null, "Invalid Aadhar Number: Must be exactly 12 digits.");
                return;
            }
            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            if (!email.matches(emailRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid Email Format.");
                return;
            }
            
            
            String query = "UPDATE student SET name=?, fatherName=?, dob=?, address=?, phone=?, email=?, m10=?, m12=?, aadhar=?, course=?, department=? " +
                           "WHERE studentID=?";
            
            Conn c = null;
            PreparedStatement pstmt = null;

            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                
                // Set all the parameters for the query
                pstmt.setString(1, name);
                pstmt.setString(2, fatherName);
                pstmt.setString(3, dob);
                pstmt.setString(4, address);
                pstmt.setString(5, phone);
                pstmt.setString(6, email);
                pstmt.setString(7, m10);
                pstmt.setString(8, m12);
                pstmt.setString(9, aadhar);
                pstmt.setString(10, course);
                pstmt.setString(11, department);
                pstmt.setString(12, this.studentID); // This is the WHERE clause

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Student Details Updated Successfully");
                setVisible(false);
                // We don't need to show the ID, they already know it
                
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().contains("Duplicate entry")) {
                    JOptionPane.showMessageDialog(null, "Error: An entry with this Aadhar number already exists.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
            
        } else if (ae.getSource() == courseBox) {
            String selectedCourse = (String) courseBox.getSelectedItem();
            if (selectedCourse.equals("B.Tech")) {
                labelDept.setEnabled(true);
                departmentBox.setEnabled(true);
            } else {
                labelDept.setEnabled(false);
                departmentBox.setEnabled(false);
            }
        }
    }
}