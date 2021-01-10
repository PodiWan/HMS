import java.util.ArrayList;

public class MainController {
    ArrayList<Customer> customerArrayList = new ArrayList<>();
    ArrayList<Room> roomArrayList = new ArrayList<>();
    ArrayList<Receptionist> receptionistArrayList = new ArrayList<>();
    ArrayList<Booking> bookingArrayList = new ArrayList<>();
    ArrayList<Task> taskArrayList = new ArrayList<>();
    SideMenu sideMenu = new SideMenu();
    SideMenu activityMenu = new SideMenu();
    Receptionist activeReceptionist = new Receptionist();
    SideMenu taskMenu = new SideMenu();

    int numberOfFloors;
    int minFloor = 0;
}
