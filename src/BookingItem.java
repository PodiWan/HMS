import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class BookingItem extends Pane {

    Booking heldBooking;
    private String strBookingInfo;

    public void setBookingInfo(String strBookingInfo) {
        this.strBookingInfo = strBookingInfo;
    }

    public BookingItem(String strBookingInfo, Booking booking) {
        this.strBookingInfo = strBookingInfo;
        this.heldBooking = booking;

        Label lBookingInfo = new Label();
        lBookingInfo.setText(this.strBookingInfo);
        lBookingInfo.setFont(new Font("Calibri", 0));
        lBookingInfo.setStyle("-fx-font-size: 1.5em");
        lBookingInfo.prefWidthProperty().bind(this.widthProperty());

        this.setPrefHeight(50);
        this.setStyle("-fx-background-color: #ddd4dd");

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
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                MainForm.hm.activeFloor = this.heldBooking.bookedRoom.floor;
                MainForm.hm.updateMap();
            }
            if(mouseEvent.getButton() == MouseButton.SECONDARY)
                contextMenu.show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        });
    }

    public BookingItem() {
        this("No text entered", null);
    }
}
