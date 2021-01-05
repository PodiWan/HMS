import javafx.scene.control.Label;

import java.time.LocalDate;

public class Room {

    public enum State{
        CLEAN,
        TO_CLEAN,
        UNDER_CONSTRUCTION
    }

    private int id;
    private static int overallId = 0;
    private int floor;
    private int number;
    private int price;
    private State state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getOverallId() {
        return overallId;
    }

    public static void setOverallId(int overallId) {
        Room.overallId = overallId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public State getState() {
        return state;
    }

    public String getStateString(){
        if(state == State.CLEAN)
            return "Clean";
        else if(state == State.TO_CLEAN)
            return "To clean";
        return "Under construction";
    }

    public void setState(State state) {
        this.state = state;

        if(this.state == State.TO_CLEAN) {
            Task newTask = new Task();
            newTask.setText("Clean room: " + this.number);
            newTask.setAssignedRoom(this);
            Main.mainController.taskArrayList.add(newTask);
            Main.mainController.taskMenu.getContent().getChildren().add(new TaskItem(newTask));
            Main.writeTasks();
        }

        if(this.state == State.CLEAN){
            for(int i = 0; i < Main.mainController.taskArrayList.size(); ++i){
                if(Main.mainController.taskArrayList.get(i).getAssignedRoom() == this){
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": done task " + Main.mainController.taskArrayList.get(i).getText());
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.getContent().getChildren().add(informationLabel);
                    Main.mainController.taskArrayList.remove(i);
                    Main.mainController.taskMenu.getContent().getChildren().remove(i);
                    Main.writeTasks();
                }
            }
        }

        MainForm.hm.updateMap();
        MainForm.hm.highlightRoom(this);
    }

    public void setState(String state){
        if(state.equals("clean"))
            this.setState(State.CLEAN);
        else if(state.equals("not-clean"))
            this.setState(State.TO_CLEAN);
        else if(state.equals("underconstruction"))
            this.setState(State.UNDER_CONSTRUCTION);
    }

    Room(int id, int floor, int number, int price){
        ++overallId;
        this.id = id;
        this.floor = floor;
        this.number = number;
        this.price = price;
        this.state = State.CLEAN;
    }

    Room(int floor, int number, int price){
        this(overallId, floor, number, price);
    }

    Room(int id){
        this(id, 0, 0, 0);
    }

    Room(){
        this(overallId,0, 0, 0);
    }
}