public class Person {
    public String name;
    public int id;
    public static int overallId = 0;

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Person(String name){
        this(++overallId, name);
    }

    public Person(int id){
        this(id, null);
    }

    public Person(){
        this(null);
    }
}
