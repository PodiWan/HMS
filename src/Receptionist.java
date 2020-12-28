public class Receptionist {
    private int id;
    private static int overallId;
    private boolean isAdmin;
    private String name;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getOverallId() {
        return overallId;
    }

    public static void setOverallId(int overallId) {
        Receptionist.overallId = overallId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
