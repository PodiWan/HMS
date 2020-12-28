public class Room {
    //TODO maybe refactor to switch to private

    public int id;
    public static int overallId = 0;
    public int floor;
    public int number;
    public int price;

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