import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainForm mainForm = new MainForm();
        LoginForm loginForm = new LoginForm();
        loginForm.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
