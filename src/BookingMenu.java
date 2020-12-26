import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class BookingMenu {

    ComboBox<String> roomComboBox = new ComboBox<>();
    ComboBox<String> receptionistComboBox = new ComboBox<>();
    ComboBox<String> personComboBox = new ComboBox<>();

    Booking newBooking;

    BookingMenu(){
        //fill the combobox options with the rooms
        for (var iterator : Main.mainController.roomArrayList) {
            boolean booked = false;
            for(var booking : Main.mainController.bookingArrayList) {
                if (booking.bookedRoom.id == iterator.id) {
                    booked = true;
                    break;
                }
            }
            if(!booked)
                roomComboBox.getItems().add(iterator.id + ". " + iterator.floor + "-" +iterator.number);
        }

        //fill the combobox options with the receptionists
        for (var iterator : Main.mainController.receptionistArrayList) {
            receptionistComboBox.getItems().add(iterator.id + ". " + iterator.name);
        }

        //fill the combobox options with the people staying at the hotel
        for (var iterator : Main.mainController.personArrayList) {
            personComboBox.getItems().add(iterator.id + ". " + iterator.name);
        }
    }

    public void start(ArrayList<BookingItem> bookingItems, Stage dialogStage){
        newBooking = new Booking();

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
        Label bookingIdLabel = new Label("Insert booking:\n#" + Integer.toString(newBooking.id));
        leftPane.setCenter(bookingIdLabel);

        //wrapper pane for better display
        BorderPane wrapperPane = new BorderPane();
        wrapperPane.getStyleClass().add("details-column");
        //header
        Label headerLabel = new Label("Booking details");
        headerLabel.setId("details-header");
        wrapperPane.setTop(headerLabel);

        //details pane is on the right, contains input fields
        GridPane detailsPane = new GridPane();
        detailsPane.getStyleClass().add("details-column");
        detailsPane.prefWidthProperty().bind(root.widthProperty().multiply(0.60));
        detailsPane.prefHeightProperty().bind(root.heightProperty());
        detailsPane.setVgap(20);
        detailsPane.setPadding(new Insets(50, 0, 0, 10));

        wrapperPane.setCenter(detailsPane);

        //inputs
        Label roomLabel = new Label("Room");
        roomComboBox.setPromptText("Available room");
        roomComboBox.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane roomHolderPane = new HolderPane(roomLabel, roomComboBox, false);

        Label receptionistLabel = new Label("Receptionist");
        receptionistComboBox.setPromptText("Receptionist");
        receptionistComboBox.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane receptionistHolderPane = new HolderPane(receptionistLabel, receptionistComboBox, false);

        Label personLabel = new Label("Person");
        personComboBox.setPromptText("Person");
        personComboBox.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane personHolderPane = new HolderPane(personLabel, personComboBox, false);

        Label startDateLabel = new Label("Start date");
        DatePicker startDate = new DatePicker();
        startDate.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane startDateHolderPane = new HolderPane(startDateLabel, startDate, false);

        Label endDateLabel = new Label("Start date");
        DatePicker endDate = new DatePicker();
        endDate.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane endDateHolderPane = new HolderPane(endDateLabel, endDate, false);

        Label errorHeader = new Label("Error!");
        errorHeader.getStyleClass().add("error-label");
        Label errorContent = new Label();
        errorContent.getStyleClass().add("error-label");
        HolderPane errorHolderPane = new HolderPane(errorHeader, errorContent, false);
        errorHolderPane.setVisible(false);

        detailsPane.add(roomHolderPane, 0, 0);
        detailsPane.add(receptionistHolderPane, 0, 1);
        detailsPane.add(personHolderPane, 0, 2);
        detailsPane.add(startDateHolderPane, 0, 3);
        detailsPane.add(endDateHolderPane, 0, 4);
        detailsPane.add(errorHolderPane, 0, 5);

        //disable past dates for the start date picker
        startDate.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        //disable dates prior to the startDate for end date picker
        endDate.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate firstDate = startDate.getValue().plusDays(1);

                setDisable(empty || date.compareTo(firstDate) < 0);
            }
        });

        Button btnClose = new Button("Cancel");
        btnClose.setOnAction(e -> {
            dialogStage.close();
        });

        Button btnSubmit = new Button("Submit");

        HolderPane buttonHolderPane = new HolderPane(btnClose, btnSubmit, true);

        Scene s = new Scene(root, 650, 670);

        btnSubmit.setOnAction(e -> {
            String missingData = "";
            if(roomComboBox.getValue() == null)
                missingData += "room";
            if(receptionistComboBox.getValue() == null) {
                if (!missingData.equals(""))
                    missingData += ", ";
                missingData += "receptionist";
            }
            if(personComboBox.getValue() == null){
                if (!missingData.equals(""))
                    missingData += ", ";
                missingData += "person";
            }
            if(startDate.getValue() == null){
                if (!missingData.equals(""))
                    missingData += ", ";
                missingData += "start date";
            }
            if(endDate.getValue() == null){
                if (!missingData.equals(""))
                    missingData += ", ";
                missingData += "end date";
            }
            if(!missingData.equals("")) {
                missingData = "Please provide information regarding:" + missingData;
                errorContent.setText(missingData);
                errorContent.setWrapText(true);
                errorHeader.setTooltip(new Tooltip(missingData));
                errorContent.setTooltip(new Tooltip(missingData));
                errorHolderPane.setVisible(true);
                dialogStage.setHeight(s.getHeight() + errorHolderPane.getHeight());
            }
            else{
                Booking newBooking = new Booking(Integer.parseInt(roomComboBox.getValue().split(Pattern.quote("."))[0]),
                        Integer.parseInt(receptionistComboBox.getValue().split(Pattern.quote("."))[0]),
                        Integer.parseInt(personComboBox.getValue().split(Pattern.quote("."))[0]),
                        //fucking mambo jambo
                        Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                //add the new booking to the list of bookings
                Main.mainController.bookingArrayList.add(newBooking);
                //add the new booking to the side menu
                bookingItems.add(new BookingItem(newBooking));
                Main.mainController.sideMenu.content.getChildren()
                        .add(Main.mainController.sideMenu.bookingItemList
                                .get(Main.mainController.sideMenu.bookingItemList.size() - 1));
                dialogStage.close();
            }
        });

        detailsPane.add(buttonHolderPane, 0, 7);

        root.add(leftPane, 0, 0);
        root.add(wrapperPane, 1, 0);

        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(s);
        dialogStage.showAndWait();
    }
}