import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;

import java.time.LocalDate;

public class Main extends Application {

    public static MainController mainController = new MainController();

    public static JSONObject readJson(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(reader);
    }

    public void readReceptionists() {
        JSONObject jsonObject = null;
        try {
            jsonObject = readJson("src/json/receptionist.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the receptionists from the json add them to the receptionArrayList
        JSONArray receptionistJSON = ((JSONArray)jsonObject.get("receptionist"));
        for(var receptionist : receptionistJSON){
            JSONObject receptionistObject = (JSONObject) receptionist;
            mainController.receptionistArrayList.add(new Receptionist(Integer.parseInt(receptionistObject.get("id").toString()),
                    (receptionistObject.get("isAdmin").equals("true")),
                    receptionistObject.get("name").toString(), receptionistObject.get("password").toString()));
        }
    }

    public void readCustomers(){
        JSONObject jsonObject = null;
        try {
            jsonObject = readJson("src/json/person.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the receptionists from the json add them to the receptionArrayList
        JSONArray personJSON = ((JSONArray)jsonObject.get("person"));
        for(var person : personJSON){
            JSONObject personObject = (JSONObject) person;
            mainController.personArrayList.add(new Person(Integer.parseInt(personObject.get("id").toString()),
                    personObject.get("name").toString(), personObject.get("country").toString(),
                    personObject.get("phone-number").toString(), personObject.get("NID").toString(),
                    personObject.get("email").toString()));
        }
    }

    public void readRooms(){
        JSONObject jsonObject = null;
        try {
            jsonObject = readJson("src/json/room.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the receptionists from the json add them to the receptionArrayList
        JSONArray roomJSON = ((JSONArray)jsonObject.get("room"));
        for(var room : roomJSON){
            JSONObject roomObject = (JSONObject) room;
            if(Integer.parseInt(roomObject.get("floor").toString()) > Main.mainController.numberOfFloors)
                Main.mainController.numberOfFloors = Integer.parseInt(roomObject.get("floor").toString());
            mainController.roomArrayList.add(new Room(Integer.parseInt(roomObject.get("id").toString()),
                    Integer.parseInt(roomObject.get("floor").toString()),
                    Integer.parseInt(roomObject.get("number").toString()),
                    Integer.parseInt(roomObject.get("price").toString())));
        }
    }

    public void readBookings(){
        JSONObject jsonObject = null;
        try {
            jsonObject = readJson("src/json/booking.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the receptionists from the json add them to the receptionArrayList
        JSONArray bookingJSON = ((JSONArray)jsonObject.get("booking"));
        for(var booking : bookingJSON){
            JSONObject bookingObject = (JSONObject) booking;
            mainController.bookingArrayList.add(new Booking(Integer.parseInt(bookingObject.get("id").toString()),
                    Integer.parseInt(bookingObject.get("room").toString()),
                    Integer.parseInt(bookingObject.get("receptionist").toString()),
                    Integer.parseInt(bookingObject.get("person").toString()),
                    LocalDate.parse(bookingObject.get("start-date").toString()),
                    LocalDate.parse(bookingObject.get("end-date").toString())));
        }
    }

    @Override
    public void start(Stage primaryStage){

        readReceptionists();
        readCustomers();
        readRooms();
        readBookings();

        mainController.activeReceptionist = null;

        for(var booking : mainController.bookingArrayList)
            mainController.sideMenu.bookingItemList.add(new BookingItem(booking));

        LoginForm loginForm = new LoginForm();
        loginForm.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
