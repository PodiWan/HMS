import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class BookingMenu {

    public boolean start(ArrayList<BookingItem> bookingItems) throws Exception{
        Dialog<Booking> dialog = new Dialog<Booking>();
        dialog.setTitle("Add booking");
        dialog.setHeaderText("Insert data regarding booking number ..");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<String> room = new ComboBox<String>();
        room.setPromptText("Room");
        //fill the combobox options with the rooms
        for (var iterator : Main.mainController.roomArrayList) {
            room.getItems().add(Integer.toString(iterator.id) + ". " + iterator.floor + "-" +iterator.number);
        }

        ComboBox<String> receptionist = new ComboBox<String>();
        receptionist.setPromptText("Receptionist");
        for (var iterator : Main.mainController.receptionistArrayList) {
            receptionist.getItems().add(Integer.toString(iterator.id) + ". " + iterator.name);
        }

        ComboBox<String> person = new ComboBox<String>();
        person.setPromptText("Person");
        for (var iterator : Main.mainController.personArrayList) {
            person.getItems().add(Integer.toString(iterator.id) + ". " + iterator.name);
        }

        grid.add(new Label("Room id:"), 0, 0);
        grid.add(room, 1, 0);
        grid.add(new Label("Receptionist id:"), 0, 1);
        grid.add(receptionist, 1, 1);
        grid.add(new Label("Person id:"), 0, 2);
        grid.add(person, 1, 2);

        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(grid);

        //executes on dialog closure, creates a new booking corresponding to a room, receptionist and person
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                //get the id from the comboboxes, it is differentiated from the rest by a ".", i.e.: id. other info
                Booking newBooking = new Booking(Integer.parseInt(room.getValue().split(Pattern.quote("."))[0]),
                        Integer.parseInt(receptionist.getValue().split(Pattern.quote("."))[0]),
                        Integer.parseInt(person.getValue().split(Pattern.quote("."))[0]));

                bookingItems.add(new BookingItem(
                        Main.mainController.personArrayList.get(newBooking.bookingPerson.id).name));
                return newBooking;
            }
            return null;
        });
        return dialog.showAndWait().isPresent();
    }
}