package frms.model;

public class Passenger {

    private String name;
    private int age;
    private String dOB;
    private String nationality;
    private String idNo;
    private String passportNo;
    private boolean needSpecialAssistance;
    private Passenger next;
    private Passenger previous;


    public Passenger(String name, int age, String dOB, String nationality, String idNo, String passportNo) {
        this.name = name;
        this.age = age;
        this.dOB = dOB;
        this.nationality = nationality;
        this.idNo = idNo;
        this.passportNo = passportNo;
        this.needSpecialAssistance = age == 1 || age == 2 || age > 65;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public boolean isNeedSpecialAssistance() {
        return needSpecialAssistance;
    }

    public void setNeedSpecialAssistance(boolean needSpecialAssistance) {
        this.needSpecialAssistance = needSpecialAssistance;
    }

    public Passenger getNext() {
        return next;
    }

    public void setNext(Passenger next) {
        this.next = next;
    }

    public Passenger getPrevious() {
        return previous;
    }

    public void setPrevious(Passenger previous) {
        this.previous = previous;
    }

}
