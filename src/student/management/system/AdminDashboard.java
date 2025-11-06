package student.management.system;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

// Class name is AdminDashboard
public class AdminDashboard extends JFrame implements ActionListener {
    
    //
    JMenuItem newStudentInfo, viewStudentDetails, leaveStudentD,
              examDetails, examMarks, updateStudentInfo, feeStructure,updateFeeStructure, feeForm,
              calculator, notepad, aboutItem, logOutItem, exitAppItem;

    AdminDashboard(){
        setTitle("Admin Dashboard - Student Management System"); 
        
        // Image Setup 
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back1.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(1500, 850, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        add(img);

        // Menu Bar 
        JMenuBar mb = new JMenuBar();

        // Menu 1: New Information
        JMenu newInfo = new JMenu("New Information");
        newInfo.setForeground(Color.BLUE);
        mb.add(newInfo);
        
        newStudentInfo = new JMenuItem("New Student Information");
        newStudentInfo.addActionListener(this);
        newInfo.add(newStudentInfo);

        // Menu 2: View Details 
        JMenu details = new JMenu("View Details");
        details.setForeground(Color.RED);
        mb.add(details);
        
        viewStudentDetails = new JMenuItem("View Student Details");
        viewStudentDetails.addActionListener(this);
        details.add(viewStudentDetails);

        // Menu 4: Approve Leave
        JMenu leaveDetails = new JMenu("Approve Leave");
        leaveDetails.setForeground(Color.RED);
        mb.add(leaveDetails);
        
        leaveStudentD = new JMenuItem("Approve Student Leave");
        leaveStudentD.addActionListener(this);
        leaveDetails.add(leaveStudentD);

        // Menu 5: Exams 
        JMenu exams = new JMenu("Examination");
        exams.setForeground(Color.BLUE);
        mb.add(exams);
        
        examDetails = new JMenuItem("Examination Results");
        examDetails.addActionListener(this);
        exams.add(examDetails);

        examMarks = new JMenuItem("Enter Marks");
        examMarks.addActionListener(this);
        exams.add(examMarks); // <-- TYPO "s." FIXED

        // Menu 6: Update Info 
        JMenu updateInfo = new JMenu("Update Details");
        updateInfo.setForeground(Color.RED);
        mb.add(updateInfo);
        
        updateStudentInfo = new JMenuItem("Update Student Details");
        updateStudentInfo.addActionListener(this);
        updateInfo.add(updateStudentInfo);

        //  Menu 7: Fees 
        JMenu fee = new JMenu("Fee Details");
        fee.setForeground(Color.BLUE);
        mb.add(fee);
        
        feeStructure = new JMenuItem("Fee Structure");
        feeStructure.addActionListener(this);
        fee.add(feeStructure);

        updateFeeStructure = new JMenuItem("Update Fee Structure");
        updateFeeStructure.addActionListener(this);
        fee.add(updateFeeStructure);

        feeForm = new JMenuItem("Student Fee Form");
        feeForm.addActionListener(this);
        fee.add(feeForm);

        //Menu 8: Utilities 
        JMenu utility = new JMenu("Utility");
        utility.setForeground(Color.RED);
        mb.add(utility);

        calculator = new JMenuItem("Calculator");
        calculator.addActionListener(this);
        utility.add(calculator);
        
        notepad = new JMenuItem("Notepad");
        notepad.addActionListener(this);
        utility.add(notepad);

        //Menu 9: About 
        JMenu about = new JMenu("About");
        about.setForeground(Color.BLUE);
        mb.add(about);
        
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        about.add(aboutItem);

        // Menu 10: Exit
        JMenu exit = new JMenu("Exit");
        exit.setForeground(Color.RED);
        mb.add(exit);
        
        logOutItem = new JMenuItem("Log Out (Back to Login)");
        logOutItem.addActionListener(this);
        exit.add(logOutItem);
        
        exit.add(new JSeparator()); // Adds a nice horizontal line
        
        exitAppItem = new JMenuItem("Exit Application");
        exitAppItem.addActionListener(this);
        exit.add(exitAppItem);

        // Finalize Frame
        setJMenuBar(mb);
        setSize(1540, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isMac = osName.contains("mac");
        boolean isWindows = osName.contains("win");
        
        if (e.getSource() == newStudentInfo) {
            new AddStudent(); 
        } else if (e.getSource() == viewStudentDetails) {
            new ViewStudentDetails(); 
            
        } else if (e.getSource() == leaveStudentD) {
            System.out.println("Opening Admin Leave Approval window...");
            new LeaveDetails(); 
            
        } else if (e.getSource() == examDetails) {
            System.out.println("Opening Exam Results window...");
            new ViewStudentDetails();
        } else if (e.getSource() == examMarks) {
            System.out.println("Opening Enter Marks window...");
            new EnterMarks(); 
        } else if (e.getSource() == updateStudentInfo) {
            new ViewStudentDetails(); 
        } else if (e.getSource() == feeStructure) {
            System.out.println("Opening Fee Structure window...");
            new FeeStructure(); 
        }else if (e.getSource() == updateFeeStructure) {
            new UpdateFeeStructure();
        } else if (e.getSource() == feeForm) {
            System.out.println("Opening Fee Form window...");
            new StudentFeeForm(); 

        // Utilities
        } else if (e.getSource() == calculator) {
            try {
                if (isWindows) {
                    new ProcessBuilder("calc.exe").start();
                } else if (isMac) {
                    new ProcessBuilder("open", "-a", "Calculator").start();
                }
            } catch (IOException ex) { 
                ex.printStackTrace(); 
            }
        } else if (e.getSource() == notepad) {
            try {
                if (isWindows) {
                    new ProcessBuilder("notepad.exe").start();
                } else if (isMac) {
                    new ProcessBuilder("open", "-a", "TextEdit").start();
                }
            } catch (IOException ex) { 
                ex.printStackTrace(); 
            }

        // About/Exit 
        } else if (e.getSource() == aboutItem) {
            new About();

        } else if (e.getSource() == logOutItem) {
            // Log out
            setVisible(false);
            new Login();
            
        } else if (e.getSource() == exitAppItem) {
            // Exit
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}