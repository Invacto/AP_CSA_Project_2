public abstract class Account {

    private Bank bank;
    private BankAccount bankAccount;
    private Person person;

    public Account(Bank bank, Person person) {
        this.bank = bank;
        this.person = person;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Person getPerson() {
        return person;
    }

    public abstract double getBalance();

    public abstract void withdrawMoney(double amount);

    public abstract void depositMoney(double amount);

    public abstract boolean wireTransfer(Account account, double amount);
}
