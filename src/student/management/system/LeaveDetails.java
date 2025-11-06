package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import net.proteanit.sql.DbUtils; // From rs2xml.jar

public class LeaveDetails extends JFrame implements ActionListener {

    JTable table;
    JButton approve, deny, back;

    LeaveDetails() {
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        //  Title 
        JLabel title = new JLabel("Student Leave Applications");
        title.setBounds(350, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // The Table
        table = new JTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1000, 500);
        add(scrollPane);

        // Buttons
        approve = new JButton("Approve");
        approve.setBounds(250, 620, 120, 30);
        approve.setBackground(Color.BLACK);
        approve.setForeground(Color.WHITE);
        approve.setOpaque(true);
        approve.setBorderPainted(false);
        approve.addActionListener(this);
        add(approve);

        deny = new JButton("Deny");
        deny.setBounds(400, 620, 120, 30);
        deny.setBackground(Color.BLACK);
        deny.setForeground(Color.WHITE);
        deny.setOpaque(true);
        deny.setBorderPainted(false);
        deny.addActionListener(this);
        add(deny);

        back = new JButton("Back");
        back.setBounds(550, 620, 120, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setOpaque(true);
        back.setBorderPainted(false);
        back.addActionListener(this);
        add(back);

        // Load Data into Table
        loadTableData();

        setVisible(true);
    }
    
  
    private void loadTableData() {
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            // A "JOIN" query to get the student's name, not just their ID
            String query = "SELECT l.leaveID, l.studentID, s.name, l.from_date, l.to_date, l.status " +
                           "FROM student_leave l JOIN student s ON l.studentID = s.studentID";
            pstmt = c.connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            // Use DbUtils to populate the table
            table.setModel(DbUtils.resultSetToTableModel(rs));

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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == approve || ae.getSource() == deny) {
            
            // 1. Get the selected row from the table
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a leave request from the table first.");
                return;
            }
            
            // 2. Get the leaveID from the first column of the selected row
            String leaveID = table.getValueAt(selectedRow, 0).toString();
            
            // 3. Determine if we are approving or denying
            String newStatus = (ae.getSource() == approve) ? "Approved" : "Denied";
            String actionVerb = (ae.getSource() == approve) ? "approve" : "deny";
            
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + actionVerb + " this leave (ID: " + leaveID + ")?", "Confirm Action", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                String query = "UPDATE student_leave SET status = ? WHERE leaveID = ?";
                
                Conn c = null;
                PreparedStatement pstmt = null;
                try {
                    c = new Conn();
                    pstmt = c.connection.prepareStatement(query);
                    pstmt.setString(1, newStatus);
                    pstmt.setString(2, leaveID);
                    pstmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Leave " + newStatus + " successfully.");
                    
                    // 4. Refresh the table data
                    loadTableData();
                    
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
            }
            
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new LeaveDetails();
    }
}