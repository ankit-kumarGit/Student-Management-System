package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class ApplyLeave extends JFrame implements ActionListener {

    // The ComboBox is GONE.
    JLabel labelName;
    JDateChooser dateFrom, dateTo;
    JButton submit, cancel;
    String studentID; // Will store the logged-in student's ID

    /**
     * The constructor now takes the student's info from the dashboard.
     */
    ApplyLeave(String studentID, String studentName) {
        this.studentID = studentID; // Store the ID

        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // --- Title ---
        JLabel title = new JLabel("Apply for Leave");
        title.setBounds(150, 30, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // --- Student Name Display ---
        JLabel labelID = new JLabel("Student Name");
        labelID.setBounds(60, 100, 150, 20);
        labelID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelID);
        
        // This replaces the dropdown
        labelName = new JLabel(studentName); 
        labelName.setBounds(60, 130, 350, 30);
        labelName.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(labelName);

        // --- From Date ---
        JLabel labelFromDate = new JLabel("From Date");
        labelFromDate.setBounds(60, 180, 150, 20);
        labelFromDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelFromDate);

        dateFrom = new JDateChooser();
        dateFrom.setBounds(60, 210, 350, 30);
        add(dateFrom);
        
        // --- To Date ---
        JLabel labelToDate = new JLabel("To Date");
        labelToDate.setBounds(60, 260, 150, 20);
        labelToDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelToDate);

        dateTo = new JDateChooser();
        dateTo.setBounds(60, 290, 350, 30);
        add(dateTo);

        // --- Buttons ---
        submit = new JButton("Submit");
        submit.setBounds(100, 400, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setOpaque(true);
        submit.setBorderPainted(false);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 400, 120, 30);
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
            
            // It now gets the ID from the class variable, not a dropdown
            String fromDate = ((JTextField) dateFrom.getDateEditor().getUiComponent()).getText();
            String toDate = ((JTextField) dateTo.getDateEditor().getUiComponent()).getText();
            
            if (fromDate.isEmpty() || toDate.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please select both a 'From' and 'To' date.");
                 return;
            }

            String query = "INSERT INTO student_leave(studentID, from_date, to_date) VALUES(?, ?, ?)";
            
            Conn c = null;
            PreparedStatement pstmt = null;
            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                
                pstmt.setString(1, this.studentID); // <-- Uses the stored ID
                pstmt.setString(2, fromDate);
                pstmt.setString(3, toDate);
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Leave application submitted successfully.");
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

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        // Just for testing
        new ApplyLeave("1001", "Test Student");
    }
}