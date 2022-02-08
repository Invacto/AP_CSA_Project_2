import java.util.List;
import java.util.Scanner;

public class ATM {

    private static int numOfTransactions;

    private final Scanner SCANNER;
    private final double WITHDRAW_FEE;

    private Card insertedCard;
    private boolean isCardInserted = false;

    public ATM() {
        SCANNER = new Scanner(System.in);
        WITHDRAW_FEE = 0.25;
        insertedCard = null;
    }

    public static int getNumOfTransactions() {
        return numOfTransactions;
    }

    public static void incrementNumOfTransactions() {
        numOfTransactions++;
    }

    public double getWITHDRAW_FEE() {
        return WITHDRAW_FEE;
    }

    private void setInsertedCard(Card insertedCard) {
        this.insertedCard = insertedCard;
    }

    public boolean insertCard(Card card) {

        if (insertedCard != null) System.out.println("There is a card already inside the ATM.");

        if (card instanceof DebitCard debitCard) {

            System.out.println("Please Enter your PIN: ");
            String input = SCANNER.nextLine();

            if (debitCard.verifyPIN(input)) {
                System.out.println("Identification Verified");
                setInsertedCard(debitCard);

                return true;
            } else {
                System.out.println("Verification Failed.");

                return false;
            }
        }

        setInsertedCard(card);

        return true;
    }

    public void returnCard() {
        setInsertedCard(null);
    }

    public void runATM(Person person) {

        while (!isCardInserted) {
            switch (displayFirstMenu()) {
                case "1" -> {
                    GameGUI.clearConsole();
                    if (insertCard(person.getBankAccount().getDebitCard())) {
                        isCardInserted = true;
                        System.out.println("Card Inserted.");
                    }
                    while (isCardInserted) {
                        whileCardInserted();
                    }
                }
                case "2" -> {
                    System.out.println("Exiting the program.");
                    System.out.println("GoodBye.");
                    //Need to change this later
                    System.exit(0);
                }
                default -> System.out.println("No Option Found");
            }
        }

    }

