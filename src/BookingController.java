import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import java.util.Optional;

public class BookingController extends HBox {

    public BookingController(SideMenu parent) {
        Button btnAdd = new Button();
        btnAdd.setText("Add a new booking");

        btnAdd.setOnAction(e -> {
            BookingMenu bm = new BookingMenu();
            try {
                if(bm.start(parent.bookingItemList))
                    parent.getChildren().add(parent.bookingItemList.get(parent.bookingItemList.size() - 1));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        this.prefWidthProperty().bind(parent.widthProperty());
        btnAdd.setPrefHeight(40);
        btnAdd.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().add(btnAdd);
    }
}