import java.util.Date;

public class Booking {

    int id;
    static int overallId = 0;
    Room bookedRoom;
    Receptionist bookingReceptionist;
    Person bookingPerson;
    Date bookingStart;
    Date bookingEnd;

    public Booking(int id, int room, int receptionist, int person, Date bookingStart, Date bookingEnd){
        this.id = id;
        this.bookedRoom = Main.mainController.roomArrayList.get(room - 1);
        this.bookingReceptionist = new Receptionist();
        this.bookingPerson = Main.mainController.personArrayList.get(person);
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public Booking(int room, int receptionist, int person, Date bookingStart, Date bookingEnd) {
        this(++overallId, room, receptionist, person, bookingStart, bookingEnd);
    }

    public Booking(int room, int receptionist, int person) {
        this(++overallId, room, receptionist, person, null, null);
    }

    public Booking(){
        this(0, 0, 0, 0, null, null);
    }
}