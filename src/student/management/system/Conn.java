package student.management.system;

import java.sql.Statement; 
import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {

    public Connection connection;
    public Statement statement;

    public Conn(){
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection("jdbc:mysql:///studentmanagement", "root","ankit1906");
            
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}