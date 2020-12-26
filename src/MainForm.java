import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.*;
import java.util.Collections;

public class MainForm {

    static HotelMap hm;

    private double xFormPosition;
    private double yFormPosition;

    private double xPos = 0;
    private double yPos = 0;

    private double formFullWidth;
    private double formFullHeight;

    private double formMinWidth;
    private double formMinHeight;

    public void resize(Stage stage){
        if(stage.getWidth() == formFullWidth && stage.getHeight() == formFullHeight) {
            stage.setWidth(formMinWidth);
            stage.setHeight(formMinHeight);
            stage.setX(100);
            stage.setY(100);
        }
        else{
            stage.setWidth(formFullWidth);
            stage.setHeight(formFullHeight);
            stage.setX(0);
            stage.setY(0);
        }
    }

    public void start(Stage primaryStage){
        formFullWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        formFullHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

        xFormPosition = 0;
        yFormPosition = 0;

        formMinWidth = 800;
        formMinHeight = 600;

        primaryStage.setX(0);
        primaryStage.setY(0);

        BorderPane borderPane = new BorderPane();
        Scene s = new Scene(borderPane, formFullWidth, formFullHeight);
        s.getStylesheets().add("css/main.css");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setStyle("-fx-background-color: #F3ECE2");

        GridPane headerBar = new GridPane();

        topBar.setOnMousePressed(mouseEvent -> {
            xFormPosition = primaryStage.getX();
            yFormPosition = primaryStage.getY();
            xPos = mouseEvent.getScreenX();
            yPos = mouseEvent.getScreenY();
        });

        topBar.setOnMouseDragged(mouseEvent -> {
            if(primaryStage.getWidth() == formFullWidth && primaryStage.getHeight() == formFullHeight) {
                primaryStage.setWidth(formMinWidth);
                primaryStage.setHeight(formMinHeight);
                primaryStage.setX(mouseEvent.getScreenX() - xPos * formMinWidth / formFullWidth);
                primaryStage.setY(mouseEvent.getScreenY() - yPos);

                xFormPosition = primaryStage.getX();
                yFormPosition = primaryStage.getY();
            }
            else{
                primaryStage.setX(xFormPosition + mouseEvent.getScreenX() - xPos);
                primaryStage.setY(yFormPosition + mouseEvent.getScreenY() - yPos);
            }
        });

        Button btnClose = new Button();
        btnClose.setText("\u2613");
        btnClose.setOnAction(e -> primaryStage.close());
        btnClose.setId("btn-close");

        Button btnState = new Button();
        btnState.setText("\u2752");
        btnState.setOnAction(e -> resize(primaryStage));

        Button btnMin = new Button();
        btnMin.setText("\u2500");
        btnMin.setOnAction(e -> primaryStage.setIconified(true));

        Text headerTitle = new Text("HMS");
        headerTitle.setId("header-title");
        headerBar.prefWidthProperty().bind(topBar.widthProperty().subtract(btnMin.widthProperty().multiply(3.5)));
        headerBar.add(headerTitle, 0, 0);

        topBar.getChildren().add(headerBar);
        topBar.getChildren().add(btnMin);
        topBar.getChildren().add(btnState);
        topBar.getChildren().add(btnClose);
        
        Main.mainController.sideMenu.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        Main.mainController.sideMenu.setPrefHeight(Region.USE_COMPUTED_SIZE);

        Main.mainController.sideMenu.content.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        Main.mainController.sideMenu.content.setPrefHeight(Region.USE_COMPUTED_SIZE);
        borderPane.setLeft(Main.mainController.sideMenu);
        borderPane.setTop(topBar);

        BookingController bcMenu = new BookingController(Main.mainController.sideMenu);
        bcMenu.setId("booking-controller");
        bcMenu.prefWidthProperty().bind(Main.mainController.sideMenu.content.widthProperty());

        Main.mainController.sideMenu.setId("side-menu");

        //insert booking controller
        Main.mainController.sideMenu.getChildren().add(bcMenu);
        //reverse list of children since the prior instruction adds the controller at the end of the list of children
        //visually, this means that the controller is at the bottom, so the swap is necessary for design purposes
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(Main.mainController.sideMenu.getChildren());
        Collections.swap(workingCollection, 0, 1);
        Main.mainController.sideMenu.getChildren().setAll(workingCollection);

        for(int i = 0; i < Main.mainController.sideMenu.bookingItemList.size(); ++i) {
            Main.mainController.sideMenu.bookingItemList.get(i).prefWidthProperty()
                    .bind(Main.mainController.sideMenu.content.widthProperty());
            Main.mainController.sideMenu.content.getChildren().add(Main.mainController.sideMenu.bookingItemList.get(i));
        }

        hm = new HotelMap();
        hm.activeFloor = 0;
        hm.getStyleClass().clear();
        borderPane.setCenter(hm);

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
