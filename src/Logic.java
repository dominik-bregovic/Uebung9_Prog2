import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/*
 * Author: Bregovic Dominik
 * here the are jdbc and the gui parts put together
 * Last change: 15.06
 * */

public class Logic extends JFrame implements ActionListener {

    private MyJDBC jdbc;
    private MyGui log;
    private JTextField pass = new JTextField();
    private JTextField user = new JTextField();
    private JTextField date_day = new JTextField();
    private JTextField week_day = new JTextField();
    private JTextField course_name = new JTextField();
    private JTextField professorId = new JTextField();
    private JTextField location = new JTextField();
    private JButton button ;
    private JButton okayButton;
    private JButton userButton;
    private String password = "msd";
    private String userName = "";
    private String date = "";
    private String day = "";
    private String course = "";
    private Integer prof;
    private String locate = "";


    public Logic(MyJDBC database, MyGui login){
        this.jdbc = database;
        this.log = login;
        retrieveData();
        addActionListenerToButton();
    }


    public void retrieveData(){
        this.user = log.getUser();
        this.pass = log.getEmail();
        this.date_day = log.getDate_day();
        this.week_day = log.getWeek_day();
        this.course_name = log.getCourse_name();
        this.professorId = log.getProf_Id();
        this.location = log.getLocationText();
        this.button = log.getButton();
        this.okayButton = log.getOkayButton();
        this.userButton = log.getUserButton();
    }

    public void addActionListenerToButton(){
        button.addActionListener(this::actionPerformed);
        okayButton.addActionListener(this::actionPerformed2);
        userButton.addActionListener(this:: actionPerformed3);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            doesUserExist();
        }
    }

    public void actionPerformed2(ActionEvent e) {
        if (e.getSource() == okayButton){
            log.getErrorFrame().dispose();
        }
    }

    public void actionPerformed3(ActionEvent e) {
        if (e.getSource() == userButton){
            getProfChanges();
            try {
                jdbc.insertIntoScheduleTable(date, day, course, prof, locate);
                log.getProftable().dispose();
                alterTimetable();
            } catch (SQLException er) {
                //create error frame and go again
                log.error("inserting error", "invalid input");
            }
        }
    }

    public void doesUserExist(){
        getInput();
        if (log.getC1().isSelected()){
           selector("firstname", "lastname", "administrator", "incorrect input","No such Admin!!");

        }else if(log.getC2().isSelected()){
           selector("firstname", "password", "professors", "incorrect input", "No such Prof");
            alterTimetable();
            System.out.println(day);

        }else if (log.getC3().isSelected()){
            selector("firstname", "password", "students", "incorrect input", "No such Stud");
            goToTimetable(475, 490);
        }else{
            log.error("Please choose a user", "No user choosen");
        }

    }

    public void getInput(){
        userName = user.getText();
        user.setText("");
        password = pass.getText();
        pass.setText("");
    }

    public void selector(String column1, String column2, String tablename, String errorMessage, String header){
        if (jdbc.searchForRecord(column1, tablename, userName) &&
                jdbc.searchForRecord(column2, tablename, password)){
            log.getLogin().dispose();
            return;

        }else{
            log.error(header, errorMessage);

        }
    }

    public void alterTimetable(){
        log.createProfTimetable(475, 790);


    }

    //not right handheld
    public void getProfChanges(){
        try {
            date = date_day.getText();
            date_day.setText("");
            day = week_day.getText();
            week_day.setText("");
            course = course_name.getText();
            course_name.setText("");
            prof = Integer.valueOf(professorId.getText());
            professorId.setText("");
            locate = location.getText();
            location.setText("");
        }catch(NumberFormatException e){
            log.getProftable().dispose();
            alterTimetable();
            log.error("inserting error", "invalid input");

        }
    }

    public void goToTimetable(int x, int y){
        log.createStudnetTimetable(x, y);
    }

}
