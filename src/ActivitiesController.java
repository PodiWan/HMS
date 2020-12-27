import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActivitiesController extends VBox {

    public ActivitiesController(Stage primaryStage) {

        Button btnAddPerson = new Button();
        btnAddPerson.setText("\uD83D\uDEC5");
        btnAddPerson.setTooltip(new Tooltip("Add a new customer"));

        Button btnTasks = new Button();
        btnTasks.setText("\uD83D\uDCDD");
        btnTasks.setTooltip(new Tooltip("Add a new task"));

        Button btnExit = new Button();
        btnExit.setText("\uD83D\uDEAA");
        btnExit.setTooltip(new Tooltip("Exit"));

        btnAddPerson.setOnAction(e -> {
            CustomerMenu cm = new CustomerMenu();
            Stage dialogStage = new Stage();
            cm.start(dialogStage);
        });

        btnTasks.setOnAction(e -> {
            TaskMenu tm = new TaskMenu();
            Stage dialogStage = new Stage();
            tm.start(dialogStage);
        });

        btnExit.setOnAction(e -> {
            primaryStage.close();
        });

        this.getChildren().add(btnAddPerson);
        this.getChildren().add(btnTasks);
        this.getChildren().add(btnExit);

        DoubleProperty fontSize = new SimpleDoubleProperty(10);
        for (var node : this.getChildren()) {
            if(node instanceof Button){
                ((Button) node).prefHeightProperty().bind(this.heightProperty().divide(3));
                ((Button) node).prefWidthProperty().bind(this.widthProperty());
                node.setStyle("-fx-font-size: 2em");
            }
        }
    }
}