import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    public static MainController mainController;

    @Override
    public void start(Stage primaryStage){
        mainController = new MainController();

        mainController.personArrayList.add(new Person("Manuel"));
        mainController.personArrayList.add(new Person("Gabriel"));
        mainController.personArrayList.add(new Person("Calin"));
        mainController.personArrayList.add(new Person("Serban"));

        mainController.roomArrayList.add(new Room(0,1,100));
        mainController.roomArrayList.add(new Room(0,2,100));
        mainController.roomArrayList.add(new Room(0,3,100));
        mainController.roomArrayList.add(new Room(0,4,100));
        mainController.roomArrayList.add(new Room(1,11,100));
        mainController.roomArrayList.add(new Room(1,12,100));
        mainController.roomArrayList.add(new Room(1,13,100));
        mainController.roomArrayList.add(new Room(1,14,100));
        mainController.roomArrayList.add(new Room(2,21,100));

        mainController.receptionistArrayList.add(new Receptionist(true, "Tabian"));
        mainController.receptionistArrayList.add(new Receptionist(true, "Quesmin"));
        mainController.receptionistArrayList.add(new Receptionist(true, "Podi"));
        mainController.receptionistArrayList.add(new Receptionist(false, "Victor"));

        mainController.bookingArrayList.add(new Booking(1, 1, 1, LocalDate.now(), LocalDate.now().plusDays(1)));
        mainController.bookingArrayList.add(new Booking(3, 2, 2, LocalDate.now(), LocalDate.now().plusDays(1)));
        mainController.bookingArrayList.add(new Booking(4, 3, 3, LocalDate.now(), LocalDate.now().plusDays(1)));
        mainController.bookingArrayList.add(new Booking(9, 4, 4, LocalDate.now(), LocalDate.now().plusDays(1)));
//        mainController.bookingArrayList.add(new Booking(1, 1, 5));

        mainController.activeReceptionist = mainController.receptionistArrayList.get(0);

        for(var booking : mainController.bookingArrayList)
            mainController.sideMenu.bookingItemList.add(new BookingItem(booking));

        LoginForm loginForm = new LoginForm();
        loginForm.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
