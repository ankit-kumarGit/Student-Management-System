package student.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class About extends JFrame implements ActionListener {

    JButton back;

    About() {

        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Student Management System");
        title.setBounds(100, 20, 500, 40); // Box is centered ( (700-500)/2 = 100 )
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setHorizontalAlignment(SwingConstants.CENTER); // <-- THE FIX
        add(title);

        // --- Version ---
        JLabel version = new JLabel("Version 1.0 (2025)");
        version.setBounds(100, 70, 500, 20); // Same box, 500px wide
        version.setFont(new Font("Tahoma", Font.PLAIN, 16));
        version.setHorizontalAlignment(SwingConstants.CENTER); // <-- THE FIX
        add(version);
        
        // --- Credits ---
        JLabel developedBy = new JLabel("Developed by: Ankit, Shashwat, Tiya."); 
        developedBy.setBounds(100, 150, 500, 20); // Same box, 500px wide
        developedBy.setFont(new Font("Tahoma", Font.BOLD, 18));
        developedBy.setHorizontalAlignment(SwingConstants.CENTER); // <-- THE FIX
        add(developedBy);
        
        JLabel contact = new JLabel("Contact: connect.ankitkumar@gmail.com");
        contact.setBounds(100, 190, 500, 20); // Same box, 500px wide
        contact.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contact.setHorizontalAlignment(SwingConstants.CENTER); // <-- THE FIX
        add(contact);
        
        // --- Description ---
        JLabel desc = new JLabel("<html>This is a comprehensive desktop application built in Java Swing. " +
                                "It manages student records, fee payments, examination marks, and leave requests. " +
                                "The system features separate, secure portals for Admin and Student users.</html>");
        desc.setBounds(100, 250, 500, 100); // Same box, 500px wide
        desc.setFont(new Font("Tahoma", Font.PLAIN, 14));
        desc.setHorizontalAlignment(SwingConstants.CENTER); // <-- THE FIX
        add(desc);

        // --- Back Button ---
        back = new JButton("Back");
        back.setBounds(300, 400, 120, 30); // ( (700-120)/2 = 290, so 300 is fine)
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setOpaque(true);
        back.setBorderPainted(false);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new About();
    }
}