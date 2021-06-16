import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;


/*
 * Author: Bregovic Dominik
 * creating the gui and the validation
 * Last change: 26.05.2021
 */

public class MyGui extends JFrame{


    private JFrame login = new JFrame();
    private JFrame errorFrame = new JFrame();
    prvate JFrame userFrame = new JFrame();
    private JFrame table;
    private JFrame proftable;
    private JTable table1 = new JTable();
    private JTextField eMail = new JTextField();
    private JTextField user = new JTextField();
    private JTextField date_day = new JTextField();
    private JTextField week_day = new JTextField();
    private JTextField course_name = new JTextField();
    private JTextField prof_Id = new JTextField();
    private JTextField location = new JTextField();
    private JPanel accountsPanel = new JPanel();
    private JCheckBox c1,c2,c3;
    private final JButton button = new JButton();
    private final JButton okayButton = new JButton();
    private final JButton userButton = new JButton();
    private final ButtonGroup group = new ButtonGroup();

    public MyGui(){
        createLogFrame( 500, 400, "Login");

        createButton();
        createOkayButton();
    }


    public void createLogFrame(int width, int height, String title){
        this.login.setTitle(title);
        this.login.setResizable(false);
        this.login.setSize(width,height);
        this.login.setLayout(null);
        this.login.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //super.getContentPane().setBackground(Color.white);
        this.login.setVisible(true);
        this.login.setLocationRelativeTo(null);

        this.login.add(createHeader(new JPanel(),0,0, 500, 50));

        this.login.add(createHeadLines(new JPanel(),0,50,500, 50, "Login/Signing user"));

        this.login.add(createTextLinesUser(new JPanel(), 250, 120, 200, 50));
        this.login.add(createTextLinesEmail(new JPanel(), 250, 170, 200, 50));


        this.login.add(createInfoForTextLines(new JPanel(),0, 120, 200, 50, "Username:"));
        this.login.add(createInfoForTextLines(new JPanel(),0, 170, 200, 50, "Password:"));

        createCheckBox();
        this.login.add(c1);
        this.login.add(c2);
        this.login.add(c3);
    }

    public void createCheckBox(){
        c1 = new JCheckBox("Admin");
        c2 = new JCheckBox("Professor");
        c3 = new JCheckBox("Student");

        c1.setBounds(0, 275, 100,30);
        c2.setBounds(0, 300, 100,30);
        c3.setBounds(0, 325, 100,30);

        this.group.add(c1);
        this.group.add(c2);
        this.group.add(c3);
    }

    public JLabel createHeaderLabel(JLabel label, String text, int fontsize){

        label.setText(text);
        label.setHorizontalTextPosition(JLabel.CENTER);       //positioning text to image
        label.setVerticalTextPosition(JLabel.CENTER);            //positioning text to image
        label.setForeground(Color.BLACK);
        label.setFont(new Font("MV Boli", Font.PLAIN, fontsize));
        label.setBackground(Color.WHITE); //set Background color
        label.setOpaque(true);     //with this Background pixels are changed
        //label.setVerticalAlignment(JLabel.TOP); // set place of label only by BorderLayout
        //label.setHorizontalAlignment(JLabel.LEFT);
        label.setBounds(0,0,750,100);       //set label within frame

        return label;
    }

