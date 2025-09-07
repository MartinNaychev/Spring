package JavaDBAppIntroductionExercises;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private static Connection connection;

    public DBConnection(String dbName, String password) {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", password);

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, prop);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
