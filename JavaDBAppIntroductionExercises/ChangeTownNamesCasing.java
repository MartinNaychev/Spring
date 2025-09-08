package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        System.out.printf("Enter password:");
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());

        updateTownNames();


        DBConnection.getConnection().close();

    }

    private static void updateTownNames() throws IOException, SQLException {
        String country = reader.readLine();
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");
        preparedStatement.setString(1, country);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.printf("%d town names were affected.%n", count);
            preparedStatement = DBConnection.getConnection().prepareStatement("SELECT name FROM towns WHERE country = ?");
            preparedStatement.setString(1, country);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> townNames = new ArrayList<>();
            while (resultSet.next()) {
                townNames.add(resultSet.getString("name"));
            }
            System.out.print(townNames);
        } else {
            System.out.println("No town names were affected.");
        }


    }
}
