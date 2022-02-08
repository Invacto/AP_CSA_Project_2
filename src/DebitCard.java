import java.util.Scanner;

public class DebitCard extends Card {

    private final Scanner SCANNER = new Scanner(System.in);
    private BankAccount bankAccount;
    private Checking checking;
    private String pIN;

    public DebitCard(BankAccount bankAccount, Checking checking, String pIN) {
        super();
        this.bankAccount = bankAccount;
        this.checking = checking;
        this.pIN = pIN;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Checking getChecking() {
        return checking;
    }

    public boolean verifyPIN(String input) {
        return (input.equals(pIN));
    }

    public boolean changePIN(String currentPIN) {
        if (!verifyPIN(currentPIN)) {
            System.out.println("Current PIN is not correct.");
            return false;
        }

        System.out.print("Enter your updated PIN: ");
        pIN = SCANNER.nextLine();
        System.out.println("Your PIN has successfully been changed.");
        return true;
    }
}
