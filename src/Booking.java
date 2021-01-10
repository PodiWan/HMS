import java.time.LocalDate;

public class Booking {
    private int id;
    private static int overallId = 0;
    private Room bookedRoom;
    private Receptionist bookingReceptionist;
    private Customer bookingCustomer;
    private LocalDate bookingStart;
    private LocalDate bookingEnd;

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
        Booking.overallId = overallId;
    }

    public Room getBookedRoom() {
        return bookedRoom;
    }

    public void setBookedRoom(Room bookedRoom) {
        this.bookedRoom = bookedRoom;
    }

    public Receptionist getBookingReceptionist() {
        return bookingReceptionist;
    }

    public void setBookingReceptionist(Receptionist bookingReceptionist) {
        this.bookingReceptionist = bookingReceptionist;
    }

    public Customer getBookingPerson() {
        return bookingCustomer;
    }

    public void setBookingPerson(Customer bookingCustomer) {
        this.bookingCustomer = bookingCustomer;
    }

    public LocalDate getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(LocalDate bookingStart) {
        this.bookingStart = bookingStart;
    }

    public LocalDate getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(LocalDate bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    public Booking(int id, int room, int receptionist, int person, LocalDate bookingStart, LocalDate bookingEnd){
        ++overallId;
        this.id = id;
        this.bookedRoom = Main.mainController.roomArrayList.get(room - 1);
        this.bookingReceptionist = new Receptionist();
        this.bookingCustomer = Main.mainController.customerArrayList.get(person - 1);
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }

    public Booking(int room, int receptionist, int person, LocalDate bookingStart, LocalDate bookingEnd) {
        this(overallId, room, receptionist, person, bookingStart, bookingEnd);
    }

    public Booking(int room, int receptionist, int person) {
        this(overallId, room, receptionist, person, null, null);
    }

    public Booking(){
        this(overallId, 1, 0, 1, null, null);
    }
}