package JavaDBAppIntroductionExercises;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Scanner;

public class PrintAllMinionNames {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException {
        System.out.printf("Enter password:");
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection("minions_db", scanner.nextLine());
        printAllMinionsNames();

        DBConnection.getConnection().close();
    }

    private static void printAllMinionsNames() throws SQLException {
        ResultSet resultSet = DBConnection.getConnection().createStatement().executeQuery("SELECT name FROM minions");

        ArrayDeque<String> minionNames = new ArrayDeque<>();

        while (resultSet.next()) {
            minionNames.addLast(resultSet.getString("name"));
        }
        while (!minionNames.isEmpty()) {
            System.out.println(minionNames.removeFirst());
            if (!minionNames.isEmpty()) {
                System.out.println(minionNames.removeLast());
            }
        }
    }
}