    private void whileCardInserted() {

        BankAccount bankAccount = ((DebitCard) insertedCard).getBankAccount();

        switch (displayMenu()) {
            case "1" -> {
                System.out.println("Which account do you want to withdraw from?");
                Account withdrawAccount = accountSelection();
                System.out.print("How much do you want to withdraw from the account? (Available $" + withdrawAccount.getBalance() + ")");
                int withdrawAmount = SCANNER.nextInt();
                SCANNER.nextLine();
                if (withdrawAmount > withdrawAccount.getBalance()) {

                    System.out.println("Insufficient Funds.");
                } else if (withdrawAmount % 5 != 0) {

                    System.out.println("The ATM only gives out 5 and 20 dollar bills.");
                } else {
                    int numOfTwenties = withdrawAmount / 20;
                    int numOfFives = (withdrawAmount - (numOfTwenties * 20)) / 5;

                    withdrawAccount.withdrawMoney(withdrawAmount + getWITHDRAW_FEE());
                    ATM.incrementNumOfTransactions();

                    String str = "You have withdrew $" + withdrawAmount + ".";

                    if (numOfTwenties != 0) str += " You have received " + numOfTwenties + " twenty dollar bills";
                    if (numOfFives != 0) str += " and " + numOfFives + " five dollar bills";

                    str += ".";

                    System.out.println(str);

                    String withDrawTransaction = "You have withdrew $" + withdrawAmount + " Current Balance: $" + withdrawAccount.getBalance() + " Transaction ID: #" + ATM.getNumOfTransactions();
                    bankAccount.appendTransaction(withDrawTransaction);
                    System.out.println(withDrawTransaction);
                }
            }
            case "2" -> {
                System.out.println("Select an Account to Deposit Money Into");
                Account depositAccount = accountSelection();
                System.out.print("How much money do you want to deposit? ");
                double amount = SCANNER.nextDouble();
                SCANNER.nextLine();
                depositAccount.depositMoney(amount);
                ATM.incrementNumOfTransactions();
                String depositTransaction = "You have deposited $" + amount + " Current Balance: $" + depositAccount.getBalance() + " Transaction ID: #" + ATM.getNumOfTransactions();
                bankAccount.appendTransaction(depositTransaction);
                System.out.println(depositTransaction);
            }
            case "3" -> {
                System.out.println("Choose an account to transfer money FROM.");
                Account transferAccount = accountSelection();
                System.out.println("Choose an account to transfer money TO");
                Account receiverAccount = accountSelection();
                System.out.print("How much money would you like to transfer? (Available Funds: $" + transferAccount.getBalance() + ")");
                double transferAmount = SCANNER.nextDouble();
                SCANNER.nextLine();
                if (transferAccount.wireTransfer(receiverAccount, transferAmount)) {
                    System.out.println("Wire Transfer Successful.");

                    ATM.incrementNumOfTransactions();
                    String transferTransaction = "You have transferred $" + transferAmount + " Current Balance: $" + transferAccount.getBalance() + " Transaction ID: #" + ATM.getNumOfTransactions();
                    bankAccount.appendTransaction(transferTransaction);
                    System.out.println(transferTransaction);

                } else {
                    System.out.println("Wire Transfer Was NOT Successful. Insufficient funds.");
                }
            }
            case "4" -> displayBalanceMenu(((DebitCard) insertedCard).getChecking().getPerson());
            case "5" -> {
                System.out.print("Please enter your current PIN: ");
                ((DebitCard) insertedCard).changePIN(SCANNER.nextLine());
                ATM.incrementNumOfTransactions();
                String changePINTransaction = "Changed PIN. Transaction ID: #" + ATM.getNumOfTransactions();
                bankAccount.appendTransaction(changePINTransaction);
                System.out.println(changePINTransaction);
            }
            case "6" -> {
                List<String> transactions = bankAccount.getTransactions();
                if (transactions.isEmpty()) {
                    System.out.println("You don't have any transactions made. ");
                } else {
                    System.out.println("Transactions: ");
                    System.out.println("------------------------------");
                    for (String transaction : bankAccount.getTransactions()) {
                        System.out.println(transaction);
                    }
                    System.out.println("------------------------------");
                }
            }
            case "7" -> {
                System.out.println("Returning Card.");
                returnCard();
                isCardInserted = false;
            }
            default -> System.out.println("This is not a valid input!");
        }
    }

    private Account accountSelection() {
        GameGUI.clearConsole();

        System.out.println("1. Checking Account");
        System.out.println("2. Savings Account");

        String input = SCANNER.nextLine();

        return switch (input) {
            case "1" -> ((DebitCard) insertedCard).getBankAccount().getChecking();
            case "2" -> ((DebitCard) insertedCard).getBankAccount().getSavings();
            default -> null;
        };
    }

    private String displayFirstMenu() {
        GameGUI.clearConsole();

        System.out.println("1. Insert Card...");
        System.out.println("2. Exit.");

        return SCANNER.nextLine();
    }

    private String displayMenu() {

        System.out.println("--------------------------");
        System.out.println("1. Withdraw money");
        System.out.println("2. Deposit money");
        System.out.println("3. Transfer money between accounts");
        System.out.println("4. Get account balances");
        System.out.println("5. Change PIN");
        System.out.println("6. Get Transactions");
        System.out.println("7. Return Card");
        System.out.println("--------------------------");

        return SCANNER.nextLine();
    }

    private void displayBalanceMenu(Person person) {
        GameGUI.clearConsole();

        System.out.println("CHECKING BALANCE: $" + person.getBankAccount().getChecking().getBalance());
        System.out.println("SAVINGS BALANCE: $" + person.getBankAccount().getSavings().getBalance());
        System.out.println("Return. (ENTER)");
        SCANNER.nextLine();
    }

}
