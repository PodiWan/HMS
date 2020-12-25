import javafx.animation.FillTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HotelMap extends GridPane {

    class RoomTile extends BorderPane {

        private int roomId;
        private Label roomNumberLabel;

        public void setRoomId(int id){
            this.roomId = id;
        }

        public void setRoomNumber(int number){
            this.roomNumberLabel.setText(Integer.toString(number));
        }

        RoomTile(HotelMap parent){
            roomNumberLabel = new Label();

            roomNumberLabel.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat");
            roomNumberLabel.setStyle("-fx-font-family: Montserrat;");

            DoubleProperty labelFontSize = new SimpleDoubleProperty(5);
            labelFontSize.bind(this.widthProperty().add(this.heightProperty()).divide(10));
            roomNumberLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", labelFontSize, ";"));

            this.setStyle("-fx-background-color: #BBB4BF; -fx-border-color: #000000");
            this.prefWidthProperty().bind(parent.widthProperty().divide(5));
            this.prefHeightProperty().bind(parent.heightProperty().divide(10));

            this.setCenter(this.roomNumberLabel);
        }
    }

    int activeFloor;
    Label activeFloorLabel;
    Button btnUp;
    Button btnDown;

    void updateLabel(){
        activeFloorLabel.setText("Floor: " + activeFloor);
    }

    void updateMap(){
        updateLabel();
        //remove every pane from the previous floor
        this.getChildren().removeIf(node -> node instanceof Pane);

        //counter for the rooms on the same level as activeFloor
        int roomsOnActiveFloor = 0;
        //go through all the rooms of the hotel
        for (int i = 0; i < Main.mainController.roomArrayList.size(); ++i) {

            this.setStyle("-fx-background-color: #F2EADF");
            this.setPadding(new Insets(50, 50, 10, 50));
            if(Main.mainController.roomArrayList.get(i).floor == activeFloor) {
                RoomTile roomTile = new RoomTile(this);
                roomTile.setRoomId(Main.mainController.roomArrayList.get(i).id);
                roomTile.setRoomNumber(Main.mainController.roomArrayList.get(i).number);
                this.add(roomTile, roomsOnActiveFloor++, 3);
            }
        }
    }

    void higlightRoom(Room selectedRoom){
        RoomTile highlightRoom = null;

        for(var node : this.getChildren())
            if(node instanceof RoomTile && ((RoomTile) node).roomId == selectedRoom.id)
                highlightRoom = ((RoomTile) node);
        highlightRoom.setStyle("-fx-background-color: #F1C95F");
    }

    HotelMap(){
        btnUp = new Button("⯅");
        btnDown = new Button("⯆");

        activeFloorLabel = new Label();
        activeFloorLabel.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat");
        activeFloorLabel.setStyle("-fx-font-family: Montserrat");

        DoubleProperty fontSize = new SimpleDoubleProperty(5);
        fontSize.bind(this.widthProperty().add(this.heightProperty()).divide(50));
        activeFloorLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize, ";"));
        btnUp.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                Bindings.max(fontSize.divide(2.5), 10), ";"));
        btnDown.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                Bindings.max(fontSize.divide(2.5), 10), ";"));

        btnUp.setOnAction(e -> {
            if(activeFloor < Main.mainController.numberOfFloors) {
                ++activeFloor;
                updateMap();
            }
        });

        btnDown.setOnAction(e -> {
            if(activeFloor > Main.mainController.minFloor) {
                --activeFloor;
                updateMap();
            }
        });

        this.add(activeFloorLabel, 0, 0, 1, 1);
        this.add(btnUp, 1, 0);
        this.add(btnDown, 1, 1);

        updateMap();
    }
}
