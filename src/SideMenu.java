import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SideMenu extends VBox {
    private ArrayList<Pane> itemList;
    private VBox content;

    public ArrayList<Pane> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Pane> itemList) {
        this.itemList = itemList;
    }

    public VBox getContent() {
        return content;
    }

    public void setContent(VBox content) {
        this.content = content;
    }

    public SideMenu() {
        content = new VBox();
        content.prefWidthProperty().bind(this.widthProperty());
        content.setStyle("-fx-background-color: #FCFCFC");
        content.setPadding(new Insets(10, 0, 0, 0));

        ScrollPane sp = new ScrollPane();
        sp.setContent(this.content);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle("-fx-background-color:transparent;");
        sp.setFitToWidth(true);

        this.setAlignment(Pos.TOP_LEFT);
        itemList = new ArrayList<>();

        this.setPadding(new Insets(0, 0, 5, 0));

        this.getChildren().add(sp);
    }
}
