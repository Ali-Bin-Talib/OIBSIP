import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String userId;
    private String userPin;
    private double balance;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
    }

    public boolean authenticate(String id, String pin) {
        return this.userId.equals(id) && this.userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            return true;
        }
        return false;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

class ATM {
    private Account account;
    private ArrayList<Transaction> history;
    private Scanner scanner;

    public ATM(Account account) {
        this.account = account;
        this.history = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to the ATM Interface ===");
        int attempts = 3;
        boolean loggedIn = false;

        while (attempts > 0) {
            System.out.print("Enter User ID: ");
            String id = scanner.next();
            System.out.print("Enter PIN: ");
            String pin = scanner.next();

            if (account.authenticate(id, pin)) {
                loggedIn = true;
                System.out.println("Login Successful!\n");
                break;
            } else {
                attempts--;
                System.out.println("Invalid credentials. Attempts left: " + attempts);
            }
        }

        if (!loggedIn) {
            System.out.println("Access Denied. Too many incorrect attempts.");
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ATM Main Menu ---");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    performWithdrawal();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
        System.out.println("Current Balance: $" + account.getBalance());
    }

    private void performWithdrawal() {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        if (account.withdraw(amount)) {
            history.add(new Transaction("Withdrawal", amount));
            System.out.println("Successfully withdrew $" + amount);
        } else {
            System.out.println("Insufficient Funds or invalid amount!");
        }
    }

    private void performDeposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            account.deposit(amount);
            history.add(new Transaction("Deposit", amount));
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    private void performTransfer() {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.next();
        Account recipient = new Account(recipientId, "0000", 0.0);
        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();

        if (account.transfer(recipient, amount)) {
            history.add(new Transaction("Transfer to " + recipientId, amount));
            System.out.println("Successfully transferred $" + amount);
        } else {
            System.out.println("Insufficient Funds or invalid amount!");
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        // Default credentials: User ID = user123, PIN = 1234, Initial Balance = $1000.00
        Account userAccount = new Account("user123", "1234", 1000.00);
        ATM atm = new ATM(userAccount);
        atm.start();
    }
}