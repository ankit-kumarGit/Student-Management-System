package student.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import net.proteanit.sql.DbUtils; // From rs2xml.jar

public class ViewMyFees extends JFrame implements ActionListener {

    JTable table;
    JButton back;
    String studentID;

    ViewMyFees(String studentID) {
        this.studentID = studentID;

        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("My Payment History");
        title.setBounds(350, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(title);

        // The Table
        table = new JTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 900, 400);
        add(scrollPane);

        // Back Button
        back = new JButton("Back");
        back.setBounds(400, 520, 120, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setOpaque(true);
        back.setBorderPainted(false);
        back.addActionListener(this);
        add(back);

        //  Load Data into Table
        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = new Conn();
            // This query selects the payment history for *this* student
            // and renames the columns to be user-friendly.
            String query = "SELECT payment_date AS 'Payment Date', semester AS 'Semester', " +
                           "total_amount AS 'Total Fee', paid_amount AS 'Amount Paid' " +
                           "FROM student_fees WHERE studentID = ?";
                           
            pstmt = c.connection.prepareStatement(query);
            pstmt.setString(1, this.studentID);
            rs = pstmt.executeQuery();

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
        // Just for testing
        new ViewMyFees("1001"); 
    }
}