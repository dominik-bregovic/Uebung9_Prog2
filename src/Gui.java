import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/*
 * Author: Bregovic Dominik
 * creating the gui and the validation
 * Last change: 26.05.2021
 */

public class Gui extends JFrame implements ActionListener {
    JTextField eMail = new JTextField();
    JTextField user = new JTextField();
    JPasswordField password = new JPasswordField();
    JTextArea usersRegisterd = new JTextArea();
    List<String> userNames = new ArrayList();
    List<String> userEmails = new ArrayList();
    JPanel accountsPanel = new JPanel();
    JButton button = new JButton();


    public Gui(){
        createFrame( 750, 650, "");
        createButton();
    }



    public void createFrame(int width, int height, String title){
        //super.setTitle(title);
        super.setResizable(false);
        super.setSize(width,height);
        super.setLayout(null);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //super.getContentPane().setBackground(Color.white);
        super.setVisible(true);
        super.setLocationRelativeTo(null);

        super.add(createHeader(new JPanel(),0,0, 750, 50));
        super.add(createHeadLines(new JPanel(),0,50,375, 50, "List of current users"));
        super.add(createHeadLines(new JPanel(),375,50,375, 50, "Register new user"));

        super.add(createTextLinesUser(new JPanel(), 500, 200, 250, 50));
        super.add(createTextLinesEmail(new JPanel(), 500, 250, 250, 50));
        super.add(createPasswordLines(new JPanel(), 500, 300, 250, 50));

        super.add(createInfoForTextLines(new JPanel(),400, 200, 100, 50, "Username:"));
        super.add(createInfoForTextLines(new JPanel(),400, 250, 100, 50, "E-mail:"));
        super.add(createInfoForTextLines(new JPanel(),400, 300, 100, 50, "Password:"));
        super.add(createInfoForTextLines(new JPanel(),400, 350, 100, 50, "Admin:"));

        super.add(createJTextField(usersRegisterd,0, 100, 375, 470, "old text"));

        super.add(createProgressbar(new JProgressBar(), 0, 590, 320, 10));

    }

    //Fill in Infos about our users in text
    public JLabel createHeaderLabel(JLabel label, String text, int fontsize){

        label.setText(text);
        label.setHorizontalTextPosition(JLabel.CENTER);       //positioning text to image
        label.setVerticalTextPosition(JLabel.CENTER);            //positioning text to image
        label.setForeground(Color.BLACK);
        label.setFont(new Font("MV Boli", Font.PLAIN, fontsize));
        label.setBackground(Color.yellow); //set Background color
        label.setOpaque(true);     //with this Background pixels are changed
        //label.setVerticalAlignment(JLabel.TOP); // set place of label only by BorderLayout
        //label.setHorizontalAlignment(JLabel.LEFT);
        label.setBounds(0,0,750,100);       //set label within frame

        return label;
    }

    public JPanel createHeader(JPanel panel, int x, int y, int width, int height){
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createHeaderLabel(new JLabel(),"Fancy User Management", 30));
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
        panel.add(createTextField(user, 200, 25));
        return panel;
    }

    public JPanel createTextLinesEmail(JPanel panel, int x, int y, int width, int height){
        //panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, width, height);
        panel.add(createTextField(eMail, 200, 25));
        return panel;
    }

    public JTextField createTextField(JTextField text, int width, int height){
        text.setPreferredSize(new Dimension(width, height));
        return text;
    }

    public JPanel createPasswordLines(JPanel panel, int x, int y, int width, int height){
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
    }


    public void createButton(){
        button.setBounds(600, 400, 105,20);
        button.addActionListener(this::actionPerformed);
        button.setText("Create User");
        button.setFocusable(false);
        super.add(button);
    }


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

    public boolean validateUserName(){
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
    }

    public boolean validateUserEmail(){
        int counter = 0;
        boolean val = false;

        for (int i = 0; i < userEmails.size(); i++) {
            if (userEmails.get(i).contains(eMail.getText())){
                counter = Collections.frequency(userEmails, eMail.getText());
            }

            if (counter > 1){
                userEmails = sortingListToUniqueElements(userEmails);
                error("User already exists");
                break;
            }
        }
        return val;
    }

    public void validation(boolean userVal, boolean emailVal){
        if(userVal && emailVal){
            usersRegisterd.append(user.getText()+" ("+ eMail.getText()+")");
        }
    }

    public List<String> sortingListToUniqueElements(List<String> listOfWords ){
        Set<String> uniqueWords = new HashSet<>();
        List<String> uniqueList;
        uniqueWords.addAll(listOfWords);
        uniqueList = new ArrayList<>(uniqueWords);
        return  uniqueList;
    }

    public void error(String text){
        JFrame frame = new JFrame();
        frame.setTitle("Duplicate Warining");
        //frame.getContentPane().setBackground(Color.white);
        frame.setResizable(false);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(new JLabel(text));

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

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            if (userNames.size() < 20 && userEmails.size() < 20)
                userNames.add(user.getText());
            user.setText("");

            userEmails.add(eMail.getText());
            eMail.setText("");

            validation(validateUserName(), validateUserEmail());

            password.setText("");
        }else {
            errorMemmoryLimit("Memory limit exeeded - cannot add another user!");
        }
    }

}