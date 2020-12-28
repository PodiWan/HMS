import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class BookingItem extends BorderPane {

    Booking heldBooking;

    public BookingItem(Booking booking) {
        this.heldBooking = booking;

        this.setId("booking-item");

        Label lBookingPerson = new Label();
        lBookingPerson.setText(this.heldBooking.bookingPerson.name);
        lBookingPerson.setId("booking-item-header");

        Label lBookingInfo = new Label();
        lBookingInfo.setText("Booked room: " + this.heldBooking.bookedRoom.number);
        lBookingInfo.setId("booking-item-content");

        this.setTop(lBookingPerson);
        this.setLeft(lBookingInfo);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(actionEvent -> {
            for (int i = 0; i < Main.mainController.bookingArrayList.size(); ++i) {
                if(Main.mainController.bookingArrayList.get(i).id == this.heldBooking.id) {
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": deleted booking #" + Main.mainController.bookingArrayList.get(i).id);
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.content.getChildren().add(informationLabel);
                    Main.mainController.bookingArrayList.remove(i);
                    Main.mainController.sideMenu.content.getChildren().remove(i);
                    MainForm.hm.updateMap();
                }
            }
        });
        MenuItem menuItemCheckOut = new MenuItem("Check-out");
        menuItemCheckOut.setOnAction(actionEvent -> {
            for (int i = 0; i < Main.mainController.bookingArrayList.size(); ++i) {
                if(Main.mainController.bookingArrayList.get(i).id == this.heldBooking.id) {
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": checked-out booking #" + Main.mainController.bookingArrayList.get(i).id);
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.content.getChildren().add(informationLabel);
                    Main.mainController.bookingArrayList.remove(i);
                    Main.mainController.sideMenu.content.getChildren().remove(i);
                    MainForm.hm.updateMap();
                }
            }
        });
        contextMenu.getItems().addAll(menuItemDelete, menuItemCheckOut);
        this.setOnMouseClicked(mouseEvent -> {
            //when left-clicked
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                //move the map to the correct floor (if necessary) and highlight the room
                if(MainForm.hm.activeFloor != this.heldBooking.bookedRoom.floor) {
                    MainForm.hm.activeFloor = this.heldBooking.bookedRoom.floor;
                    MainForm.hm.updateMap();
                }
                MainForm.hm.highlightRoom(this.heldBooking.bookedRoom);
            }
            if(mouseEvent.getButton() == MouseButton.SECONDARY)
                contextMenu.show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        });
    }

    public BookingItem() {
        this(null);
    }
}
