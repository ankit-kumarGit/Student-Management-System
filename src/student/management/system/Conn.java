package student.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties; // <-- New import
import java.io.FileInputStream; // <-- New import

public class Conn {

    public Connection connection;
    public Statement statement;

    public Conn(){
        try {
            // 1. Create a Properties object
            Properties props = new Properties();
            
            // 2. Load the .properties file
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            // 3. Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 4. Connect using the credentials from the file
            connection = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.pass")
            );
            
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
