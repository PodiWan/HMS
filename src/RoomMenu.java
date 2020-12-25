import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class RoomMenu {

    ComboBox<String> floor = new ComboBox<>();
    ComboBox<String> number = new ComboBox<>();
    ComboBox<String> price = new ComboBox<>();

    String prettyPrintFloor(int floorNumber){
        int lastDigit = floorNumber % 10;
        switch (lastDigit){
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    RoomMenu(){
        for(int i = 0; i < Main.mainController.numberOfFloors; ++i)
            floor.getItems().add((i + 1) + prettyPrintFloor(i + 1));

        for(var roomIterator : Main.mainController.roomArrayList){
            number.getItems().add(Integer.toString(roomIterator.number));
        }
    }

    public boolean start(){
        Dialog<Room> roomDialog = new Dialog<>();
        roomDialog.setTitle("Add room");
        roomDialog.setHeaderText("Insert data about the newly added room");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 10));

        floor.setPromptText("Floor");
        number.setPromptText("Room number");
        price.setPromptText("Price per night");

        grid.add(new Label("Floor:"), 0, 0);
        grid.add(floor, 1, 0);
        grid.add(new Label("Room number:"), 0, 1);
        grid.add(number, 1, 1);
        grid.add(new Label("Price per night:"), 0, 2);
        grid.add(price, 1, 2);

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        roomDialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);
        roomDialog.getDialogPane().setContent(grid);
        roomDialog.setResultConverter(dialogButton -> {
            if(dialogButton == submitButtonType){
                return null;
            }
            return null;
        });
        return roomDialog.showAndWait().isPresent();
    }
}
