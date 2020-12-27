import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

public class HotelMap extends BorderPane {

    static class RoomTile extends BorderPane {

        private int roomId;
        private final Label roomNumberLabel;
        private Booking currentBooking;

        public void setRoomId(int id){
            this.roomId = id;
        }
        public void setRoomNumber(int number){
            this.roomNumberLabel.setText(Integer.toString(number));
        }
        public void setCurrentBooking(Booking currentBooking){ this.currentBooking = currentBooking; }
        public Booking getCurrentBooking(){ return this.currentBooking; }

        Tooltip roomTooltip;

        RoomTile(HotelMap parent){
            roomNumberLabel = new Label();
            roomNumberLabel.setId("room-number");

            roomTooltip = new Tooltip("The room is not booked");
            Tooltip.install(this, roomTooltip);

            this.prefWidthProperty().bind(parent.widthProperty().divide(4));
            this.setPrefHeight(250);

            this.setId("room-tile");
            this.setOnMouseClicked(e -> {
                if(!this.isFocused()) {
                    if(this.currentBooking != null)
                        parent.highlightRoom(this.currentBooking.bookedRoom);
                }
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
    GridPane rooms;

    void updateLabel(){
        activeFloorLabel.setText("Floor: " + activeFloor);
    }

    void updateMap(){
        updateLabel();
        //remove every pane from the previous floor
        this.rooms.getChildren().removeIf(node -> node instanceof RoomTile);
        //counter for the rooms on the same level as activeFloor
        int roomsOnActiveFloor = 0;
        int firstRow = 0;
        int secondRow = 8;
        //go through all the rooms of the hotel
        for (int i = 0; i < Main.mainController.roomArrayList.size(); ++i) {
            this.setPadding(new Insets(50, 50, 10, 50));
            if(Main.mainController.roomArrayList.get(i).floor == activeFloor) {
                RoomTile roomTile = new RoomTile(this);
                roomTile.setRoomId(Main.mainController.roomArrayList.get(i).id);
                roomTile.setRoomNumber(Main.mainController.roomArrayList.get(i).number);

                for(var booking : Main.mainController.bookingArrayList)
                    if(booking.bookedRoom.id == roomTile.roomId) {
                        roomTile.setCurrentBooking(booking);
                        roomTile.roomTooltip.setText("Booked by: " + roomTile.currentBooking.bookingPerson.name +
                                "\nStart date: " + roomTile.currentBooking.bookingStart.toString() +
                                "\nEnd date: " + roomTile.currentBooking.bookingEnd.toString());
                    }

                if(roomsOnActiveFloor < 4)
                    this.rooms.add(roomTile, roomsOnActiveFloor++, firstRow);
                else
                    this.rooms.add(roomTile, roomsOnActiveFloor++ - 4, secondRow);
            }
        }
    }

    void highlightRoom(Room selectedRoom){
        RoomTile highlightRoom = null;

        for(var node : this.rooms.getChildren())
            if(node instanceof RoomTile && ((RoomTile) node).roomId == selectedRoom.id)
                highlightRoom = ((RoomTile) node);
        assert highlightRoom != null;
        highlightRoom.requestFocus();

        Label personLabel = new Label("Booked by: " + highlightRoom.currentBooking.bookingPerson.name);
        Label startDateLabel = new Label("Start date: " + highlightRoom.currentBooking.bookingStart.toString());
        Label endDateLabel = new Label("End date: " + highlightRoom.currentBooking.bookingEnd.toString());
        HolderPane hp = new HolderPane(startDateLabel, endDateLabel, false);
        HolderPane mainHp = new HolderPane(personLabel, hp, false);
        mainHp.setPadding(new Insets(10, 5, 10, 0 ));

        highlightRoom.setRight(mainHp);
        RoomTile finalHighlightRoom = highlightRoom;
        highlightRoom.focusedProperty().addListener((obs, oldVal, newVal) -> {
            finalHighlightRoom.getChildren().remove(mainHp);
        });
    }

    HotelMap(){
        rooms = new GridPane();
        this.rooms.setVgap(25);

        btnUp = new Button("⯅");
        btnDown = new Button("⯆");

        activeFloorLabel = new Label();
        activeFloorLabel.setId("floor-counter");

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

        this.setTop(activeFloorLabel);

        HolderPane buttonHolderPane = new HolderPane(btnUp, btnDown, false);
        btnUp.prefHeightProperty().bind(this.rooms.heightProperty().divide(2));
        btnDown.prefHeightProperty().bind(this.rooms.heightProperty().divide(2));
        buttonHolderPane.setPadding(new Insets(0, 0, 0, 30));
        this.setRight(buttonHolderPane);
        this.setCenter(this.rooms);

        this.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        this.setId("hotel-map");

        updateMap();
    }
}
