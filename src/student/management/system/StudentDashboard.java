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

public class StudentDashboard extends JFrame implements ActionListener {

    String studentID; 
    String studentName;

    // Menu Items 
    JMenuItem applyLeave, viewLeave, viewResults, 
              updateMyInfo, feeStructure, viewMyFees,
              calculator, notepad, aboutItem, 
              logOutItem, exitAppItem; 

    StudentDashboard(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;

        setTitle("Student Dashboard - " + this.studentName);
        setSize(1540, 850);
        setLocationRelativeTo(null);

        // Background Image 
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back1.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(1500, 850, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        add(img);

        // Menu Bar
        JMenuBar mb = new JMenuBar();

        // Menu 1: My Profile
        JMenu updateInfo = new JMenu("My Profile");
        updateInfo.setForeground(Color.BLUE);
        mb.add(updateInfo);
        
        updateMyInfo = new JMenuItem("Update My Details");
        updateMyInfo.addActionListener(this);
        updateInfo.add(updateMyInfo);

        // Menu 2: Leave 
        JMenu leave = new JMenu("Leave");
        leave.setForeground(Color.RED);
        mb.add(leave);
        
        applyLeave = new JMenuItem("Apply for Leave");
        applyLeave.addActionListener(this);
        leave.add(applyLeave);

        viewLeave = new JMenuItem("View My Leave Status");
        viewLeave.addActionListener(this);
        leave.add(viewLeave);

        //  Menu 3: Examination 
        JMenu exams = new JMenu("Examination");
        exams.setForeground(Color.BLUE);
        mb.add(exams);
        
        viewResults = new JMenuItem("View My Results");
        viewResults.addActionListener(this);
        exams.add(viewResults);

        //  Menu 4: Fees 
        JMenu fee = new JMenu("Fee Details");
        fee.setForeground(Color.RED);
        mb.add(fee);
        
        feeStructure = new JMenuItem("View Fee Structure");
        feeStructure.addActionListener(this);
        fee.add(feeStructure);

        viewMyFees = new JMenuItem("View My Payment History"); 
        viewMyFees.addActionListener(this);
        fee.add(viewMyFees);

        //  Menu 5: Utilities 
        JMenu utility = new JMenu("Utility");
        utility.setForeground(Color.BLUE);
        mb.add(utility);

        calculator = new JMenuItem("Calculator");
        calculator.addActionListener(this);
        utility.add(calculator);
        
        notepad = new JMenuItem("Notepad");
        notepad.addActionListener(this);
        utility.add(notepad);

        //  Menu 6: About
        JMenu about = new JMenu("About");
        about.setForeground(Color.RED);
        mb.add(about);
        
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        about.add(aboutItem);

        //Menu 7: Exit
        JMenu exit = new JMenu("Exit");
        exit.setForeground(Color.BLUE);
        mb.add(exit);
        
        logOutItem = new JMenuItem("Log Out (Back to Login)");
        logOutItem.addActionListener(this);
        exit.add(logOutItem);
        
        exit.add(new JSeparator()); 
        
        exitAppItem = new JMenuItem("Exit Application");
        exitAppItem.addActionListener(this);
        exit.add(exitAppItem);

        // Finalize Frame 
        setJMenuBar(mb);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // These variables are now USED
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isMac = osName.contains("mac");
        boolean isWindows = osName.contains("win");
        
        if (e.getSource() == updateMyInfo) {
            new UpdateStudent(this.studentID); 
        
        } else if (e.getSource() == applyLeave) {
            new ApplyLeave(this.studentID, this.studentName);
        
        } else if (e.getSource() == viewLeave) {
            System.out.println("Opening 'View My Leave' screen for " + this.studentID);
            new ViewMyLeaveStatus(this.studentID); // <-- Our next class to build
            
        } else if (e.getSource() == viewResults) {
            System.out.println("Opening 'View My Results' screen for " + this.studentID);
            new ViewMyResults(this.studentID); // <-- A future class to build
            
        } else if (e.getSource() == feeStructure) {
            System.out.println("Opening 'Fee Structure' window...");
            new FeeStructure();
            
        } else if (e.getSource() == viewMyFees) {
            new ViewMyFees(this.studentID);

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
            setVisible(false);
            new Login();
            
        } else if (e.getSource() == exitAppItem) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        // This is just for testing
        new StudentDashboard("1001", "Test Student"); 
    }
}