public class Task {
    int id;
    String text;

    Task(int id, String text){
        this.id = id;
        this.text = text;
    }

    Task(){
        this(0, null);
    }
}
