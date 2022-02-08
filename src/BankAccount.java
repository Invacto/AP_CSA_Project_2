import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private List<String> transactions;

    private Bank bank;
    private Person person;

    private DebitCard debitCard;
    private Checking checking;
    private Savings savings;

    public BankAccount(Bank bank, Person person) {
        this.bank = bank;
        this.person = person;

        transactions = new ArrayList<>();
        debitCard = null;
        checking = null;
        savings = null;
    }

    public void appendTransaction(String transaction) {
        transactions.add(transaction);
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public Checking getChecking() {
        return checking;
    }

    public Savings getSavings() {
        return savings;
    }

    public void setDebitCard(DebitCard debitCard) {
        this.debitCard = debitCard;
    }

    public void setChecking(Checking checking) {
        this.checking = checking;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public Person getPerson() {
        return person;
    }
}
