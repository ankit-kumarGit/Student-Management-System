package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class UpdateFeeStructure extends JFrame implements ActionListener, ItemListener {

    JComboBox<String> courseComboBox;
    JLabel labelCurrentFee;
    JTextField tfNewFee;
    JButton updateButton, backButton;

    UpdateFeeStructure() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        //  Title 
        JLabel title = new JLabel("Update Course Fees");
        title.setBounds(120, 30, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        //  Course Selection 
        JLabel labelCourse = new JLabel("Select Course");
        labelCourse.setBounds(60, 100, 150, 20);
        labelCourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelCourse);
        
        courseComboBox = new JComboBox<>();
        courseComboBox.setBounds(220, 100, 200, 20);
        courseComboBox.setBackground(Color.WHITE);
        courseComboBox.addItemListener(this); // Add listener to update fee
        add(courseComboBox);

        // Current Fee
        JLabel labelCurrent = new JLabel("Current Fee");
        labelCurrent.setBounds(60, 150, 150, 20);
        labelCurrent.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelCurrent);
        
        labelCurrentFee = new JLabel(); // This will be auto-filled
        labelCurrentFee.setBounds(220, 150, 200, 20);
        labelCurrentFee.setFont(new Font("Tahoma", Font.BOLD, 16));
        labelCurrentFee.setForeground(Color.RED);
        add(labelCurrentFee);
        
        //  New Fee
        JLabel labelNew = new JLabel("Enter New Fee");
        labelNew.setBounds(60, 200, 150, 20);
        labelNew.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(labelNew);
        
        tfNewFee = new JTextField();
        tfNewFee.setBounds(220, 200, 200, 20);
        add(tfNewFee);

        //  Buttons
        updateButton = new JButton("Update");
        updateButton.setBounds(100, 280, 120, 30);
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.WHITE);
        updateButton.setOpaque(true);
        updateButton.setBorderPainted(false);
        updateButton.addActionListener(this);
        add(updateButton);

        backButton = new JButton("Back");
        backButton.setBounds(250, 280, 120, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.addActionListener(this);
        add(backButton);

        // Populate the ComboBox and initial fee 
        populateCourseComboBox();

        setVisible(true);
    }
    
    private void populateCourseComboBox() {
        Conn c = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            rs = c.statement.executeQuery("SELECT course_name FROM course_fees");
            while (rs.next()) {
                courseComboBox.addItem(rs.getString("course_name"));
            }
            // After populating, update the fee label for the first item
            updateCurrentFeeLabel();
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
    
    private void updateCurrentFeeLabel() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        if (selectedCourse == null) return;
        
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            String query = "SELECT total_fee FROM course_fees WHERE course_name = ?";
            pstmt = c.connection.prepareStatement(query);
            pstmt.setString(1, selectedCourse);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                labelCurrentFee.setText(rs.getString("total_fee"));
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
    }
    
    // This handles the ComboBox selection change
    @Override
    public void itemStateChanged(ItemEvent ie) {
        if (ie.getStateChange() == ItemEvent.SELECTED) {
            updateCurrentFeeLabel();
        }
    }

    // This handles the button clicks
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateButton) {
            String course = (String) courseComboBox.getSelectedItem();
            String newFee = tfNewFee.getText();
            
            // Validation
            if (newFee.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please enter a new fee amount.");
                 return;
            }
            if (!newFee.matches("\\d+")) { // Check for digits only
                 JOptionPane.showMessageDialog(null, "Fee must be a valid number (e.g., 120000).");
                 return;
            }

            String query = "UPDATE course_fees SET total_fee = ? WHERE course_name = ?";
            
            Conn c = null;
            PreparedStatement pstmt = null;
            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, newFee);
                pstmt.setString(2, course);
                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Fee for " + course + " updated successfully to " + newFee);
                
                // Refresh the "Current Fee" label
                updateCurrentFeeLabel();
                tfNewFee.setText(""); // Clear the text field

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
        new UpdateFeeStructure();
    }
}