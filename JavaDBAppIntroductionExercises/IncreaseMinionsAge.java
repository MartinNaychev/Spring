package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class IncreaseMinionsAge {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        System.out.printf("Enter password:");
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());
        increaseMinionAge();

        DBConnection.getConnection().close();

    }

    private static void increaseMinionAge() throws SQLException, IOException {
        int[] ids = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < Arrays.stream(ids).count(); i++) {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("UPDATE minions SET name = LOWER(name), age = age+1 " +
                    " WHERE id = ?");
            preparedStatement.setInt(1, ids[i]);
            preparedStatement.executeUpdate();

        }

        ResultSet resultSet = DBConnection.getConnection().createStatement().executeQuery("SELECT name, age FROM minions");
        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }


    }
}
