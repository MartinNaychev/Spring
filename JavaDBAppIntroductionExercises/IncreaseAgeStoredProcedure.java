package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        System.out.printf("Enter password:");
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());
        increaseAgeProcedure();

        DBConnection.getConnection().close();
    }

    private static void increaseAgeProcedure() throws SQLException, IOException {
        int minionId = Integer.parseInt(reader.readLine());
        CallableStatement callableStatement = DBConnection.getConnection().prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.execute();

        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("SELECT name, age FROM minions WHERE id = ?");
        preparedStatement.setInt(1, minionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
    }
}
