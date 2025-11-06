package student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils; // <-- This is from rs2xml.jar

public class ViewStudentDetails extends JFrame implements ActionListener {

    JComboBox<String> studentIDComboBox;
    JTable table;
    JButton search, print, update, delete, back, viewResults;

    ViewStudentDetails() {
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- Search Bar ---
        JLabel heading = new JLabel("Search by Student ID:");
        heading.setBounds(20, 20, 150, 20);
        add(heading);

        studentIDComboBox = new JComboBox<>();
        studentIDComboBox.setBounds(180, 20, 150, 20);
        studentIDComboBox.setBackground(Color.WHITE);
        add(studentIDComboBox);

        // --- Populate the JComboBox with all Student IDs ---
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                studentIDComboBox.addItem(rs.getString("studentID"));
            }
            rs.close();
            c.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Buttons ---
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 20);
        update.addActionListener(this);
        add(update);

        delete = new JButton("Delete");
        delete.setBounds(320, 70, 80, 20);
        delete.addActionListener(this);
        add(delete);
        
        // --- NEW BUTTON ---
        viewResults = new JButton("View Results");
        viewResults.setBounds(420, 70, 110, 20); // <-- NEW
        viewResults.addActionListener(this);
        add(viewResults);

        back = new JButton("Back");
        back.setBounds(550, 70, 80, 20); // <-- SHIFTED
        back.addActionListener(this);
        add(back);

        // --- The Table ---
        table = new JTable();
        
        // This JScrollPane is what makes the table headers visible and scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1000, 600);
        add(scrollPane);

        // --- Load ALL student data into the table on startup ---
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM student");
            
            // This is the magic line from rs2xml.jar
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            rs.close();
            c.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String selectedID = (String) studentIDComboBox.getSelectedItem();

        if (ae.getSource() == search) {
            // --- Search for a SPECIFIC student ---
            String query = "SELECT * FROM student WHERE studentID = ?";
            
            // --- FIXED: Replaced try-with-resources ---
            Conn c = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, selectedID);
                rs = pstmt.executeQuery();
                
                // Update the table with just the search results
                table.setModel(DbUtils.resultSetToTableModel(rs));
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Manually close everything
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (ae.getSource() == print) {
            // --- Print the table ---
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (ae.getSource() == update) {
            // --- Open the Update window ---
            System.out.println("Opening Update window for ID: " + selectedID);
            new UpdateStudent(selectedID); 

        } else if (ae.getSource() == viewResults) {
            // Open the student's result screen for the selected ID
            new ViewMyResults(selectedID);

        } else if (ae.getSource() == delete) {
            // --- Delete a student ---
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete student ID " + selectedID + "?", "Delete", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM student WHERE studentID = ?";
                
                // --- FIXED: Replaced try-with-resources ---
                Conn c = null;
                PreparedStatement pstmt = null;
                try {
                    c = new Conn();
                    pstmt = c.connection.prepareStatement(query);
                    pstmt.setString(1, selectedID);
                    pstmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Student ID " + selectedID + " deleted successfully.");
                    
                    // Refresh the view
                    setVisible(false);
                    new ViewStudentDetails();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                } finally {
                    // Manually close everything
                    try {
                        if (pstmt != null) pstmt.close();
                        if (c != null && c.connection != null) c.connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ViewStudentDetails();
    }
}