import java.util.Date;

public class Booking {
    Room bookedRoom;
    Receptionist bookingReceptionist;
    Person bookingPerson;
    Date bookingStart;
    Date bookingEnd;

    public Booking(int room, int receptionist, int person, Date bookingStart, Date bookingEnd){
        this.bookedRoom = new Room();
        this.bookingReceptionist = new Receptionist();
        this.bookingPerson = new Person(person);
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public Booking(int room, int receptionist, int person) {
        this(room, receptionist, person, null, null);
    }

    public Booking(){
        this(0, 0, 0, null, null);
    }
}