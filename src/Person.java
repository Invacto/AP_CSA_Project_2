public class Person {

    private final String ID;

    private BankAccount bankAccount;
    private String fullName;
    private int age;

    public Person(String fullName, int age, String id) {
        this.fullName = fullName;
        this.age = age;
        this.ID = id;

        bankAccount = null;

    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getID() {
        return ID;
    }

    public String toString() {
        String str =  "";
        str += "Full Name: " + fullName;
        str += "\nAge: " + age;
        str += "\nID#: " + ID;

        return str;
    }

}
