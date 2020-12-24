import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
//import javax.script.Bindings;

public class BookingController extends HBox {

    public BookingController(SideMenu parent) {
        Button btnAddBooking = new Button();
        btnAddBooking.setText("\u2795");
        btnAddBooking.setTooltip(new Tooltip("Add a new booking"));

        Button btnAddReceptionist = new Button();
        btnAddReceptionist.setText("\uD83D\uDC64");
        btnAddReceptionist.setTooltip(new Tooltip("Add a new receptionist"));

        Button btnAddRoom = new Button();
        btnAddRoom.setText("\uD83D\uDEAA");
        btnAddRoom.setTooltip(new Tooltip("Add a new room"));

        Button btnAddPerson = new Button();
        btnAddPerson.setText("\uD83D\uDEC5");
        btnAddPerson.setTooltip(new Tooltip("Add a new person"));

        btnAddBooking.setOnAction(e -> {
            BookingMenu bm = new BookingMenu();
            try {
                if(bm.start(parent.bookingItemList))
                    parent.getChildren().add(parent.bookingItemList.get(parent.bookingItemList.size() - 1));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        this.prefWidthProperty().bind(parent.widthProperty());

        this.getChildren().add(btnAddBooking);
        this.getChildren().add(btnAddReceptionist);
        this.getChildren().add(btnAddRoom);
        this.getChildren().add(btnAddPerson);

        DoubleProperty fontSize = new SimpleDoubleProperty(10);
        for (var node : this.getChildren()) {
            if(node instanceof Button){
                ((Button) node).setPrefHeight(40);
                ((Button) node).prefWidthProperty().bind(this.widthProperty().multiply(0.25));
                fontSize.bind(this.widthProperty().add(this.heightProperty()).divide(20));
                node.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
            }
        }
    }
}