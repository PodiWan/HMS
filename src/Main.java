import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
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
        assert jsonObject != null;
        JSONArray receptionistJSON = ((JSONArray)jsonObject.get("receptionist"));
        for(var receptionist : receptionistJSON){
            JSONObject receptionistObject = (JSONObject) receptionist;
            mainController.receptionistArrayList.add(new Receptionist(Integer.parseInt(receptionistObject.get("id").toString()),
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
        assert jsonObject != null;
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
        assert jsonObject != null;
        JSONArray roomJSON = ((JSONArray)jsonObject.get("room"));
        for(var room : roomJSON){
            JSONObject roomObject = (JSONObject) room;
            if(Integer.parseInt(roomObject.get("floor").toString()) > Main.mainController.numberOfFloors)
                Main.mainController.numberOfFloors = Integer.parseInt(roomObject.get("floor").toString());
            Room activeRoom = new Room(Integer.parseInt(roomObject.get("id").toString()),
                    Integer.parseInt(roomObject.get("floor").toString()),
                    Integer.parseInt(roomObject.get("number").toString()),
                    Integer.parseInt(roomObject.get("price").toString()));
            activeRoom.setState(roomObject.get("state").toString());
            mainController.roomArrayList.add(activeRoom);
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
        assert jsonObject != null;
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

    public void readTasks(){
        JSONObject jsonObject = null;
        try {
            jsonObject = readJson("src/json/task.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the receptionists from the json add them to the receptionArrayList
        assert jsonObject != null;
        JSONArray tasksJSON = ((JSONArray)jsonObject.get("task"));
        for(var booking : tasksJSON){
            JSONObject taskObject = (JSONObject) booking;
            mainController.taskArrayList.add(new Task(Integer.parseInt(taskObject.get("id").toString()),
                    taskObject.get("text").toString()));
        }
    }

    public void writeReceptionists(){
        JSONObject receptionistFile = new JSONObject();
        JSONArray receptionists = new JSONArray();
        for(var receptionist : Main.mainController.receptionistArrayList) {
            JSONObject receptionistDetails = new JSONObject();
            receptionistDetails.put("id", receptionist.getId());
            receptionistDetails.put("name", receptionist.getName());
            receptionistDetails.put("password", receptionist.getPassword());

            receptionists.add(receptionistDetails);
        }
        receptionistFile.put("receptionist", receptionists);

        try (FileWriter file = new FileWriter("src/json/receptionist.json")) {
            file.write(receptionistFile.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomers(){
        JSONObject customerFile = new JSONObject();
        JSONArray customers = new JSONArray();
        for(var customer : Main.mainController.personArrayList) {
            JSONObject customerDetails = new JSONObject();
            customerDetails.put("id", customer.getId());
            customerDetails.put("name", customer.getName());
            customerDetails.put("country", customer.getCountry());
            customerDetails.put("phone-number", customer.getPhoneNumber());
            customerDetails.put("NID", customer.getNID());
            customerDetails.put("email", customer.getEmail());

            customers.add(customerDetails);
        }
        customerFile.put("person", customers);

        try (FileWriter file = new FileWriter("src/json/person.json")) {
            file.write(customerFile.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBookings(){
        JSONObject fileObject = new JSONObject();
        JSONArray objects = new JSONArray();
        for(var booking : Main.mainController.bookingArrayList) {
            JSONObject objectDetails = new JSONObject();
            objectDetails.put("id", booking.getId());
            objectDetails.put("room", booking.getBookedRoom().getId());
            objectDetails.put("receptionist", booking.getBookingReceptionist().getId());
            objectDetails.put("person", booking.getBookingPerson().getId());
            objectDetails.put("start-date", booking.getBookingStart().toString());
            objectDetails.put("end-date", booking.getBookingEnd().toString());

            objects.add(objectDetails);
        }
        fileObject.put("booking", objects);

        try (FileWriter file = new FileWriter("src/json/booking.json")) {
            file.write(fileObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTasks(){
        JSONObject fileObject = new JSONObject();
        JSONArray objects = new JSONArray();
        for(var task : Main.mainController.taskArrayList) {
            JSONObject objectDetails = new JSONObject();
            objectDetails.put("id", task.getId());
            objectDetails.put("text", task.getText());

            objects.add(objectDetails);
        }
        fileObject.put("task", objects);

        try (FileWriter file = new FileWriter("src/json/task.json")) {
            file.write(fileObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeRooms(){
        JSONObject fileObject = new JSONObject();
        JSONArray objects = new JSONArray();
        for(var room : Main.mainController.roomArrayList) {
            JSONObject objectDetails = new JSONObject();
            objectDetails.put("id", room.getId());
            objectDetails.put("floor", room.getFloor());
            objectDetails.put("number", room.getNumber());
            objectDetails.put("price", room.getPrice());
            objectDetails.put("state", room.getStateString());

            objects.add(objectDetails);
        }
        fileObject.put("room", objects);

        try (FileWriter file = new FileWriter("src/json/room.json")) {
            file.write(fileObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage){

        readReceptionists();
        readCustomers();
        readRooms();
        readBookings();
        readTasks();

        mainController.activeReceptionist = null;

        LoginForm loginForm = new LoginForm();
        loginForm.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
