import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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


            createTables();

            //könnte die methode insert überladen.....!!

            //here i have to get the info of the person from the gui
            //insertIntoProfessorTable("michael", "eden", "math", "msd");
            //insertIntoProfessorTable("peter", "eden", "math", "msd");
            //insertIntoScheduleTable("tuesday", "mathematics", 1);
            //here i can add courses to the table
            //insertCoursesInTable("math", 1);
            // location of the course, attetion with the int primary key here!!!!!
            //insertRoomsInTable(1,"west");


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

    public static void createTables() throws SQLException{
        try {
            createAdminTable();
            createProfTable();
            createRoom();
            createCourses();
            createSchedule();

        }catch (SQLException e){
            throw new SQLException(state.getWarnings().getMessage(),
                    state.getWarnings().getSQLState(),
                    state.getWarnings().getErrorCode());
        }
    }


    private static void createAdminTable() throws SQLException{

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

    private static void createProfTable() throws SQLException{

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

    private static void createCourses() throws SQLException{

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

    private static void createSchedule() throws SQLException{

        String createTable;
        createTable = "CREATE TABLE IF NOT EXISTS " + "schedule" + " (week_day VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,"
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

    private static void createRoom() throws SQLException{

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

    private static void insertCoursesInTable(String courseName, int index) throws SQLException{

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
    }

    public static void insertIntoScheduleTable(String week_day, String course_name, int prof_Id) throws SQLException {

        stmt = connection.prepareStatement("INSERT INTO schedule (week_day, course_name, prof_Id)VALUES(?, ?, ?)");

        try {

            stmt.setString(1, week_day);
            stmt.setString(2, course_name);
            stmt.setInt(3, prof_Id);
            stmt.addBatch();
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new SQLException(stmt.getWarnings().getMessage(),
                    stmt.getWarnings().getSQLState(),
                    stmt.getWarnings().getErrorCode());
        }
    }

    ////////////////////////////////////////////////////////////////////////////



    public static boolean searchForRecord(String collumn, String tablename, String username){

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
    public void createTable(DefaultTableModel model){

        //Container cnt = this.getContentPane();
        JTable jtbl = new JTable(model);
            cnt.setLayout(new FlowLayout(FlowLayout.LEFT));
            model.addColumn("Id");
            model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Create");
            try {
                stmt = connection.prepareStatement("SELECT * FROM schedule");
                ResultSet Rs = stmt.executeQuery();
                while(Rs.next()){
                    model.addRow(new Object[]{Rs.getInt(1),
                            Rs.getString(2),
                            Rs.getInt(3)});
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JScrollPane pg = new JScrollPane(jtbl);
            cnt.add(pg);
            this.pack();
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
