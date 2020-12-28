public class Receptionist {
    //TODO make private

    public int id;
    public static int overallId;
    public boolean isAdmin;
    public String name;
    public String password;

    Receptionist(int id, boolean isAdmin, String name, String password){
        ++overallId;
        this.id = id;
        this.isAdmin = isAdmin;
        this.name = name;
        this.password = password;
    }

    Receptionist(boolean isAdmin, String name, String password){ this(overallId, isAdmin, name, password); }

    Receptionist(){
        this(overallId, false, null, null);
    }
}
