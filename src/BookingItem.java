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

    //TODO add update/delete on click
    int bookingId;
    private Label lBookingInfo;
    private String strBookingInfo;

    public void setBookingInfo(String strBookingInfo) {
        this.strBookingInfo = strBookingInfo;
    }

    public BookingItem(String strBookingInfo, int bookingId) {
        this.strBookingInfo = strBookingInfo;
        this.bookingId = bookingId;

        this.lBookingInfo = new Label();
        this.lBookingInfo.setText(this.strBookingInfo);
        this.lBookingInfo.setFont(new Font("Calibri", 0));
        this.lBookingInfo.setStyle("-fx-font-size: 1.5em");
        this.lBookingInfo.prefWidthProperty().bind(this.widthProperty());

        this.setPrefHeight(50);
        this.setStyle("-fx-background-color: #ddd4dd");

        this.getChildren().add(lBookingInfo);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemUpdate = new MenuItem("Update");
        menuItemUpdate.setOnAction(actionEvent -> System.out.println("Updating"));
        MenuItem menuItemDelete = new MenuItem("Delete");
        menuItemDelete.setOnAction(actionEvent -> {
            for (int i = 0; i < Main.mainController.bookingArrayList.size(); ++i) {
                if(Main.mainController.bookingArrayList.get(i).id == this.bookingId) {
                    Main.mainController.bookingArrayList.remove(i);
                }
            }
        });
        contextMenu.getItems().addAll(menuItemUpdate, menuItemDelete);
        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY)
                System.out.println("Left clicked me!");
            if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                System.out.println("Right clicked me!");
                contextMenu.show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });
    }

    public BookingItem() {
        this("No text entered", 0);
    }
}
