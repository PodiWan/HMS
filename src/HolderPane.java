import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class HolderPane extends GridPane {
    HolderPane(Node title, Node input, boolean horizontal){
        this.setVgap(5);
        this.setHgap(210);

        if(!horizontal) {
            this.add(title, 0, 0);
            this.add(input, 0, 1);
        }
        else{
            this.add(title, 0, 0);
            this.add(input, 1, 0);
        }
    }
}
