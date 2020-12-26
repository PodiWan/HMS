import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

public class HotelMap extends GridPane {

    static class RoomTile extends BorderPane {

        private int roomId;
        private final Label roomNumberLabel;

        public void setRoomId(int id){
            this.roomId = id;
        }

        public void setRoomNumber(int number){
            this.roomNumberLabel.setText(Integer.toString(number));
        }

        RoomTile(HotelMap parent){
            roomNumberLabel = new Label();

            this.prefWidthProperty().bind(parent.widthProperty().divide(5));
            this.prefHeightProperty().bind(parent.heightProperty().divide(10));

            Tooltip infoTooltip = new Tooltip("Relevant data");
            Tooltip.install(this, infoTooltip);

            this.setId("room-tile");
            this.setOnMouseClicked(e -> {
                if(!this.isFocused())
                    this.requestFocus();
                else
                    parent.requestFocus();
            });

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

            this.setPadding(new Insets(50, 50, 10, 50));
            if(Main.mainController.roomArrayList.get(i).floor == activeFloor) {
                RoomTile roomTile = new RoomTile(this);
                roomTile.setRoomId(Main.mainController.roomArrayList.get(i).id);
                roomTile.setRoomNumber(Main.mainController.roomArrayList.get(i).number);
                this.add(roomTile, roomsOnActiveFloor++, 3);
            }
        }
    }

    void highlightRoom(Room selectedRoom){
        RoomTile highlightRoom = null;

        for(var node : this.getChildren())
            if(node instanceof RoomTile && ((RoomTile) node).roomId == selectedRoom.id)
                highlightRoom = ((RoomTile) node);
        assert highlightRoom != null;
        highlightRoom.requestFocus();
    }

    HotelMap(){
        this.setVgap(10);

        btnUp = new Button("⯅");
        btnDown = new Button("⯆");

        activeFloorLabel = new Label();
        activeFloorLabel.setId("floor-counter");

        DoubleProperty fontSize = new SimpleDoubleProperty(5);
        fontSize.bind(this.widthProperty().add(this.heightProperty()).divide(50));

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

        this.add(activeFloorLabel, 0, 0, 1, 2);
        this.add(btnUp, 1, 0);
        this.add(btnDown, 1, 1);

        this.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        this.setId("hotel-map");

        updateMap();
    }
}
