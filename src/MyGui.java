import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


/*
 * Author: Bregovic Dominik
 * creating the gui and the validation
 * Last change: 26.05.2021
 */

public class MyGui extends JFrame{

    JFrame login = new JFrame();
    JFrame errorFrame = new JFrame();
    JFrame table = new JFrame();
    JTable table1 = new JTable();
    JTextField eMail = new JTextField();
    JTextField user = new JTextField();
    JPanel accountsPanel = new JPanel();
    private JButton button = new JButton();
    private JButton okayButton = new JButton();

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
        //super.add(createHeadLines(new JPanel(),0,50,375, 50, "Login User"));
        this.login.add(createHeadLines(new JPanel(),0,50,500, 50, "Login/Signing user"));

        this.login.add(createTextLinesUser(new JPanel(), 250, 150, 200, 50));
        this.login.add(createTextLinesEmail(new JPanel(), 250, 220, 200, 50));
        //super.add(createPasswordLines(new JPanel(), 500, 300, 250, 50));

        this.login.add(createInfoForTextLines(new JPanel(),0, 150, 200, 50, "Username:"));
        this.login.add(createInfoForTextLines(new JPanel(),0, 220, 200, 50, "E-mail:"));
       // super.add(createInfoForTextLines(new JPanel(),400, 300, 100, 50, "Password:"));
        this.login.add(createInfoForTextLines(new JPanel(),0, 300, 100, 50, "Admin:"));

        //super.add(createJTextField(usersRegisterd,0, 100, 375, 470, "old text"));
        //super.add(createProgressbar(new JProgressBar(), 0, 590, 320, 10));

    }

    //Fill in Infos about our users in text
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
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),"My Timetable", 30));
        return panel;
    }

    public JPanel createHeadLines(JPanel panel, int x, int y, int width, int height, String text){
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),text, 20));
        return panel;
    }

    public JPanel createInfoForTextLines(JPanel panel, int x, int y, int width, int height, String text) {
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),text, 20));
        return panel;
    }

    public JPanel createTextLinesUser(JPanel panel, int x, int y, int width, int height){
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createTextField(this.user, 200, 25));
        return panel;
    }

    public JPanel createTextLinesEmail(JPanel panel, int x, int y, int width, int height){
        //panel.setBackground(Color.WHITE);
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

    public void error(String text){
        this.errorFrame.setTitle("Duplicate Warining");
        this.errorFrame.setLayout(null);
        //frame.getContentPane().setBackground(Color.white);
        this.errorFrame.setResizable(false);
        this.errorFrame.setSize(300, 120);
        this.errorFrame.setLocationRelativeTo(null);
        this.errorFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.errorFrame.setVisible(true);
        this.errorFrame.add(new JLabel(text));
        this.errorFrame.add(createInfoForErrorLines(new JPanel(),50, 0, 200, 50, "incorrect input"));
        this.errorFrame.add(okayButton);
    }
    public void createOkayButton(){
        this.okayButton.setBounds(100, 50, 100,20);
        this.okayButton.setText("Okay");
        this.okayButton.setFocusable(false);
    }
    public JPanel createInfoForErrorLines(JPanel panel, int x, int y, int width, int height, String text) {
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),text, 15));
        return panel;
    }

    public void createTableFrame(int width, int height, String title){
        this.table.setTitle(title);
        this.table.setResizable(false);
        this.table.setSize(width,height);
        this.table.setLayout(null);
        this.table.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //super.getContentPane().setBackground(Color.white);
        this.table.setVisible(true);
        this.table.setLocationRelativeTo(null);
        this.table.add(createHeader(new JPanel(),500,0, 500, 50));
    }




    /*public JPanel createPasswordLines(JPanel panel, int x, int y, int width, int height){
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createPasswordField(password, 200, 25));
        return panel;
    }

    public JPasswordField createPasswordField(JPasswordField field, int width, int height){
        field.setPreferredSize(new Dimension(width, height));
        return field;
    }

    public JTextArea createJTextField(JTextArea users, int x, int y, int width, int height, String text) {
        users.setBackground(Color.WHITE);
        users.setBounds(x, y, width, height);
        users.setEditable(false);
        users.setLineWrap(true);
        super.getContentPane().add(users);
        return users;
    }

    public JProgressBar createProgressbar(JProgressBar progressBar, int x, int y, int width, int height){
        progressBar.setBounds(x, y, width, height);
        return progressBar;
    }*/



    public void createDataPanel(JPanel panel,int x, int y, int width, int height){
        panel.setBounds(x,y,width,height);
        panel.setLayout(null);
        // panel.add(Headerlabel);
    }

    public void createListPanel(int x, int y, int width, int height){
        accountsPanel.setBackground(Color.blue);
        accountsPanel.setBounds(x,y,width,height);
        accountsPanel.setLayout(null);
        //accountsPanel.add(Headerlabel);
    }

    public void createImageLabel(JLabel label){
        ImageIcon image = new ImageIcon("<path to image>");
        label.setIcon(image);      // set the image that i want
        label.setIconTextGap(-25); //set gap of text to image
    }


    public JTextField getUser() {
        return user;
    }


    public JTextField getEmail() {
        return eMail;
    }


    public JButton getButton() {
        return button;
    }

    public JButton getOkayButton() {
        return okayButton;
    }


}