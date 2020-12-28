import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.Period;

public class CheckOutMenu {

    private Booking checkOutBooking;

    public Booking getCheckOutBooking() {
        return checkOutBooking;
    }

    public void setCheckOutBooking(Booking checkOutBooking) {
        this.checkOutBooking = checkOutBooking;
    }

    private final double initHeight = 400;
    private boolean paymentComplete = false;

    public boolean start(Stage dialogStage){
        GridPane root = new GridPane();
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(40);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(60);
        root.getColumnConstraints().addAll(leftColumn, rightColumn);
        root.getStylesheets().add("css/dialog.css");
        //left element
        BorderPane leftPane = new BorderPane();
        leftPane.setId("left-column");
        leftPane.prefWidthProperty().bind(root.widthProperty().multiply(0.40));
        leftPane.prefHeightProperty().bind(root.heightProperty());

        //text in middle of element which displays the new customer id
        Label customerIdLabel = new Label("Checking out\nbooking: #" + checkOutBooking.getId());
        leftPane.setCenter(customerIdLabel);

        //wrapper pane for better display
        BorderPane wrapperPane = new BorderPane();
        wrapperPane.getStyleClass().add("details-column");
        //header
        Label headerLabel = new Label("Customer details");
        headerLabel.setId("details-header");
        wrapperPane.setTop(headerLabel);

        GridPane detailsPane = new GridPane();
        detailsPane.getStyleClass().add("details-column");
        detailsPane.prefWidthProperty().bind(root.widthProperty().multiply(0.60));
        detailsPane.prefHeightProperty().bind(root.heightProperty());
        detailsPane.setVgap(20);
        detailsPane.setPadding(new Insets(50, 0, 0, 10));

        wrapperPane.setCenter(detailsPane);
        Label priceLabel = new Label("Price");
        int computedPrice = Period.between(checkOutBooking.getBookingStart(), checkOutBooking.getBookingEnd()).getDays()
                * checkOutBooking.getBookedRoom().getPrice();
        Label computedPriceLabel = new Label(Integer.toString(computedPrice));
        computedPriceLabel.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane priceHolderPane = new HolderPane(priceLabel, computedPriceLabel, false);

        Label additionalCostsLabel = new Label("Mini-bar costs");
        TextField additionalCostsField = new TextField();
        additionalCostsField.setPromptText("e.g.: 10");
        additionalCostsField.textProperty().addListener((obs, oldValue, newValue) -> {
            if(!additionalCostsField.getText().isEmpty()) {
                if (Integer.parseInt(additionalCostsField.getText()) >= 0)
                    computedPriceLabel.setText(Integer.toString(computedPrice +
                            Integer.parseInt(additionalCostsField.getText())));
            }
            else{
                computedPriceLabel.setText(Integer.toString(computedPrice));
            }
        });

        HolderPane additionalCostsHolderPane = new HolderPane(additionalCostsLabel, additionalCostsField,false);

        detailsPane.add(priceHolderPane, 0, 0);
        detailsPane.add(additionalCostsHolderPane, 0, 1);

        root.add(leftPane, 0, 0);
        root.add(wrapperPane, 1, 0);

        Button btnClose = new Button("Cancel");
        btnClose.setOnAction(e -> dialogStage.close());

        Scene s = new Scene(root, 650, initHeight);

        Button btnSubmit = new Button("Pay");
        btnSubmit.setOnAction(e -> {
            paymentComplete = true;
            dialogStage.close();
        });
        HolderPane buttonHolderPane = new HolderPane(btnClose, btnSubmit, true);

        detailsPane.add(buttonHolderPane, 0, 5);

        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(s);
        dialogStage.showAndWait();

        return paymentComplete;
    }
}


