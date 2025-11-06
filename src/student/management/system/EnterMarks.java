package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class EnterMarks extends JFrame implements ActionListener {

    JComboBox<String> studentIDComboBox;
    JComboBox<String> semesterComboBox;
    JTextField tfSub1, tfSub2, tfSub3, tfSub4, tfSub5;
    JTextField tfMarks1, tfMarks2, tfMarks3, tfMarks4, tfMarks5;
    JButton submit, cancel;

    EnterMarks() {
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        //  Title
        JLabel title = new JLabel("Enter Student Marks");
        title.setBounds(350, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // Student ID Selection 
        JLabel labelID = new JLabel("Select Student (ID - Name)");
        labelID.setBounds(50, 70, 200, 20);
        labelID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelID);
        
        studentIDComboBox = new JComboBox<>();
        studentIDComboBox.setBounds(250, 70, 200, 20);
        studentIDComboBox.setBackground(Color.WHITE);
        add(studentIDComboBox);

        // Populate the JComboBox 
        Conn c1 = null;
        ResultSet rs = null;
        try {
            c1 = new Conn();
            rs = c1.statement.executeQuery("SELECT studentID, name FROM student");
            while (rs.next()) {
                studentIDComboBox.addItem(rs.getString("studentID") + " - " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (c1 != null && c1.connection != null) c1.connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Semester Selection 
        JLabel labelSemester = new JLabel("Select Semester");
        labelSemester.setBounds(50, 110, 200, 20);
        labelSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelSemester);
        
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        semesterComboBox = new JComboBox<>(semesters);
        semesterComboBox.setBounds(250, 110, 200, 20);
        semesterComboBox.setBackground(Color.WHITE);
        add(semesterComboBox);

        // Headers 
        JLabel subjectHeader = new JLabel("Subject Name");
        subjectHeader.setBounds(250, 150, 200, 20);
        subjectHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(subjectHeader);

        JLabel marksHeader = new JLabel("Marks Obtained");
        marksHeader.setBounds(500, 150, 200, 20);
        marksHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(marksHeader);

        //  Subject & Marks Fields
        tfSub1 = new JTextField();
        tfSub1.setBounds(250, 180, 200, 25);
        add(tfSub1);
        tfMarks1 = new JTextField();
        tfMarks1.setBounds(500, 180, 100, 25);
        add(tfMarks1);
        
        tfSub2 = new JTextField();
        tfSub2.setBounds(250, 210, 200, 25);
        add(tfSub2);
        tfMarks2 = new JTextField();
        tfMarks2.setBounds(500, 210, 100, 25);
        add(tfMarks2);
        
        tfSub3 = new JTextField();
        tfSub3.setBounds(250, 240, 200, 25);
        add(tfSub3);
        tfMarks3 = new JTextField();
        tfMarks3.setBounds(500, 240, 100, 25);
        add(tfMarks3);
        
        tfSub4 = new JTextField();
        tfSub4.setBounds(250, 270, 200, 25);
        add(tfSub4);
        tfMarks4 = new JTextField();
        tfMarks4.setBounds(500, 270, 100, 25);
        add(tfMarks4);
        
        tfSub5 = new JTextField();
        tfSub5.setBounds(250, 300, 200, 25);
        add(tfSub5);
        tfMarks5 = new JTextField();
        tfMarks5.setBounds(500, 300, 100, 25);
        add(tfMarks5);

        //  Buttons
        submit = new JButton("Submit");
        submit.setBounds(300, 380, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setOpaque(true);
        submit.setBorderPainted(false);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(450, 380, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String selectedItem = (String) studentIDComboBox.getSelectedItem();
            String studentID = selectedItem.split(" - ")[0];
            String semester = (String) semesterComboBox.getSelectedItem();
            
            String s1_name = tfSub1.getText();
            String s1_marks = tfMarks1.getText();
            String s2_name = tfSub2.getText();
            String s2_marks = tfMarks2.getText();
            String s3_name = tfSub3.getText();
            String s3_marks = tfMarks3.getText();
            String s4_name = tfSub4.getText();
            String s4_marks = tfMarks4.getText();
            String s5_name = tfSub5.getText();
            String s5_marks = tfMarks5.getText();

            // Simple validation
            if (s1_marks.isEmpty() || s2_marks.isEmpty() || s3_marks.isEmpty() || s4_marks.isEmpty() || s5_marks.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please enter marks for all 5 subjects.");
                 return;
            }

            String query = "INSERT INTO student_marks(studentID, semester, " +
                           "subject1_name, subject1_marks, " +
                           "subject2_name, subject2_marks, " +
                           "subject3_name, subject3_marks, " +
                           "subject4_name, subject4_marks, " +
                           "subject5_name, subject5_marks) " +
                           "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            Conn c2 = null;
            PreparedStatement pstmt = null;
            try {
                c2 = new Conn();
                pstmt = c2.connection.prepareStatement(query);
                
                pstmt.setString(1, studentID);
                pstmt.setString(2, semester);
                pstmt.setString(3, s1_name);
                pstmt.setString(4, s1_marks);
                pstmt.setString(5, s2_name);
                pstmt.setString(6, s2_marks);
                pstmt.setString(7, s3_name);
                pstmt.setString(8, s3_marks);
                pstmt.setString(9, s4_name);
                pstmt.setString(10, s4_marks);
                pstmt.setString(11, s5_name);
                pstmt.setString(12, s5_marks);
                
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Marks entered successfully.");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (c2 != null && c2.connection != null) c2.connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EnterMarks();
    }
}