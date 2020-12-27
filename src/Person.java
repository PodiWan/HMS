public class Person {
    public String name;
    public int id;
    public String country;
    public String phoneNumber;
    public String NID;
    public String email;
    public static int overallId = 0;

    public Person(int id, String name, String country, String phoneNumber, String NID, String email){
        ++overallId;
        this.id = id;
        this.name = name;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.NID = NID;
        this.email = email;
    }

    public Person(String name){
        this(++overallId, name, null, null, null, null);
    }

    public Person(int id){
        this(id, null, null, null, null, null);
    }

    public Person(){
        this(null);
    }
}
