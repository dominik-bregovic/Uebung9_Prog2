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

    public MyJDBC(){
        createRegistrationTable();
    }

    public void createRegistrationTable() {
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

            createTables();

        } catch (SQLException e) {
            while (e != null){
                System.out.println("Code = " + e.getErrorCode());
                System.out.println("Message = " + e.getMessage());
                System.out.println("State = " + e.getSQLState());
                e = e.getNextException();
            }
        }/*finally {
            try {
                if (state != null){state.close();}
                if (stmt != null){stmt.close();}
                if (connection != null){connection.close();}
            }catch (SQLException e){
                e.printStackTrace();
            }
        }*/
    }
////////////////////////////////////////////////////////////////////////////////

    public void createTables() throws SQLException{
        try {
            createAdminTable();
            createProfTable();
            createStudentsTable();
            createRoom();
            createCourses();
            createSchedule();

        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }


    private void createAdminTable() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS administrator (id INT(11) NOT NULL AUTO_INCREMENT,"
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

    private void createProfTable() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + "professors" + " (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " firstname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "lastname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "coursename  VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,"
                + "password VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "PRIMARY KEY (id))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private void createStudentsTable() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + "students" + " (id INT(11) NOT NULL AUTO_INCREMENT,"
                + " firstname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "lastname VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "password VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "PRIMARY KEY (id))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private void createCourses() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + "courses" + " (id INT(11) NOT NULL AUTO_INCREMENT,"
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

    private void createSchedule() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + "schedule" + " (date_day VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,"
                + " week_day VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + " course_name VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + " prof_Id INT(11) NOT NULL, "
                + " location VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci, "
                + "PRIMARY KEY (week_day))";

        try {
            state.executeUpdate(createTable);
        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }

    private  void createRoom() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + "rooms" + " (room_nr INT(11) NOT NULL,"
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

    /*private static void insertCoursesInTable(String courseName, int index) throws SQLException{

        stmt = connection.prepareStatement("INSERT INTO courses (course_name, proff_Id)VALUES(?, ?)");

        try {

            stmt.setString(1, courseName);
            stmt.setInt(2, index);

            stmt.addBatch();

            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }

    private static void insertRoomsInTable(int room_nr, String location) throws SQLException{

        stmt = connection.prepareStatement("INSERT INTO rooms (room_nr, room_location)VALUES(?, ?)");

        try {

            stmt.setInt(1, room_nr);
            stmt.setString(2, location);
            stmt.addBatch();

            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }

    public static void insertIntoProfessorTable(String firstname, String lastname, String course, String password) throws SQLException {

        stmt = connection.prepareStatement("INSERT INTO professors (firstname,lastname,coursename, password)VALUES(?, ?, ?, ?)");

        try {

            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, course);
            stmt.setString(4, password);
            stmt.addBatch();
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }*/

    public void insertIntoScheduleTable(String date_day,String week_day, String course_name, int prof_Id, String location) throws SQLException {

        stmt = connection.prepareStatement("INSERT INTO schedule (date_day, week_day, course_name, prof_Id, location)VALUES(?, ?, ?, ?, ?)");

        try {
            stmt.setString(1, date_day);
            stmt.setString(2, week_day);
            stmt.setString(3, course_name);
            stmt.setInt(4, prof_Id);
            stmt.setString(5, location);
            stmt.addBatch();
            stmt.executeBatch();

        } catch (SQLException e) {
            /*throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());*/

        }
    }

    /*public static void insertIntoStudentsTable(String firstname, String lastname, String password) throws SQLException {

        stmt = connection.prepareStatement("INSERT INTO students (firstname,lastname,password)VALUES(?, ?, ?)");

        try {

            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, password);
            stmt.addBatch();
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }

    public static void insertIntoAdminTable(String firstname, String lastname) throws SQLException {

        stmt = connection.prepareStatement("INSERT INTO administrator (firstname,lastname)VALUES(?, ?)");

        try {

            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.addBatch();
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }*/

    ////////////////////////////////////////////////////////////////////////////



    public boolean searchForRecord(String collumn, String tablename, String username){

        try {
            result = state.executeQuery("SELECT " + collumn + " FROM " + tablename);
            while (result.next()) {
                if (username.contains(result.getString(collumn))) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
/////////////////////////////////////////////////

    public static Connection getConnection() {
        return connection;
    }

    public static PreparedStatement getStmt() {
        return stmt;
    }


    /*public static void validation(){
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
    }*/
}
