import javafx.scene.Node;
import javafx.scene.layout.*;

public class HolderPane extends GridPane {
    HolderPane(Node firstNode, Node secondNode, boolean horizontal){
        this.setVgap(5);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().add(c1);

        if(!horizontal) {
            this.add(firstNode, 0, 0);
            this.add(secondNode, 0, 1, 2, 2);
        }
        else{
            this.add(firstNode, 0, 0);
            this.add(secondNode, 1, 0);
            GridPane.setColumnIndex(secondNode, 1);
        }
    }
}
