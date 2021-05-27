import java.sql.*;

public class MyJDBC {

    public static void createRegistrationTable(String url, String username, String password) {
        String sqlCommand;
        sqlCommand = "CREATE DATABASE IF NOT EXISTS dbd_student CHARACTER SET utf8 COLLATE utf8_unicode_ci";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            Statement state = connection.createStatement();
            state.executeUpdate(sqlCommand);

            state.execute("USE dbd_student");
            createTable(state);
            insertRecordsInTable(state);
            readRecordsFromTable(state);
            readSortedRecordsFromTable(state);

            state.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
