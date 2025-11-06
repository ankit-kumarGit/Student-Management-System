package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import net.proteanit.sql.DbUtils; // From rs2xml.jar

public class FeeStructure extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    FeeStructure() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        //  Title
        JLabel title = new JLabel("University Fee Structure");
        title.setBounds(250, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // The Table
        table = new JTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 800, 400);
        add(scrollPane);

        // Back Button 
        back = new JButton("Back");
        back.setBounds(340, 520, 120, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setOpaque(true);
        back.setBorderPainted(false);
        back.addActionListener(this);
        add(back);

        // Load Data into Table
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            // Let's make the column headers
            String query = "SELECT course_name AS 'Course', total_fee AS 'Total Fee (per year)' FROM course_fees";
            pstmt = c.connection.prepareStatement(query);
            rs = pstmt.executeQuery();

            // from rs2xml.jar
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

        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new FeeStructure(); 
    }
}