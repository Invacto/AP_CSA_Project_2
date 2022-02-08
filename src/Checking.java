public class Checking extends Account {

    private double balance;
    private double overdraftFee;

    public Checking(Bank bank, Person person, double balance, double overdraftFee) {
        super(bank, person);
        this.balance = balance;
        this.overdraftFee = overdraftFee;
    }

    public double getBalance() {
        return balance;
    }

    public void withdrawMoney(double amount) {
        balance -= amount;
    }

    public void depositMoney(double amount) {
        balance += amount;
    }

    public boolean wireTransfer(Account account, double amount) {

        if (amount > balance) return false;

        balance -= amount;
        account.depositMoney(amount);

        return true;
    }
}