import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class BookingMenu {

    ComboBox<String> room = new ComboBox<>();
    ComboBox<String> receptionist = new ComboBox<>();
    ComboBox<String> person = new ComboBox<>();

    BookingMenu(){
        //fill the combobox options with the rooms
        for (var iterator : Main.mainController.roomArrayList) {
            boolean booked = false;
            for(var booking : Main.mainController.bookingArrayList) {
                if (booking.bookedRoom.id == iterator.id) {
                    booked = true;
                    break;
                }
            }
            if(!booked)
                room.getItems().add(iterator.id + ". " + iterator.floor + "-" +iterator.number);
        }

        //fill the combobox options with the receptionists
        for (var iterator : Main.mainController.receptionistArrayList) {
            receptionist.getItems().add(iterator.id + ". " + iterator.name);
        }

        //fill the combobox options with the people staying at the hotel
        for (var iterator : Main.mainController.personArrayList) {
            person.getItems().add(iterator.id + ". " + iterator.name);
        }
    }

    public boolean start(ArrayList<BookingItem> bookingItems){
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Add booking");
        dialog.setHeaderText("Insert data regarding booking number: #" + Main.mainController.bookingArrayList.size() + 1);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 10));

        room.setPromptText("Available room");
        receptionist.setPromptText("Receptionist");
        person.setPromptText("Person");

        DatePicker startDate = new DatePicker();
        DatePicker endDate = new DatePicker();

        grid.add(new Label("Room:"), 0, 0);
        grid.add(room, 1, 0);
        grid.add(new Label("Receptionist:"), 0, 1);
        grid.add(receptionist, 1, 1);
        grid.add(new Label("Person:"), 0, 2);
        grid.add(person, 1, 2);
        grid.add(new Label("Start date:"), 0, 3);
        grid.add(startDate, 1, 3);
        grid.add(new Label("End date:"), 0, 4);
        grid.add(endDate, 1, 4);

        //disable past dates for the start date picker
        startDate.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        //disable dates prior to the startDate for end date picker
        endDate.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate firstDate = startDate.getValue().plusDays(1);

                setDisable(empty || date.compareTo(firstDate) < 0);
            }
        });
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(grid);

        //executes on dialog closure, creates a new booking corresponding to a room, receptionist and person
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                //try-catch block to ensure the correctness of the data and to notify the user if something went wrong
                try {
                    //get the id from the comboboxes, it is differentiated from the rest by a ".", i.e.: id. other info
                    Booking newBooking = new Booking(Integer.parseInt(room.getValue().split(Pattern.quote("."))[0]),
                            Integer.parseInt(receptionist.getValue().split(Pattern.quote("."))[0]),
                            Integer.parseInt(person.getValue().split(Pattern.quote("."))[0]),
                            //fucking mambo jambo
                            Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    //add the new booking to the list of bookings
                    Main.mainController.bookingArrayList.add(newBooking);
                    //add the new booking to the side menu
                    bookingItems.add(new BookingItem(
                            Main.mainController.personArrayList.get(newBooking.bookingPerson.id - 1).name
                            .concat(" " + newBooking.bookingStart.toString()), newBooking));
                    return newBooking;
                }
                catch (Exception ex){
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setHeaderText("Make sure to complete the fields with correct data!");
                    errorDialog.show();
                }
            }
            return null;
        });
        return dialog.showAndWait().isPresent();
    }
}