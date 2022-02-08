import java.util.Scanner;

public class Program {

    private final Scanner SCANNER = new Scanner(System.in);
    private final double OVERDRAFT_FEE = 35.0;
    private final double MIN_BALANCE = 150.0;
    private final double INTEREST_RATE = 2.0;

    public Program() { }

    public void start() {
        System.out.println("Welcome to the ATM.");
        System.out.print("What is your full name? ");
        String fullName = SCANNER.nextLine();

        GameGUI.clearConsole();

        System.out.print("Age? ");
        int age = SCANNER.nextInt();
        SCANNER.nextLine();

        GameGUI.clearConsole();

        System.out.print("SSN? (Personal Identification) ");
        String id = SCANNER.nextLine();

        GameGUI.clearConsole();

        Person person = new Person(fullName, age, id);

        System.out.println("Perfect.");
        System.out.println("You will now be setting up your two accounts. A Checking and a Savings account.");
        System.out.println("First your Checking Account");

        System.out.print("What is your Bank name that you will be using? ");
        String bankName = SCANNER.nextLine();
        Bank bank = new Bank(bankName);

        BankAccount bankAccount = new BankAccount(bank, person);

        person.setBankAccount(bankAccount);

        GameGUI.clearConsole();

        System.out.print("What will be the starting balance for the checking account? ");
        double checkingStartingBalance = SCANNER.nextDouble();
        SCANNER.nextLine();

        GameGUI.clearConsole();

        Checking checking = new Checking(bank, person, checkingStartingBalance, OVERDRAFT_FEE);
        bankAccount.setChecking(checking);

        System.out.println("Second your Savings Account");

        System.out.print("What will be the starting balance for the savings account? ");
        double savingsStartingBalance = SCANNER.nextDouble();
        SCANNER.nextLine();

        GameGUI.clearConsole();

        Savings savings = new Savings(bank, person, savingsStartingBalance, INTEREST_RATE, MIN_BALANCE, OVERDRAFT_FEE);
        bankAccount.setSavings(savings);

        System.out.println("Now lets set up a Debit Card linked to your checking account. ");

        System.out.print("What will be your PIN (Personal Identification Number) for your Debit Card? ");
        String pIN = SCANNER.nextLine();

        GameGUI.clearConsole();

        DebitCard debitCard = new DebitCard(bankAccount, checking, pIN);
        bankAccount.setDebitCard(debitCard);

        ATM atm = new ATM();
        atm.runATM(person);
    }
}
