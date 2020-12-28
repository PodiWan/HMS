public class Room {
    private int id;
    private static int overallId = 0;
    private int floor;
    private int number;
    private int price;

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

    Room(int id, int floor, int number, int price){
        ++overallId;
        this.id = id;
        this.floor = floor;
        this.number = number;
        this.price = price;
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