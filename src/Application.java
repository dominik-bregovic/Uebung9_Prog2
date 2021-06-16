/*
 * Author: Bregovic Dominik
 * main-class
 * Last change: 30.05.2021
 */

public class Application {

    public static void main(String[] args) {
        Logic logic = new Logic(new MyJDBC(), new MyGui());
    }

}
