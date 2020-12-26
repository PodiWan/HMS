import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BookingController extends HBox {

    public BookingController(SideMenu parent) {
        Button btnAddBooking = new Button();
        btnAddBooking.setText("\u2795");
        btnAddBooking.setTooltip(new Tooltip("Add a new booking"));

        Button btnAddPerson = new Button();
        btnAddPerson.setText("\uD83D\uDEC5");
        btnAddPerson.setTooltip(new Tooltip("Add a new customer"));
        btnAddPerson.setId("booking-controller-last-button");

        btnAddBooking.setOnAction(e -> {
            BookingMenu bm = new BookingMenu();
            Stage dialogStage = new Stage();
            bm.start(parent.bookingItemList, dialogStage);
        });

        btnAddPerson.setOnAction(e -> {
            CustomerMenu cm = new CustomerMenu();
            Stage dialogStage = new Stage();
            cm.start(dialogStage);
        });

        this.prefWidthProperty().bind(parent.widthProperty());

        this.getChildren().add(btnAddBooking);
        this.getChildren().add(btnAddPerson);

        DoubleProperty fontSize = new SimpleDoubleProperty(10);
        for (var node : this.getChildren()) {
            if(node instanceof Button){
                ((Button) node).setPrefHeight(40);
                ((Button) node).prefWidthProperty().bind(this.widthProperty().divide(2));
                fontSize.bind(this.widthProperty().add(this.heightProperty()).divide(20));
                node.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
            }
        }
    }
}