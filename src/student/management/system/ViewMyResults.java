package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class ViewMyResults extends JFrame implements ActionListener {

    String studentID;
    JComboBox<String> semesterComboBox;
    JButton searchButton, backButton;
    
    // Labels to display the marks
    JLabel lblSub1, lblSub2, lblSub3, lblSub4, lblSub5;
    JLabel lblMark1, lblMark2, lblMark3, lblMark4, lblMark5;

    ViewMyResults(String studentID) {
        this.studentID = studentID;

        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setTitle("View My Results - " + studentID);

        // --- Title ---
        JLabel title = new JLabel("View My Results");
        title.setBounds(180, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // --- Semester Selection ---
        JLabel labelSemester = new JLabel("Select Semester");
        labelSemester.setBounds(50, 80, 150, 20);
        labelSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelSemester);
        
        semesterComboBox = new JComboBox<>();
        semesterComboBox.setBounds(200, 80, 200, 20);
        semesterComboBox.setBackground(Color.WHITE);
        add(semesterComboBox);
        
        // --- Search Button ---
        searchButton = new JButton("Search");
        searchButton.setBounds(420, 80, 100, 20);
        searchButton.addActionListener(this);
        add(searchButton);

        // --- Headers ---
        JLabel subjectHeader = new JLabel("Subject");
        subjectHeader.setBounds(100, 150, 200, 20);
        subjectHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(subjectHeader);

        JLabel marksHeader = new JLabel("Marks");
        marksHeader.setBounds(350, 150, 200, 20);
        marksHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(marksHeader);

        // --- Result Display Area (JLabels) ---
        // We create them empty first
        lblSub1 = new JLabel(); lblSub1.setBounds(100, 180, 200, 25); add(lblSub1);
        lblMark1 = new JLabel(); lblMark1.setBounds(350, 180, 100, 25); add(lblMark1);
        
        lblSub2 = new JLabel(); lblSub2.setBounds(100, 210, 200, 25); add(lblSub2);
        lblMark2 = new JLabel(); lblMark2.setBounds(350, 210, 100, 25); add(lblMark2);
        
        lblSub3 = new JLabel(); lblSub3.setBounds(100, 240, 200, 25); add(lblSub3);
        lblMark3 = new JLabel(); lblMark3.setBounds(350, 240, 100, 25); add(lblMark3);
        
        lblSub4 = new JLabel(); lblSub4.setBounds(100, 270, 200, 25); add(lblSub4);
        lblMark4 = new JLabel(); lblMark4.setBounds(350, 270, 100, 25); add(lblMark4);
        
        lblSub5 = new JLabel(); lblSub5.setBounds(100, 300, 200, 25); add(lblSub5);
        lblMark5 = new JLabel(); lblMark5.setBounds(350, 300, 100, 25); add(lblMark5);

        // --- Back Button ---
        backButton = new JButton("Back");
        backButton.setBounds(240, 400, 120, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.addActionListener(this);
        add(backButton);

        // --- Populate the Semester ComboBox ---
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            String query = "SELECT DISTINCT semester FROM student_marks WHERE studentID = ?";
            pstmt = c.connection.prepareStatement(query);
            pstmt.setString(1, this.studentID);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                semesterComboBox.addItem(rs.getString("semester"));
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

        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {
            // --- User clicked "Search", so let's get the marks ---
            String selectedSemester = (String) semesterComboBox.getSelectedItem();
            
            if (selectedSemester == null) {
                JOptionPane.showMessageDialog(null, "You have no results to display.");
                return;
            }
            
            Conn c = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                c = new Conn();
                String query = "SELECT * FROM student_marks WHERE studentID = ? AND semester = ?";
                pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, this.studentID);
                pstmt.setString(2, selectedSemester);
                rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    // Populate the labels with the data
                    lblSub1.setText(rs.getString("subject1_name"));
                    lblMark1.setText(rs.getString("subject1_marks"));
                    
                    lblSub2.setText(rs.getString("subject2_name"));
                    lblMark2.setText(rs.getString("subject2_marks"));
                    
                    lblSub3.setText(rs.getString("subject3_name"));
                    lblMark3.setText(rs.getString("subject3_marks"));
                    
                    lblSub4.setText(rs.getString("subject4_name"));
                    lblMark4.setText(rs.getString("subject4_marks"));
                    
                    lblSub5.setText(rs.getString("subject5_name"));
                    lblMark5.setText(rs.getString("subject5_marks"));
                } else {
                    JOptionPane.showMessageDialog(null, "No results found for that semester.");
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

        } else if (ae.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        // Just for testing
        new ViewMyResults("1001"); 
    }
}