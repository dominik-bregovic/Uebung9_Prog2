public class Application {

    public static void main(String[] args) {
        Logic logic = new Logic(new MyJDBC(), new MyGui());
    }

}
