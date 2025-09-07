package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddMinion {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());
        addMinion();

        DBConnection.getConnection().close();
    }

    private static void addMinion() throws IOException, SQLException {
        String[] minionTokens = reader.readLine().split("\\s+");
        String[] villainTokens = reader.readLine().split("\\s+");

        String minionName = minionTokens[1];
        int minionAge = Integer.parseInt(minionTokens[2]);
        String minionTown = villainTokens[3];
        int townId = 0;
        if (townExists(minionTown) == 0) {
            townId = createTown(minionTown);
        }

        int minionId = createMinion(minionName, minionAge, townId);

        int villainId = 0;
        String villainName = villainTokens[1];
        if (villainExist(villainName) == 0) {
         villainId =   createVillain(villainName);
        }

    }

    private static int createVillain(String villainName) {
        return 0;
    }

    private static int villainExist(String villainName) {
        return 0;
    }

    private static int createMinion(String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("INSERT INTO minions (name, age, town_id) VALUE (?, ?, ?)");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();
        preparedStatement = DBConnection.getConnection().prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int createTown(String townName) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("INSERT INTO towns (name) VALUE (?)");
        preparedStatement.setString(1, townName);
        preparedStatement.executeUpdate();
        preparedStatement = DBConnection.getConnection().prepareStatement("SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int townExists(String townName) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }
}
