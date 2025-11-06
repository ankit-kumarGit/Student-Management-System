package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class StudentFeeForm extends JFrame implements ActionListener {

    JComboBox<String> studentIDComboBox;
    JLabel labelCourse, labelTotal;
    JComboBox<String> semesterComboBox;
    JTextField tfPaidAmount;
    JDateChooser dateChooser;
    JButton payButton, backButton;

    StudentFeeForm() {
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Title 
        JLabel title = new JLabel("Student Fee Payment");
        title.setBounds(180, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // Student ID Selection 
        JLabel labelID = new JLabel("Select Student (ID - Name)");
        labelID.setBounds(50, 80, 200, 20);
        labelID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelID);
        
        studentIDComboBox = new JComboBox<>();
        studentIDComboBox.setBounds(270, 80, 200, 20);
        studentIDComboBox.setBackground(Color.WHITE);
        add(studentIDComboBox);
        
        // This listener will auto-fill the course 
        studentIDComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    updateCourseAndTotal();
                }
            }
        });

        // Course 
        JLabel labelCourseLabel = new JLabel("Course");
        labelCourseLabel.setBounds(50, 120, 200, 20);
        labelCourseLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelCourseLabel);
        
        labelCourse = new JLabel(); // This will be auto-filled
        labelCourse.setBounds(270, 120, 200, 20);
        labelCourse.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(labelCourse);
        
        // Semester Selection 
        JLabel labelSemester = new JLabel("Select Semester");
        labelSemester.setBounds(50, 160, 200, 20);
        labelSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelSemester);
        
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        semesterComboBox = new JComboBox<>(semesters);
        semesterComboBox.setBounds(270, 160, 200, 20);
        semesterComboBox.setBackground(Color.WHITE);
        add(semesterComboBox);

        //Total Amount
        JLabel labelTotalLabel = new JLabel("Total Amount");
        labelTotalLabel.setBounds(50, 200, 200, 20);
        labelTotalLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelTotalLabel);
        
        labelTotal = new JLabel(); // This will be auto-filled
        labelTotal.setBounds(270, 200, 200, 20);
        labelTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        labelTotal.setForeground(Color.RED);
        add(labelTotal);

        // Paid Amount
        JLabel labelPaid = new JLabel("Amount Paid");
        labelPaid.setBounds(50, 240, 200, 20);
        labelPaid.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelPaid);
        
        tfPaidAmount = new JTextField();
        tfPaidAmount.setBounds(270, 240, 200, 20);
        add(tfPaidAmount);
        
        // Payment Date 
        JLabel labelDate = new JLabel("Payment Date");
        labelDate.setBounds(50, 280, 200, 20);
        labelDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelDate);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(270, 280, 200, 20);
        add(dateChooser);

        //  Buttons 
        payButton = new JButton("Submit Payment");
        payButton.setBounds(150, 360, 150, 30);
        payButton.setBackground(Color.BLACK);
        payButton.setForeground(Color.WHITE);
        payButton.setOpaque(true);
        payButton.setBorderPainted(false);
        payButton.addActionListener(this);
        add(payButton);

        backButton = new JButton("Back");
        backButton.setBounds(330, 360, 120, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.addActionListener(this);
        add(backButton);

        //  Populate the Student ComboBox 
        populateStudentIDComboBox();

        setVisible(true);
    }
    
    private void populateStudentIDComboBox() {
        Conn c = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            rs = c.statement.executeQuery("SELECT studentID, name FROM student");
            while (rs.next()) {
                studentIDComboBox.addItem(rs.getString("studentID") + " - " + rs.getString("name"));
            }
            // After populating, update the fields for the first item
            updateCourseAndTotal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (c != null && c.connection != null) c.connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    

    private void updateCourseAndTotal() {
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectedItem = (String) studentIDComboBox.getSelectedItem();
            // Add a check in case the box is empty
            if (selectedItem == null) {
                labelCourse.setText("N/A");
                labelTotal.setText("0");
                return;
            }
            
            String studentID = selectedItem.split(" - ")[0];

            c = new Conn();
            
            // This query joins the student table and fee table
            // to get the course and its fee in one shot.
            String query = "SELECT s.course, f.total_fee " +
                        "FROM student s " +
                        "LEFT JOIN course_fees f ON s.course = f.course_name " +
                        "WHERE s.studentID = ?";
                        
            pstmt = c.connection.prepareStatement(query);
            pstmt.setString(1, studentID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String course = rs.getString("course");
                String total = rs.getString("total_fee");
                
                labelCourse.setText(course);
                
                // If the fee is not in the table (null), set it to 0
                if (total == null) {
                    labelTotal.setText("0");
                } else {
                    labelTotal.setText(total);
                }
                
            } else {
                // This student's course isn't in the fees table
                labelCourse.setText("N/A");
                labelTotal.setText("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Manually close all resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null && c.connection != null) c.connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == payButton) {
            String selectedItem = (String) studentIDComboBox.getSelectedItem();
            String studentID = selectedItem.split(" - ")[0];
            String course = labelCourse.getText();
            String semester = (String) semesterComboBox.getSelectedItem();
            String totalAmount = labelTotal.getText();
            String paidAmount = tfPaidAmount.getText();
            String paymentDate = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();

            // Validation
            if (paidAmount.isEmpty() || paymentDate.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please enter amount paid and select a date.");
                 return;
            }
            if (!paidAmount.matches("\\d+")) { // Simple check for digits only
                 JOptionPane.showMessageDialog(null, "Amount must be a number.");
                 return;
            }

            String query = "INSERT INTO student_fees(studentID, course, semester, total_amount, paid_amount, payment_date) " +
                           "VALUES(?, ?, ?, ?, ?, ?)";
            
            Conn c = null;
            PreparedStatement pstmt = null;
            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, studentID);
                pstmt.setString(2, course);
                pstmt.setString(3, semester);
                pstmt.setString(4, totalAmount);
                pstmt.setString(5, paidAmount);
                pstmt.setString(6, paymentDate);
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Fee payment recorded successfully.");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (ae.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}