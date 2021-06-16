import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Userhandling extends JFrame{

    DefaultTableModel model = new DefaultTableModel();
    Container cnt = this.getContentPane();
    JTable jtbl = new JTable(model);
    public Userhandling() {
        cnt.setLayout(new FlowLayout(FlowLayout.LEFT));
        model.addColumn("ID");
        model.addColumn("Firstname");
        model.addColumn("Lastname");
        model.addColumn("Course");
        model.addColumn("Password");
        //model.addColumn("Create");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "StudentTimetable", "1234");
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM professors");
            pstm.execute("USE timetable");
            ResultSet Rs = pstm.executeQuery();
            while(Rs.next()){
                model.addRow(new Object[]{Rs.getInt(1), Rs.getString(2),Rs.getString(3),Rs.getString(4), Rs.getString(5)});
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JScrollPane pg = new JScrollPane(jtbl);
        cnt.add(pg);
        this.pack();
    }
}
