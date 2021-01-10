public class Customer {
    private String name;
    private int id;
    private String country;
    private String phoneNumber;
    private String NID;
    private String email;
    private static int overallId = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static int getOverallId() {
        return overallId;
    }

    public static void setOverallId(int overallId) {
        Customer.overallId = overallId;
    }

    public Customer(int id, String name, String country, String phoneNumber, String NID, String email){
        ++overallId;
        this.id = id;
        this.name = name;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.NID = NID;
        this.email = email;
    }

    public Customer(String name){
        this(++overallId, name, null, null, null, null);
    }

    public Customer(int id){
        this(id, null, null, null, null, null);
    }

    public Customer(){
        this(null);
    }
}
