import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainController = new MainController();
        mainController.personArrayList.add(new Person("Manuel"));
        mainController.personArrayList.add(new Person("Gabriel"));
        mainController.personArrayList.add(new Person("Calin"));
        mainController.personArrayList.add(new Person("Serban"));

        mainController.roomArrayList.add(new Room(0,1,100));
        mainController.roomArrayList.add(new Room(0,2,100));
        mainController.roomArrayList.add(new Room(0,3,100));
        mainController.roomArrayList.add(new Room(0,4,100));
        mainController.roomArrayList.add(new Room(1,1,100));
        mainController.roomArrayList.add(new Room(1,2,100));
        mainController.roomArrayList.add(new Room(1,3,100));
        mainController.roomArrayList.add(new Room(1,4,100));

        mainController.receptionistArrayList.add(new Receptionist(true, "Tabian"));
        mainController.receptionistArrayList.add(new Receptionist(true, "Quesmin"));
        mainController.receptionistArrayList.add(new Receptionist(true, "Podi"));
        mainController.receptionistArrayList.add(new Receptionist(false, "Septimiu"));

//        bookingArrayList.add(new Booking());

        MainForm mainForm = new MainForm();
        LoginForm loginForm = new LoginForm();
        loginForm.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
