import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginForm {
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
        s.getStylesheets().add("css/dialog.css");
        primaryStage.setScene(s);

        double rectWidth = s.getWidth() / 4;
        double rectHeight = s.getHeight();

        Rectangle rect = new Rectangle();
        rect.setX(0);
        rect.setY(0);
        rect.setWidth(0);
        rect.setHeight(rectHeight);
        rect.setFill(Color.web("#349AF7"));
        root.getChildren().add(rect);
        root.setStyle("-fx-background-color: #FCFCFC");

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
        grid.getStyleClass().add("login");

        Text loginText = new Text("Welcome to HMS");
        loginText.setId("login-title");
        grid.add(loginText, 0, 0, 2, 1);

        Label userText = new Label("User id: ");
        grid.add(userText, 0, 1);
        TextField userField = new TextField();
        grid.add(userField, 1, 1);

        Label passwordText = new Label("Password: ");
        grid.add(passwordText, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button btnLogin = new Button("Login");
        btnLogin.setLayoutX(primaryStage.getMaxWidth() - 20);
        EventHandler<ActionEvent> buttonClick = e -> {
            for(var user : Main.mainController.receptionistArrayList)
                if(Integer.parseInt(userField.getText()) == user.id && passwordField.getText().equals(user.password))
                    Main.mainController.activeReceptionist = user;

            if(Main.mainController.activeReceptionist != null) {
                primaryStage.close();
                MainForm mainForm = new MainForm();
                try {
                    mainForm.start(primaryStage);
                } catch (Exception exception) {
                    System.out.println(exception.toString());
                }
            }
        };
        btnLogin.setOnAction(buttonClick);

        Button btnClose = new Button("Cancel");
        btnClose.setOnAction(e -> primaryStage.close());

        HolderPane buttonHolder = new HolderPane(btnClose, btnLogin, true);
        buttonHolder.setPadding(new Insets(30, 0, 0, 0));

        grid.add(buttonHolder, 1, 3);
        root.add(grid, 1, 0);
        primaryStage.show();
    }
}
