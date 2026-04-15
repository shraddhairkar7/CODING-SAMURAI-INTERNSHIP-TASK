import java.util.Scanner;

class ATM {

    private double balance;
    private final int PIN = 1234; // fixed PIN
    private int[] transactions = new int[20];
    private int index = 0;

    // Constructor
    public ATM(double initialBalance) {
        balance = initialBalance;
    }

    // PIN validation
    public boolean validatePIN(int inputPin) {
        return inputPin == PIN;
    }

    // Check balance
    public void checkBalance() {
        System.out.println("Available Balance: Rs." + balance);
    }

    // Deposit with validation
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount! Enter positive value.");
            return;
        }

        if (amount > 50000) {
            System.out.println("Deposit limit exceeded! Max Rs.50,000 allowed.");
            return;
        }

        balance += amount;
        if (index < transactions.length)
            transactions[index++] = (int) amount;

        System.out.println("Rs." + amount + " deposited successfully.");
    }

    // Withdraw with validation
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount % 100 != 0) {
            System.out.println("Amount must be in multiples of ₹100.");
            return;
        }

        if (amount > 20000) {
            System.out.println("Withdrawal limit is Rs.20,000 per transaction.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= amount;
        if (index < transactions.length)
            transactions[index++] = -(int) amount;

        System.out.println("Rs." + amount + " withdrawn successfully.");
    }

    // Transaction history
    public void showTransactions() {
        if (index == 0) {
            System.out.println("No transactions yet.");
            return;
        }

        System.out.println("---- Transaction History ----");
        for (int i = 0; i < index; i++) {
            if (transactions[i] > 0)
                System.out.println("Deposited: Rs." + transactions[i]);
            else
                System.out.println("Withdrawn: Rs." + (-transactions[i]));
        }
    }
}

public class ATMSystem {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ATM user = new ATM(10000);

        int attempts = 0;
        boolean authenticated = false;

        // PIN Authentication (3 attempts)
        while (attempts < 3) {
            System.out.print("Enter your 4-digit PIN: ");
            int pin = sc.nextInt();

            if (user.validatePIN(pin)) {
                authenticated = true;
                break;
            } else {
                attempts++;
                System.out.println("Incorrect PIN! Attempts left: " + (3 - attempts));
            }
        }

        if (!authenticated) {
            System.out.println("Card blocked due to multiple wrong attempts.");
            sc.close();
            return;
        }

        int choice;

        // Main ATM loop
        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Balance Inquiry");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");

            System.out.print("Choose option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    user.checkBalance();
                    break;

                case 2:
                    System.out.print("Enter deposit amount: Rs.");
                    double dep = sc.nextDouble();
                    user.deposit(dep);
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: Rs.");
                    double wit = sc.nextDouble();

                    // confirmation step
                    System.out.print("Confirm withdrawal (yes/no): ");
                    String confirm = sc.next();

                    if (confirm.equalsIgnoreCase("yes")) {
                        user.withdraw(wit);
                    } else {
                        System.out.println("Transaction cancelled.");
                    }
                    break;

                case 4:
                    user.showTransactions();
                    break;

                case 5:
                    System.out.println("Thank you for using ATM.");
                    break;

                default:
                    System.out.println("Invalid option!");
            }

        } while (choice != 5);

        sc.close();
    }
}