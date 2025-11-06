package student.management.system;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.SwingUtilities;


public class Splash extends JWindow {

    public Splash() {

        // Image Setup
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/screen.png"));
        Image i2 = i1.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH); // Use SCALE_SMOOTH
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        
        // Add the image to the JWindow's content pane
        getContentPane().add(img, BorderLayout.CENTER);

        // Window Setup
        //pack() sizes the window perfectly to fit the image
        pack(); 
        
        //This centers the window on the screen
        setLocationRelativeTo(null); 

        //Show the window
        setVisible(true);

        //The Timer
        //Use a javax.swing.Timer to handle the delay.
        // It's thread-safe and designed for this.
        Timer timer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This code runs on the correct UI thread after 4 seconds
                setVisible(false);
                dispose(); // Free up the resources
                new Login();
            }
        });

        //Make the timer run only *once*
        timer.setRepeats(false);
        
        //Start the timer!
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Splash();
            }
        });
    }
}