import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class BookingItem extends Pane {

    //TODO add update/delete on click

    private Label lBookingInfo;
    private String strBookingInfo;

    public void setBookingInfo(String strBookingInfo) {
        this.strBookingInfo = strBookingInfo;
    }

    public BookingItem(String strBookingInfo) {
        this.strBookingInfo = strBookingInfo;

        this.lBookingInfo = new Label();
        this.lBookingInfo.setText(this.strBookingInfo);
        this.lBookingInfo.setFont(new Font("Calibri", 0));
        this.lBookingInfo.setStyle("-fx-font-size: 1.5em");
        this.lBookingInfo.prefWidthProperty().bind(this.widthProperty());

        this.setPrefHeight(50);
        this.setStyle("-fx-background-color: #ddd4dd");

        this.getChildren().add(lBookingInfo);
    }

    public BookingItem() {
        this("No text entered");
    }
}
