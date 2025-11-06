package student.management.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Login extends JFrame implements ActionListener {

    JTextField textFieldName;
    JPasswordField passwordField;
    JComboBox<String> roleSelector;
    JButton login, back;
    JLabel labelName;
    JLabel labelpass;

    Login() {
        setTitle("Student Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel imagePanel = new JPanel();
        JPanel formPanel = new JPanel();

        setLayout(new BorderLayout());

        //1. Setup the Image Panel
        imagePanel.setBackground(new Color(240, 240, 240)); 
        imagePanel.setPreferredSize(new Dimension(250, 350));
        imagePanel.setLayout(new BorderLayout());
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/second.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imgLabel = new JLabel(i3);
        
        imagePanel.add(imgLabel, BorderLayout.CENTER);

        // 2. Setup the Form Panel 
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Title Label 
        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(title, gbc);

        // Role Selector 
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelRole = new JLabel("Login As:");
        formPanel.add(labelRole, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        String[] roles = {"Admin", "Student"};
        roleSelector = new JComboBox<>(roles);
        roleSelector.addActionListener(this);
        formPanel.add(roleSelector, gbc);
        
        //  Username/ID Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        labelName = new JLabel("Username:");
        formPanel.add(labelName, gbc);

        // Username Text Field 
        gbc.gridx = 1;
        gbc.gridy = 2;
        textFieldName = new JTextField();
        textFieldName.setPreferredSize(new Dimension(200, 25));
        formPanel.add(textFieldName, gbc);

        // Password/Aadhar Label 
        gbc.gridx = 0;
        gbc.gridy = 3;
        labelpass = new JLabel("Password:");
        formPanel.add(labelpass, gbc);

        // Password Field 
        gbc.gridx = 1;
        gbc.gridy = 3;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        formPanel.add(passwordField, gbc);

        // Button Panel (for Login + Back buttons) 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT)); 
        
        login = createStyledButton("Login");
        login.addActionListener(this);
        buttonPanel.add(login);

        back = createStyledButton("Back");
        back.addActionListener(this);
        buttonPanel.add(back);

        // Add button panel to form
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(buttonPanel, gbc);

        //  3. Add panels to JFrame 
        add(imagePanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        // 4. Finalize the Frame 
        pack(); 
        setLocationRelativeTo(null);
        setResizable(false);
        setMinimumSize(getSize()); // ✅ prevent shrinking issue
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        return button;
    }

    // ACTIONPERFORMED METHOD (Handles all events)
    // In Login.java
@Override
public void actionPerformed(ActionEvent e) {
    
    // Handle role selection change
    if (e.getSource() == roleSelector) {
        String role = (String) roleSelector.getSelectedItem();
        if (role.equals("Admin")) {
            labelName.setText("Username:");
            labelpass.setText("Password:");
        } else if (role.equals("Student")) {
            labelName.setText("Student ID:");
            labelpass.setText("Aadhar Number:");
        }
        SwingUtilities.invokeLater(() -> {
            pack();
            revalidate();
            repaint();
        });
        return;
    }

    // Handle Login button click
    if (e.getSource() == login) {
        
        String role = (String) roleSelector.getSelectedItem();
        String username = textFieldName.getText(); // This is the studentID
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        if (role.equals("Admin")) {
            // ADMIN LOGIN 
            String query = "select * from login where username = ? and password = ?";
            Conn c = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    setVisible(false);
                    new AdminDashboard(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Admin Username or Password");
                }
            } catch (Exception E) {
                E.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception ex) { ex.printStackTrace(); }
            }

        } else if (role.equals("Student")) {
            
            String query = "select * from student where studentID = ? and aadhar = ?";
            
            Conn c = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                c = new Conn();
                pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, username); // username is the studentID
                pstmt.setString(2, password); // password is the aadhar
                rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    // 1. We found the student, now get their name
                    String studentName = rs.getString("name"); 
                    
                    setVisible(false);
                    
                    // 2. Pass BOTH the ID (username) and the Name
                    new StudentDashboard(username, studentName); 
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Student ID or Aadhar Number");
                }
            } catch (Exception E) {
                E.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception ex) { ex.printStackTrace(); }
            }
           
        }
        java.util.Arrays.fill(passwordChars, ' ');

    //  Handle Back button click 
    } else if (e.getSource() == back) {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the application?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
