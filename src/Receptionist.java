public class Receptionist {
    //TODO make private

    public int id;
    public static int overallId;
    public boolean isAdmin;
    public String name;

    Receptionist(int id, boolean isAdmin, String name){
        this.id = id;
        this.isAdmin = isAdmin;
        this.name = name;
    }

    Receptionist(boolean isAdmin, String name){ this(++overallId, isAdmin, name); }

    Receptionist(){
        this(++overallId, false, null);
    }
}