    public JPanel createHeader(JPanel panel, int x, int y, int width, int height){
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),"My Timetable", 30));
        return panel;
    }

    public JPanel createHeadLines(JPanel panel, int x, int y, int width, int height, String text){
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),text, 20));
        return panel;
    }

    public JPanel createInfoForTextLines(JPanel panel, int x, int y, int width, int height, String text) {
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),text, 20));
        return panel;
    }

    public JPanel createTextLinesUser(JPanel panel, int x, int y, int width, int height){
        panel.setBounds(x, y, width, height);
        panel.add(createTextField(this.user, 200, 25));
        return panel;
    }

    public JPanel createTextLinesEmail(JPanel panel, int x, int y, int width, int height){
        panel.setBounds(x, y, width, height);
        panel.add(createTextField(this.eMail, 200, 25));
        return panel;
    }

    public JTextField createTextField(JTextField text, int width, int height){
        text.setPreferredSize(new Dimension(width, height));
        return text;
    }

    public void createButton(){
        this.button.setBounds(350, 320, 100,20);
        this.button.setText("Next");
        this.button.setFocusable(false);
        this.login.add(button);
    }

    public void error(String text, String message){
        this.errorFrame.setTitle(text);
        this.errorFrame.setLayout(null);
        this.errorFrame.setResizable(false);
        this.errorFrame.setSize(300, 120);
        this.errorFrame.setLocationRelativeTo(null);
        this.errorFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.errorFrame.setVisible(true);
        this.errorFrame.add(new JLabel(text));
        this.errorFrame.add(createInfoForErrorLines(new JPanel(),50, 0, 200, 50, message));
        this.errorFrame.add(okayButton);
    }

    public void createOkayButton(){
        this.okayButton.setBounds(100, 50, 100,20);
        this.okayButton.setText("Okay");
        this.okayButton.setFocusable(false);
    }

    public JPanel createInfoForErrorLines(JPanel panel, int x, int y, int width, int height, String text) {
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),text, 15));
        return panel;
    }

    public void createStudnetTimetable(int width, int height){
        this.table = new Timetable();
        this.table.setTitle("My Timetable");
        this.table.setResizable(false);
        this.table.setSize(width,height);
        this.table.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.table.setVisible(true);
        this.table.setLocationRelativeTo(null);
    }

    public void createProfTimetable(int width, int height){
        this.proftable = new Timetable();
        this.proftable.setTitle("Prof Timetable");
        this.proftable.setResizable(false);
        this.proftable.setSize(width,height);
        this.proftable.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.proftable.setVisible(true);
        this.proftable.setLocationRelativeTo(null);

        this.proftable.add(createTextLinesProf(new JPanel(), 700, 120, 200, 50, date_day));
        this.proftable.add(createInfoForTextLines(new JPanel(),0, 120, 200, 50, "date_day:"));
        this.proftable.add(createTextLinesProf(new JPanel(), 700, 200, 200, 50, week_day));
        this.proftable.add(createInfoForTextLines(new JPanel(),0, 120, 200, 50, "week_day:"));
        this.proftable.add(createTextLinesProf(new JPanel(), 700, 280, 200, 50, course_name));
        this.proftable.add(createInfoForTextLines(new JPanel(),0, 120, 200, 50, "course_name:"));
        this.proftable.add(createTextLinesProf(new JPanel(), 700, 360, 200, 50, prof_Id));
        this.proftable.add(createInfoForTextLines(new JPanel(),0, 120, 200, 50, "prof_Id:"));
        this.proftable.add(createTextLinesProf(new JPanel(), 700, 440, 200, 50, location));
        this.proftable.add(createInfoForTextLines(new JPanel(),0, 120, 200, 50, "location:"));
        this.proftable.add(userButton);
    }

    public JPanel createTextLinesProf(JPanel panel, int x, int y, int width, int height, JTextField jtf){
        panel.setBounds(x, y, width, height);
        panel.add(createTextField(jtf, 200, 25));
        return panel;
    }

    public JTextField getUser() {
        return user;
    }


    public JTextField getEmail() {
        return eMail;
    }

    public JTextField getDate_day() {
        return date_day;
    }

    public JTextField getWeek_day() {
        return week_day;
    }

    public JTextField getCourse_name() {
        return course_name;
    }

    public JTextField getProf_Id() {
        return prof_Id;
    }


    public JTextField getLocationText() {
        return location;
    }



    public JButton getButton() {
        return button;
    }

    public JButton getOkayButton() {
        return okayButton;
    }

    public JButton getUserButton() {
        return userButton;
    }

    public javax.swing.JFrame getLogin() {
        return login;
    }

    public javax.swing.JFrame getErrorFrame() {
        return errorFrame;
    }

    public javax.swing.JFrame getProftable() {
        return proftable;
    }

    public JCheckBox getC1() {
        return c1;
    }

    public JCheckBox getC2() {
        return c2;
    }

    public JCheckBox getC3() {
        return c3;
    }




}