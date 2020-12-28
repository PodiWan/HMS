import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

public class BookingItem extends BorderPane {

    private Booking heldBooking;

    public Booking getHeldBooking() {
        return heldBooking;
    }

    public void setHeldBooking(Booking heldBooking) {
        this.heldBooking = heldBooking;
    }

    public BookingItem(Booking booking) {
        this.heldBooking = booking;

        this.setId("booking-item");

        Label lBookingPerson = new Label();
        lBookingPerson.setText(this.heldBooking.getBookingPerson().getName());
        lBookingPerson.setId("booking-item-header");

        Label lBookingInfo = new Label();
        lBookingInfo.setText("Booked room: " + this.heldBooking.getBookedRoom().getNumber());
        lBookingInfo.setId("booking-item-content");

        this.setTop(lBookingPerson);
        this.setLeft(lBookingInfo);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(actionEvent -> {
            for (int i = 0; i < Main.mainController.bookingArrayList.size(); ++i) {
                if(Main.mainController.bookingArrayList.get(i).getId() == this.heldBooking.getId()) {
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": deleted booking #" + Main.mainController.bookingArrayList.get(i).getId());
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.getContent().getChildren().add(informationLabel);
                    Main.mainController.bookingArrayList.remove(i);
                    Main.mainController.sideMenu.getContent().getChildren().remove(i);
                    Main.writeBookings();
                    MainForm.hm.updateMap();
                }
            }
        });
        MenuItem menuItemCheckOut = new MenuItem("Check-out");
        menuItemCheckOut.setOnAction(actionEvent -> {
            for (int i = 0; i < Main.mainController.bookingArrayList.size(); ++i) {
                if(Main.mainController.bookingArrayList.get(i).getId() == this.heldBooking.getId()) {
                    Stage dialogStage = new Stage();
                    CheckOutMenu com = new CheckOutMenu();
                    com.setCheckOutBooking(Main.mainController.bookingArrayList.get(i));
                    if(com.start(dialogStage)) {
                        Label informationLabel = new Label(LocalDate.now().toString() +
                                ": checked-out booking #" + Main.mainController.bookingArrayList.get(i).getId());
                        informationLabel.setId("activity-log-content");
                        Main.mainController.activityMenu.getContent().getChildren().add(informationLabel);
                        Main.mainController.bookingArrayList.remove(i);
                        Main.mainController.sideMenu.getContent().getChildren().remove(i);
                        Main.writeBookings();
                        MainForm.hm.updateMap();
                    }
                }
            }
        });
        contextMenu.getItems().addAll(menuItemDelete, menuItemCheckOut);
        this.setOnMouseClicked(mouseEvent -> {
            //when left-clicked
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                //move the map to the correct floor (if necessary) and highlight the room
                if(MainForm.hm.getActiveFloor() != this.heldBooking.getBookedRoom().getFloor()) {
                    MainForm.hm.setActiveFloor(this.heldBooking.getBookedRoom().getFloor());
                    MainForm.hm.updateMap();
                }
                MainForm.hm.highlightRoom(this.heldBooking.getBookedRoom());
            }
            if(mouseEvent.getButton() == MouseButton.SECONDARY)
                contextMenu.show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        });
    }

    public BookingItem() {
        this(null);
    }
}
