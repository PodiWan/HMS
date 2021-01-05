import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class TaskItem extends Pane {

    private Task heldTask;
    public Task getHeldTask() {
        return heldTask;
    }
    public void setHeldTask(Task heldTask) {
        this.heldTask = heldTask;
    }

    public TaskItem(Task task){
        this.heldTask = task;

        Label taskComponent = new Label((Main.mainController.taskMenu.getContent().getChildren().size() + 1) + ". "
                + task.getText());
        Button btnRemove = new Button("\uD83D\uDDD1");
        Button btnDone = new Button("✓");
        HolderPane buttonHolder = new HolderPane(btnDone, btnRemove, true);
        HolderPane taskHolder = new HolderPane(taskComponent, buttonHolder, true);
        taskHolder.setId("task-content");
        taskHolder.prefWidthProperty().bind(Main.mainController.taskMenu.widthProperty());

        this.getChildren().add(taskHolder);

        btnDone.setOnAction(btnClick -> {
            for(int i = 0; i < Main.mainController.taskArrayList.size(); ++i)
                if(Main.mainController.taskArrayList.get(i).getId() == this.heldTask.getId()){
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": done task " + Main.mainController.taskArrayList.get(i).getText());
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.getContent().getChildren().add(informationLabel);
                    Main.mainController.taskArrayList.remove(this.heldTask);
                    Main.mainController.taskMenu.getContent().getChildren().remove(i);
                    if(this.heldTask.getAssignedRoom() != null) {
                        this.heldTask.getAssignedRoom().setState(Room.State.CLEAN);
                    }
                    Main.writeTasks();
                    Main.writeRooms();
                    MainForm.hm.updateMap();
                }
        });

        btnRemove.setOnAction(btnClick -> {
            for(int i = 0; i < Main.mainController.taskArrayList.size(); ++i)
                if(Main.mainController.taskArrayList.get(i).getId() == this.heldTask.getId()){
                    Label informationLabel = new Label(LocalDate.now().toString() +
                            ": deleted task " + Main.mainController.taskArrayList.get(i).getText());
                    informationLabel.setId("activity-log-content");
                    Main.mainController.activityMenu.getContent().getChildren().add(informationLabel);
                    Main.mainController.taskArrayList.remove(i);
                    Main.mainController.taskMenu.getContent().getChildren().remove(i);
                    Main.writeTasks();
                    Main.writeRooms();
                    MainForm.hm.updateMap();
                }
        });
    }
}
