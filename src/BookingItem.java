import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

public class BookingItem extends Pane {

    Booking heldBooking;

    public BookingItem(Booking booking) {
        this.heldBooking = booking;

        this.setId("booking-item");

        Label lBookingInfo = new Label();
        lBookingInfo.setText(this.heldBooking.bookingPerson.name);
        lBookingInfo.prefWidthProperty().bind(this.widthProperty());

        this.setPrefHeight(50);

        this.getChildren().add(lBookingInfo);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(actionEvent -> {
            for (int i = 0; i < Main.mainController.bookingArrayList.size(); ++i) {
                if(Main.mainController.bookingArrayList.get(i).id == this.heldBooking.id) {
                    Main.mainController.bookingArrayList.remove(i);
                    Main.mainController.sideMenu.getChildren().remove(i + 1);
                }
            }
        });
        contextMenu.getItems().addAll(menuItemDelete);
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
