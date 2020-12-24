import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SideMenu extends VBox {
    ArrayList<BookingItem> bookingItemList;

    public SideMenu() {

        this.setStyle("-fx-background-color: #5BD7C0");
        this.setAlignment(Pos.TOP_LEFT);

        bookingItemList = new ArrayList<BookingItem>();
    }
}
