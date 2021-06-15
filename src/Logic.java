import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Logic extends JFrame implements ActionListener {

    private MyJDBC jdbc;
    private MyGui log;
    JTextField pass = new JTextField();
    JTextField user = new JTextField();
    JTextArea usersRegistered = new JTextArea();
    JButton button ;
    JButton okayButton;
    String password = "msd";
    String userName = "";


    public Logic(MyJDBC database, MyGui login){
        this.jdbc = database;
        this.log = login;
        retrievData();
        addActionListenerToButton();
    }


    public void retrievData(){
       this.user = log.getUser();
       this.pass = log.getEmail();
       this.button = log.getButton();
       this.okayButton = log.getOkayButton();
    }

    public void addActionListenerToButton(){
        button.addActionListener(this::actionPerformed);
        okayButton.addActionListener(this::actionPerformed2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            doesUserExist();
        }
    }

    public void actionPerformed2(ActionEvent e) {
        if (e.getSource() == okayButton){
            log.errorFrame.dispose();
        }
    }

    public void doesUserExist(){
        getInput();
        if (jdbc.searchForRecord("firstname", "professors", userName) &&
                jdbc.searchForRecord("password", "professors", password)){
            log.login.dispose();
            log.createTableFrame(1000, 750, "Timetable");
            log.table1.setModel(jdbc.createTable(new DefaultTableModel()));

        }else{
            log.error("dont know");
        }
    }

    public void getInput(){
        userName = user.getText();
        user.setText("");
        password = pass.getText();
        pass.setText("");
    }

    public void errorMemmoryLimit(String text){
        JFrame frame = new JFrame();
        frame.setTitle("Duplicate Warining");
        //frame.getContentPane().setBackground(Color.white);
        frame.setResizable(false);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(new JLabel(text));
        frame.add(button);

    }






    /*public boolean validateUserEmail(){
        int counter = 0;
        boolean val = false;

        for (int i = 0; i < userPassword.size(); i++) {
            if (userPassword.get(i).contains(pass.getText())){
                counter = Collections.frequency(userPassword, pass.getText());
            }

            if (counter > 1){
                userPassword = sortingListToUniqueElements(userPassword);
                error("User already exists");
                break;
            }
        }
        return val;
    }*/



    public void validation(boolean userVal, boolean emailVal){
        if(userVal && emailVal){
            usersRegistered.append(user.getText()+" ("+ pass.getText()+")");
        }
    }

    public List<String> sortingListToUniqueElements(List<String> listOfWords ){
        Set<String> uniqueWords = new HashSet<>();
        List<String> uniqueList;
        uniqueWords.addAll(listOfWords);
        uniqueList = new ArrayList<>(uniqueWords);
        return  uniqueList;
    }





    /*public boolean validateUserName(){
        int counter = 0;
        boolean val = false;

        for (int i = 0; i < userNames.size(); i++) {
            if (userNames.get(i).contains(user.getText())){
                counter = Collections.frequency(userNames, user.getText());
            }

            if (counter > 1){
                userNames = sortingListToUniqueElements(userNames);
                error("E-mail address already exists!");
                break;
            }else {
                val = true;
            }
        }
        return val;
    }*/



}
