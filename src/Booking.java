import java.time.LocalDate;

public class Booking {

    int id;
    static int overallId = 0;
    Room bookedRoom;
    Receptionist bookingReceptionist;
    Person bookingPerson;
    LocalDate bookingStart;
    LocalDate bookingEnd;

    public Booking(int id, int room, int receptionist, int person, LocalDate bookingStart, LocalDate bookingEnd){
        this.id = id;
        this.bookedRoom = Main.mainController.roomArrayList.get(room - 1);
        this.bookingReceptionist = new Receptionist();
        this.bookingPerson = Main.mainController.personArrayList.get(person - 1);
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public Booking(int room, int receptionist, int person, LocalDate bookingStart, LocalDate bookingEnd) {
        this(++overallId, room, receptionist, person, bookingStart, bookingEnd);
    }

    public Booking(int room, int receptionist, int person) {
        this(++overallId, room, receptionist, person, null, null);
    }

    public Booking(){
        this(++overallId, 1, 0, 1, null, null);
    }
}