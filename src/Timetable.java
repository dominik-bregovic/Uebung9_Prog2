import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
/*
* Author: Bregovic Dominik
* Last change: 15.06
* Source: https://stackoverflow.com/questions/27815400/retrieving-data-from-jdbc-database-into-jtable/43772751
* */
public class Timetable extends JFrame{
    private MyJDBC jdbc;


        DefaultTableModel model = new DefaultTableModel();
        Container cnt = this.getContentPane();
        JTable jtbl = new JTable(model);
        public Timetable() {
            cnt.setLayout(new FlowLayout(FlowLayout.LEFT));
            model.addColumn("Date");
            model.addColumn("Day");
            model.addColumn("Course");
            model.addColumn("Professor_Id");
            model.addColumn("Location");
            //model.addColumn("Create");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "StudentTimetable", "1234");
                PreparedStatement pstm = con.prepareStatement("SELECT * FROM schedule");
                pstm.execute("USE timetable");
                ResultSet Rs = pstm.executeQuery();
                while(Rs.next()){
                    model.addRow(new Object[]{Rs.getString(1), Rs.getString(2),Rs.getString(3),Rs.getInt(4), Rs.getString(5)});
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JScrollPane pg = new JScrollPane(jtbl);
            cnt.add(pg);
            this.pack();
        }

}
