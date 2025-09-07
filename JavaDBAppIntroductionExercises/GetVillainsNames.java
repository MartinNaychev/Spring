package JavaDBAppIntroductionExercises;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username default (root): ");
        String user = scanner.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.print("Enter password default (empty): ");
        String password = scanner.nextLine().trim();
        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", password);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);
        Statement  statement = connection.createStatement();


        ResultSet resultSet = statement.executeQuery(
                "SELECT v.name, COUNT(*) AS count FROM villains v " +
                "JOIN minions_villains mv ON v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING COUNT > 15 " +
                "ORDER BY count DESC");



        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int count = resultSet.getInt("count");
            System.out.printf("%s %d%n", name, count);

        }

        connection.close();
    }
}
