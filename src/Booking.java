import java.util.Date;

public class Booking {
    Room bookedRoom;
    Receptionist bookingReceptionist;
    Person bookingPerson;
    Date bookingStart;
    Date bookingEnd;

    public Booking(String room, String receptionist, String person) {
        this.bookedRoom = new Room();
        this.bookingReceptionist = new Receptionist();
        this.bookingPerson = new Person(person);
    }
}