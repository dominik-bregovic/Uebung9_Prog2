import java.sql.*;
import java.util.Scanner;

/*
 * Author: Bregovic Dominik
 * jdbc create-insert-delete
 * Last change: 30.05.2021
 */

public class MyJDBC {
    private static Connection connection;
    private static Statement state;
    private static PreparedStatement stmt;
    private static ResultSet result;
    private static Scanner scan = new Scanner(System.in);
    private static Long validateID;

    public static void main(String[] args) {
        createRegistrationTable();
    }

    public static void createRegistrationTable() {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "dbd_user";
        String password = "8_7*06AZ!#y";
        String sqlCommand;
        sqlCommand = "CREATE DATABASE IF NOT EXISTS dbd_employee CHARACTER SET utf8 COLLATE utf8_unicode_ci";

        try  {
            connection = DriverManager.getConnection(url, username, password);
            state = connection.createStatement();
            state.executeUpdate(sqlCommand);
            state.execute("USE dbd_employee");

            stmt = connection.prepareStatement("INSERT INTO salary (firstname,lastname,department,amount)VALUES(?, ?, ?, ?)");

            createTable();
            //insertRecordsInTable();
            //deleteSingleRecordViaTransaction();

        } catch (SQLException e) {
            while (e != null){
                System.out.println("Code = " + e.getErrorCode());
                System.out.println("Message = " + e.getMessage());
                System.out.println("State = " + e.getSQLState());
                e = e.getNextException();
            }
        }finally {
            try {
                if (state != null){state.close();}
                if (stmt != null){stmt.close();}
                if (connection != null){connection.close();}
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static void createTable() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS salary (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " firstname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "lastname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "department VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "amount INT(11) NOT NULL , PRIMARY KEY (id))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private static void insertRecordsInTable() throws SQLException{
        try {

            stmt.setString(1, "Max");
            stmt.setString(2, "Mustermann");
            stmt.setString(3, "Engineering");
            stmt.setInt(4, 2000);
            stmt.addBatch();

            stmt.setString(1, "Katrin");
            stmt.setString(2, "Musterfrau");
            stmt.setString(3, "Production");
            stmt.setInt(4, 2200);
            stmt.addBatch();

            stmt.setString(1, "John");
            stmt.setString(2, "Doe");
            stmt.setString(3, "Engineering");
            stmt.setInt(4, 2400);
            stmt.addBatch();

            stmt.setString(1, "Becker");
            stmt.setString(2, "Heinz");
            stmt.setString(3, "Marketing");
            stmt.setInt(4, 2800);
            stmt.addBatch();

            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }

    public static void deleteSingleRecordViaTransaction(){
        validation();
        String delete;

        try {
            result = state.executeQuery("SELECT id FROM salary");
            while (result.next()) {

                if (validateID == result.getLong("id")){
                    sendingDeleteRequest(validateID);
                    System.out.print("Is it okay to delete?");
                    delete = scan.next();

                    if (delete.equals("y")) {
                        //execute and commit the delete request
                        state.executeBatch();
                        connection.commit();
                        System.out.println("Successfully deleted!");
                    }else {
                        //call rollback method!
                        connection.rollback();
                        System.out.println("Can not be deleted!");
                    }
                    return;
                }
            }
            //gets only printed when id does not exists
            System.out.println("ID " + validateID + " does not exist!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void validation(){
        String input;

        try {
            System.out.print("Please enter an ID:");
            input = scan.nextLine();
            validateID = Long.valueOf(input);
        }catch (Exception e){
            validation();
        }
        if (validateID <= 0){
            validation();
        }

    }

    public static void sendingDeleteRequest(Long idRow){
        try {
            connection.setAutoCommit(false);
            state.addBatch("DELETE FROM salary WHERE id =" + idRow);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
