package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        System.out.printf("Enter password:");
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());
        removeVillain();

        DBConnection.getConnection().close();
    }

    private static void removeVillain() throws IOException, SQLException {
        int villainID = Integer.parseInt(reader.readLine());
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("SELECT name FROM Villains WHERE id=?");
        preparedStatement.setInt(1, villainID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            System.out.println("No such villain was found");
        } else {
            String villainName = resultSet.getString("name");

            preparedStatement = DBConnection.getConnection().prepareStatement("DELETE FROM minions_villains WHERE villain_id=?");
            preparedStatement.setInt(1, villainID);
            int deletedCount = preparedStatement.executeUpdate();

            preparedStatement = DBConnection.getConnection().prepareStatement("DELETE FROM villains WHERE id=?");
            preparedStatement.setInt(1, villainID);
            preparedStatement.executeUpdate();

            System.out.printf("%s was deleted%n", villainName);
            System.out.printf("%d minions released",deletedCount);
        }
    }
}
