public class Task {
    private int id;
    private String text;
    private Room assignedRoom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Room getAssignedRoom() {
        return assignedRoom;
    }

    public void setAssignedRoom(Room assignedRoom) {
        this.assignedRoom = assignedRoom;
    }

    Task(int id, String text){
        this.id = id;
        this.text = text;
    }

    Task(){
        this(0, null);
    }
}
