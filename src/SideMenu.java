import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SideMenu extends VBox {
    ArrayList<BookingItem> bookingItemList;

    VBox content;

    public SideMenu() {
        content = new VBox();
        content.prefWidthProperty().bind(this.widthProperty());
        content.setStyle("-fx-background: #637AA6;");

        ScrollPane sp = new ScrollPane();
        sp.setContent(this.content);
        sp.setStyle("-fx-background: #637AA6;");
        sp.setFitToWidth(true);

        this.setAlignment(Pos.BASELINE_LEFT);
        bookingItemList = new ArrayList<BookingItem>();

        this.getChildren().add(sp);
    }
}
