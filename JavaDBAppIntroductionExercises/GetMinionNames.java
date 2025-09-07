package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());


        getMinionNames();


        DBConnection.getConnection().close();
    }

    private static void getMinionNames() throws IOException, SQLException {
        int villainId = Integer.parseInt(reader.readLine());

        PreparedStatement preparedStatement = DBConnection.getConnection()
                .prepareStatement("SELECT id, name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
            return;
        }

        System.out.printf("Villain: %s%n", rs.getString("name"));


        preparedStatement = DBConnection.getConnection().prepareStatement(
                "SELECT m.name, m.age FROM minions m " +
                        "JOIN minions_villains mv  ON  mv.minion_id = m.id " +
                        "WHERE mv.villain_id = ?");
        preparedStatement.setInt(1, villainId);
        rs = preparedStatement.executeQuery();
        int count = 1;
        while (rs.next()) {

            System.out.printf("%d. %s %d%n", count++, rs.getString("name"), rs.getInt("age"));

        }
    }
}

