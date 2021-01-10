import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class BookingMenu {

    ComboBox<String> roomComboBox = new ComboBox<>();
    ComboBox<String> personComboBox = new ComboBox<>();

    private Booking newBooking;

    public Booking getNewBooking() {
        return newBooking;
    }

    public void setNewBooking(Booking newBooking) {
        this.newBooking = newBooking;
    }

    BookingMenu(){
        //fill the combobox options with the rooms
        for (var iterator : Main.mainController.roomArrayList) {
            boolean booked = false;
            for(var booking : Main.mainController.bookingArrayList) {
                if (booking.getBookedRoom().getId() == iterator.getId()) {
                    booked = true;
                    break;
                }
            }
            if(!booked)
                roomComboBox.getItems().add(iterator.getId() + ". " + iterator.getFloor() + "-" +iterator.getNumber());
        }

        personComboBox.setEditable(true);

        ArrayList<String> options = new ArrayList<>();
        for (var iterator : Main.mainController.customerArrayList)
            options.add(iterator.getId() + ". " + iterator.getName());


        ObservableList<String> items = FXCollections.observableArrayList(options);
        // Create a FilteredList wrapping the ObservableList.
        FilteredList<String> filteredItems = new FilteredList<>(items, p -> true);

        personComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = personComboBox.getEditor();
            final String selected = personComboBox.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                if (selected == null || !selected.contains(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        if (item.toUpperCase().contains(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        personComboBox.setItems(filteredItems);
}

    private final double initHeight = 670.0;

    public void start(ArrayList<Pane> bookingItems, Stage dialogStage){
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
        Label bookingIdLabel = new Label("Insert booking:\n#" + newBooking.getId());
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
        Label receptionistIdLabel = new Label(Main.mainController.activeReceptionist.getId() + ". " +
                Main.mainController.activeReceptionist.getName());

        HolderPane receptionistHolderPane = new HolderPane(receptionistLabel, receptionistIdLabel, false);

        Label personLabel = new Label("Customer");
        personComboBox.setPromptText("Customer");
        personComboBox.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane personHolderPane = new HolderPane(personLabel, personComboBox, false);

        Label startDateLabel = new Label("Start date");
        DatePicker startDate = new DatePicker();
        startDate.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane startDateHolderPane = new HolderPane(startDateLabel, startDate, false);

        Label endDateLabel = new Label("End date");
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
                LocalDate firstDate;
                if(startDate.getValue() != null) {
                    firstDate = startDate.getValue().plusDays(1);

                    setDisable(empty || date.compareTo(firstDate) < 0);
                }
                else{
                    setDisable(true);
                }
            }
        });

        Button btnClose = new Button("Cancel");
        btnClose.setOnAction(e -> dialogStage.close());

        Button btnSubmit = new Button("Submit");

        HolderPane buttonHolderPane = new HolderPane(btnClose, btnSubmit, true);

        Scene s = new Scene(root, 650, initHeight);

        btnSubmit.setOnAction(e -> {
            String missingData = "";
            if(roomComboBox.getValue() == null)
                missingData += "room";
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
                String str = "Please provide information regarding:";
                if(!errorContent.getText().contains(str)) {
                    dialogStage.setHeight(initHeight + errorHolderPane.getHeight());
                }
                missingData = str + missingData;
                errorContent.setText(missingData);
                errorContent.setWrapText(true);
                errorHeader.setTooltip(new Tooltip(missingData));
                errorContent.setTooltip(new Tooltip(missingData));
                errorHolderPane.setVisible(true);
            }
            else{
                Booking newBooking = new Booking(Integer.parseInt(roomComboBox.getValue().split(Pattern.quote("."))[0]),
                        Main.mainController.activeReceptionist.getId(),
                        Integer.parseInt(personComboBox.getValue().split(Pattern.quote("."))[0]),
                        startDate.getValue(), endDate.getValue());

                //add the new booking to the list of bookings
                Main.mainController.bookingArrayList.add(newBooking);
                //add the new booking to the side menu
                bookingItems.add(new BookingItem(newBooking));
                Main.mainController.sideMenu.getContent().getChildren()
                        .add(Main.mainController.sideMenu.getItemList()
                                .get(Main.mainController.sideMenu.getItemList().size() - 1));
                Label informationLabel = new Label(LocalDate.now().toString() +
                        ": registered booking #" + newBooking.getId());
                informationLabel.setId("activity-log-content");
                Main.mainController.activityMenu.getContent().getChildren().add(informationLabel);
                Main.writeBookings();
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