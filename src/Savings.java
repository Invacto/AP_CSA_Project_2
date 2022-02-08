public class Savings extends Account {

    private double balance;
    private double interestRate;
    private double minBalance;
    private double fee;

    public Savings(Bank bank,Person person, double balance, double interestRate, double minBalance, double fee) {
        super(bank, person);
        this.balance = balance;
        this.interestRate = interestRate;
        this.minBalance = minBalance;
        this.fee = fee;
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

    public double getBalance() {
        return balance;
    }
}
