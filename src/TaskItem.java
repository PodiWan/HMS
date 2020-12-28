import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class TaskItem extends Pane {
    Task heldTask;

    public TaskItem(Task task){
        this.heldTask = task;

        Label taskComponent = new Label((Main.mainController.taskMenu.content.getChildren().size() + 1) + ". "
                + task.text);
        Button btnRemove = new Button("\uD83D\uDDD1");
        Button btnDone = new Button("✓");
        HolderPane buttonHolder = new HolderPane(btnDone, btnRemove, true);
        HolderPane taskHolder = new HolderPane(taskComponent, buttonHolder, true);
        taskHolder.setId("task-content");
        taskHolder.prefWidthProperty().bind(Main.mainController.taskMenu.widthProperty());

        this.getChildren().add(taskHolder);

        btnDone.setOnAction(btnClick -> {
            for(int i = 0; i < Main.mainController.taskArrayList.size(); ++i)
                if(Main.mainController.taskArrayList.get(i).id == this.heldTask.id){
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": done task " + Main.mainController.taskArrayList.get(i).text);
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.content.getChildren().add(informationLabel);
                    Main.mainController.taskArrayList.remove(i);
                    Main.mainController.taskMenu.content.getChildren().remove(i);
                }
        });

        btnRemove.setOnAction(btnClick -> {
            for(int i = 0; i < Main.mainController.taskArrayList.size(); ++i)
                if(Main.mainController.taskArrayList.get(i).id == this.heldTask.id){
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": deleted task " + Main.mainController.taskArrayList.get(i).text);
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.content.getChildren().add(informationLabel);
                    Main.mainController.taskArrayList.remove(i);
                    Main.mainController.taskMenu.content.getChildren().remove(i);
                }
        });
    }
}
