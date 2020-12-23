import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class BookingMenu {

    public boolean start(ArrayList<BookingItem> bookingItems) throws Exception{
        Dialog<Booking> dialog = new Dialog<Booking>();
        dialog.setTitle("Add booking");
        dialog.setHeaderText("Insert data regarding booking number ..");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField room = new TextField();
        room.setPromptText("Room");
        TextField receptionist = new TextField();
        receptionist.setPromptText("Receptionist");
        TextField person = new TextField();
        person.setPromptText("Person");

        grid.add(new Label("Room id:"), 0, 0);
        grid.add(room, 1, 0);
        grid.add(new Label("Receptionist id:"), 0, 1);
        grid.add(receptionist, 1, 1);
        grid.add(new Label("Person id:"), 0, 2);
        grid.add(person, 1, 2);

        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                Booking newBooking = new Booking(room.getText(), receptionist.getText(), person.getText());
                bookingItems.add(new BookingItem(newBooking.bookingPerson.name));
                return newBooking;
            }
            return null;
        });

        return dialog.showAndWait().isPresent();
    }
}