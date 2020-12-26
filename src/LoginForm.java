import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginForm {

    //TODO write credentials to file AND read them when logging in
    public void start(Stage primaryStage){
        int formWidth = 400;
        int formHeight = 275;

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setX((Screen.getPrimary().getBounds().getWidth() - formWidth) / 2);
        primaryStage.setY((Screen.getPrimary().getBounds().getHeight() - formHeight) / 2);

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);

        Scene s = new Scene(root, 500, 300);
        s.getStylesheets().add("css/main.css");
        primaryStage.setScene(s);

        double rectWidth = s.getWidth() / 4;
        double rectHeight = s.getHeight();

        Rectangle rect = new Rectangle();
        rect.setX(0);
        rect.setY(0);
        rect.setWidth(0);
        rect.setHeight(rectHeight);
        rect.setFill(Color.web("#F1C95F"));
        root.getChildren().add(rect);

        AnimationTimer timer =
                new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        if(rect.getWidth() < rectWidth){
                            rect.setWidth(rect.getWidth() + 3);
                        }
                        else this.stop();
                    }
                };
        timer.start();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text loginText = new Text("Welcome to HMS");
        loginText.setId("login-title");
        grid.add(loginText, 0, 0, 2, 1);

        Text userText = new Text("Username: ");
        grid.add(userText, 0, 1);
        TextField userField = new TextField();
        grid.add(userField, 1, 1);

        Text passwordText = new Text("Password: ");
        grid.add(passwordText, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button button = new Button("Login");
        button.setLayoutX(primaryStage.getMaxWidth() - 20);
        EventHandler<ActionEvent> buttonClick = e -> {
            primaryStage.close();
            MainForm mainForm = new MainForm();
            try {
                mainForm.start(primaryStage);
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        };
        button.setOnAction(buttonClick);
        HBox hboxButton = new HBox(10);
        hboxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hboxButton.getChildren().add(button);
        grid.add(hboxButton, 1, 3);

        root.add(grid, 1, 0);

        primaryStage.show();
    }
}
