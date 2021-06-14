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

    public MyJDBC(){
        createRegistrationTable();
    }

    public static void createRegistrationTable() {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "StudentTimetable";
        String password = "1234";
        String sqlCommand;
        sqlCommand = "CREATE DATABASE IF NOT EXISTS timetable CHARACTER SET utf8 COLLATE utf8_unicode_ci";

        try  {
            connection = DriverManager.getConnection(url, username, password);
            state = connection.createStatement();
            state.executeUpdate(sqlCommand);
            state.execute("USE timetable");


            createTable("administrator");
            createProfTable("professors");
            createCourses("courses");
            createRoom("rooms");
            createSchedule("schedule");



            stmt = connection.prepareStatement("INSERT INTO professors (firstname,lastname)VALUES(?, ?, ?)");
            //insertPersonsInTable("Michael", "Eden", "mathematics");

            stmt = connection.prepareStatement("INSERT INTO courses (course_name)VALUES(?, ?)");
            insertCoursesInTable("Mathe");

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
////////////////////////////////////////////////////////////////////////////////


    private static void createTable(String tablename) throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + tablename + " (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " firstname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "lastname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "PRIMARY KEY (id))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private static void createProfTable(String tablename) throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + tablename + " (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " firstname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "lastname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "coursename  VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,"
                + "PRIMARY KEY (id))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private static void createCourses(String tablename) throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + tablename + " (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " course_name VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + " proff_Id INT(11) NOT NULL, "
                + "PRIMARY KEY (id))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private static void createSchedule(String tablename) throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + tablename + " (week_day VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,"
                + " course_name VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "prof_Id INT(11) NOT NULL, "
                + "PRIMARY KEY (week_day))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private static void createRoom(String tablename) throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + tablename + " (room_Nr INT(11) NOT NULL,"
                + " room_location VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "PRIMARY KEY (room_NR))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    /////////////////////////////////////////////////////////////////////////
    private static void insertPersonsInTable(String first, String last, String course) throws SQLException{
        try {

            stmt.setString(1, first);
            stmt.setString(2, last);
            stmt.setString(3, course);
            stmt.addBatch();
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }

    private static void insertCoursesInTable(String courseName) throws SQLException{
        try {

            stmt.setString(1, courseName);
            stmt.setInt(2, 0);
            stmt.addBatch();

            stmt.executeBatch();

        } catch (SQLException e) {
           /* throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());*/
        }
    }

    public static void insertNewProf(){

    }

    ////////////////////////////////////////////////////////////////////////////



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
