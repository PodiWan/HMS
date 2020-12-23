import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;

public class MainForm {

    //TODO add map of the hotel for each floor

    private double xFormPosition;
    private double yFormPosition;

    private double xPos = 0;
    private double yPos = 0;

    private double formFullWidth;
    private double formFullHeight;

    private double formMinWidth;
    private double formMinHeight;

    private SideMenu sideMenu;
    private BookingController bcMenu;

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

    public void start(Stage primaryStage) throws Exception{
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

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setStyle("-fx-background-color: #DDDDDD");

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
        btnClose.setOnAction(e -> {
            primaryStage.close();
        });
        btnClose.setStyle("-fx-background-radius: 0px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        Button btnState = new Button();
        btnState.setText("\u2752");
        btnState.setOnAction(e -> {
            resize(primaryStage);
        });
        btnState.setStyle("-fx-background-radius: 0px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        Button btnMin = new Button();
        btnMin.setText("\u2500");
        btnMin.setOnAction(e -> {
            primaryStage.setIconified(true);
        });
        btnMin.setStyle("-fx-background-radius: 0px; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        topBar.getChildren().add(btnMin);
        topBar.getChildren().add(btnState);
        topBar.getChildren().add(btnClose);

        ScrollPane sp = new ScrollPane();
        sideMenu = new SideMenu();
        sp.setContent(sideMenu);
        sp.setStyle("-fx-background-color: #5BD7C0");

        sideMenu.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        sideMenu.setPrefHeight(Region.USE_COMPUTED_SIZE);
        borderPane.setLeft(sp);
        borderPane.setTop(topBar);

        BookingItem b = new BookingItem("Text aaaaaaaaaa");
        sideMenu.bookingItemList.add(b);
        sideMenu.bookingItemList.add(new BookingItem());

        bcMenu = new BookingController(sideMenu);
        bcMenu.prefWidthProperty().bind(sideMenu.widthProperty());
        sideMenu.getChildren().add(bcMenu);

        for(int i = 0; i < sideMenu.bookingItemList.size(); ++i) {
            sideMenu.bookingItemList.get(i).prefWidthProperty().bind(sideMenu.widthProperty());
            sideMenu.getChildren().add(sideMenu.bookingItemList.get(i));
        }

        primaryStage.setScene(s);
        primaryStage.show();
    }
}
