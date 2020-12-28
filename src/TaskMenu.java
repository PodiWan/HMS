import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TaskMenu {

    public void start(Stage dialogStage){
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
        Label taskIdLabel = new Label("Insert task:\n#" + (Main.mainController.taskMenu.getContent().getChildren().size() + 1));
        leftPane.setCenter(taskIdLabel);

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
        Label taskLabel = new Label("Task");
        TextArea taskField = new TextArea();
        taskField.setPromptText("i.e.: clean a certain room");
        taskField.prefWidthProperty().bind(detailsPane.widthProperty().multiply(0.95));

        HolderPane taskHolderPane = new HolderPane(taskLabel, taskField, false);

        detailsPane.add(taskHolderPane, 0, 0);

        root.add(leftPane, 0, 0);
        root.add(wrapperPane, 1, 0);

        Button btnClose = new Button("Cancel");
        btnClose.setOnAction(e -> dialogStage.close());

        double initHeight = 570;
        Scene s = new Scene(root, 600, initHeight);

        Label errorHeader = new Label("Error!");
        errorHeader.getStyleClass().add("error-label");
        Label errorContent = new Label();
        errorContent.getStyleClass().add("error-label");
        HolderPane errorHolderPane = new HolderPane(errorHeader, errorContent, false);
        errorHolderPane.setVisible(false);

        detailsPane.add(errorHolderPane, 0, 1);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
            if(taskField.getText().equals("")){
                errorContent.setWrapText(true);
                errorContent.setText("No task inputted.");
                errorHeader.setTooltip(new Tooltip("No task inputted."));
                errorHolderPane.setVisible(true);
            }
            else{
                Task newTask = new Task(Main.mainController.taskMenu.getContent().getChildren().size() + 1,
                        taskField.getText());
                Main.mainController.taskArrayList.add(newTask);
                Main.mainController.taskMenu.getContent().getChildren().add(new TaskItem(newTask));
                dialogStage.close();
            }
        });

        HolderPane buttonHolderPane = new HolderPane(btnClose, btnSubmit, true);

        detailsPane.add(buttonHolderPane, 0, 3);

        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setScene(s);
        dialogStage.showAndWait();
    }
}
