import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CustomerMenu {

    Person newCustomer;

    public void start(Stage dialogStage){
        newCustomer = new Person();

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
        Label customerIdLabel = new Label("Insert customer:\n#" + Integer.toString(newCustomer.id));
        leftPane.setCenter(customerIdLabel);

        //wrapper pane for better display
        BorderPane wrapperPane = new BorderPane();
        wrapperPane.getStyleClass().add("details-column");
        //header
        Label headerLabel = new Label("Customer details");
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

        //fields
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane nameHolderPane = new HolderPane(nameLabel, nameField, false);

        Label countryLabel = new Label("Country");
        TextField countryField = new TextField();
        countryField.setPromptText("Country");
        countryField.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane countryHolderPane = new HolderPane(countryLabel, countryField, false);

        Label phoneLabel = new Label("Phone number");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone number");
        phoneField.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane phoneHolderPane = new HolderPane(phoneLabel, phoneField, false);

        Label errorHeader = new Label("Error!");
        errorHeader.getStyleClass().add("error-label");
        Label errorContent = new Label();
        errorContent.getStyleClass().add("error-label");
        HolderPane errorHolderPane = new HolderPane(errorHeader, errorContent, false);
        errorHolderPane.setVisible(false);

        detailsPane.add(nameHolderPane, 0, 0);
        detailsPane.add(countryHolderPane, 0, 1);
        detailsPane.add(phoneHolderPane, 0, 2);
        detailsPane.add(errorHolderPane, 0, 3);

        Button btnClose = new Button("Cancel");
        btnClose.setOnAction(e -> {
            dialogStage.close();
        });

        Scene s = new Scene(root, 650, 500);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
            String missingData = "";
            if(nameField.getText().isEmpty())
                missingData += "name";
            if(countryField.getText().isEmpty()) {
                if (!missingData.equals(""))
                    missingData += ", ";
                missingData += "country";
            }
            if(phoneField.getText().isEmpty()){
                if (!missingData.equals(""))
                    missingData += ", ";
                missingData += "phone number!";
            }
            if(!missingData.equals("")) {
                missingData = "Please provide information regarding:" + missingData;
                errorContent.setWrapText(true);
                errorContent.setText(missingData);
                errorHeader.setTooltip(new Tooltip(missingData));
                errorContent.setTooltip(new Tooltip(missingData));
                errorHolderPane.setVisible(true);
                dialogStage.setHeight(s.getHeight() + errorHolderPane.getHeight());
            }
            else{
                if(Pattern.matches("[0-9]+", phoneField.getText()) && phoneField.getText().length() >= 10
                && phoneField.getText().length() <= 15){
                    newCustomer.name = nameField.getText();
                    newCustomer.country = countryField.getText();
                    newCustomer.phoneNumber = phoneField.getText();
                    Main.mainController.personArrayList.add(newCustomer);
                    dialogStage.close();
                }
                else{
                    errorContent.setText("Incorrect phone number!");
                    errorHolderPane.setVisible(true);
                }
            }
        });

        HolderPane buttonHolderPane = new HolderPane(btnClose, btnSubmit, true);

        detailsPane.add(buttonHolderPane, 0, 5);

        root.add(leftPane, 0, 0);
        root.add(wrapperPane, 1, 0);

        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(s);
        dialogStage.showAndWait();
    }
}
