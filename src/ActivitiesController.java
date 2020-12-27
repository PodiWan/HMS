import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActivitiesController extends VBox {

    public ActivitiesController() {


        Button btnAddPerson = new Button();
        btnAddPerson.setText("\uD83D\uDEC5");
        btnAddPerson.setTooltip(new Tooltip("Add a new customer"));

        Button btnTasks = new Button();
        btnTasks.setText("\uD83D\uDCDD");
        btnTasks.setTooltip(new Tooltip("Add a new booking"));

        Button btnCalendar = new Button();
        btnCalendar.setText("\uD83D\uDCC5");
        btnCalendar.setTooltip(new Tooltip("Add a new customer"));

        btnAddPerson.setOnAction(e -> {
            CustomerMenu cm = new CustomerMenu();
            Stage dialogStage = new Stage();
            cm.start(dialogStage);
        });

//        this.getChildren().add(btnAddBooking);
        this.getChildren().add(btnAddPerson);
        this.getChildren().add(btnTasks);
        this.getChildren().add(btnCalendar);

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