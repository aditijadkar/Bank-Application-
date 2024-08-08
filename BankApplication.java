/*Bank Account Bank, Account, Employee, Transactions,

1) Create Account

2) Bank Transacation:

Sub Menu: Credit, debit, checkBalance

Ask for the account number and pin

Validate the pin agint DB

3) Print PassBook :

Ask for the account number and pin.

Validate the pin agint DB and display last 10 transaction

4) Change Pin

Ask for the account number and DOB.

Validate and change the PIN

5) Exit */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private int accountNumber;
    private String accountHolderName;
    private String dob; // Date of birth for authentication
    private int pin;
    private double balance;
    private List<Transaction> transactions;

    public Account(int accountNumber, String accountHolderName, String dob, int pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.dob = dob;
        this.pin = pin;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getDob() {
        return dob;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int newPin) {
        this.pin = newPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Credit", amount, balance));
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Debit", amount, balance));
            System.out.println("Amount withdrawn successfully.");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public void printPassbook() {
        System.out.println("Printing the last 10 transactions for account number " + accountNumber);
        int size = transactions.size();
        for (int i = Math.max(size - 10, 0); i < size; i++) {
            transactions.get(i).printTransaction();
        }
    }
}

class Transaction {
    private String type; // Credit or Debit
    private double amount;
    private double balanceAfterTransaction;

    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public void printTransaction() {
        System.out.println(type + " | Amount: $" + amount + " | Balance After Transaction: $" + balanceAfterTransaction);
    }
}

class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public Account createAccount(int accountNumber, String accountHolderName, String dob, int pin) {
        Account account = new Account(accountNumber, accountHolderName, dob, pin);
        accounts.add(account);
        System.out.println("Account created successfully.");
        return account;
    }

    public Account getAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public void changePin(int accountNumber, String dob, int newPin) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            if (account.getDob().equals(dob)) {
                account.setPin(newPin);
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("DOB validation failed. PIN change unsuccessful.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
}

public class BankApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Bank Transaction");
            System.out.println("3. Print Passbook");
            System.out.println("4. Change PIN");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Enter account holder name: ");
                    String accountHolderName = scanner.nextLine();

                    System.out.print("Enter date of birth (DOB): ");
                    String dob = scanner.nextLine();

                    System.out.print("Enter PIN: ");
                    int pin = scanner.nextInt();

                    bank.createAccount(accountNumber, accountHolderName, dob, pin);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    int accountNumberForTransaction = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    int pinForTransaction = scanner.nextInt();

                    Account accountForTransaction = bank.getAccount(accountNumberForTransaction);
                    if (accountForTransaction != null && accountForTransaction.getPin() == pinForTransaction) {
                        System.out.println("\nTransaction Sub-Menu:");
                        System.out.println("1. Credit");
                        System.out.println("2. Debit");
                        System.out.println("3. Check Balance");
                        System.out.print("Choose a transaction option: ");
                        int transactionChoice = scanner.nextInt();

                        switch (transactionChoice) {
                            case 1:
                                System.out.print("Enter amount to credit: ");
                                double amountToCredit = scanner.nextDouble();
                                accountForTransaction.deposit(amountToCredit);
                                break;

                            case 2:
                                System.out.print("Enter amount to debit: ");
                                double amountToDebit = scanner.nextDouble();
                                accountForTransaction.withdraw(amountToDebit);
                                break;

                            case 3:
                                accountForTransaction.checkBalance();
                                break;

                            default:
                                System.out.println("Invalid transaction choice.");
                                break;
                        }
                    } else {
                        System.out.println("Account number or PIN validation failed.");
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    int accountNumberForPassbook = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    int pinForPassbook = scanner.nextInt();

                    Account accountForPassbook = bank.getAccount(accountNumberForPassbook);
                    if (accountForPassbook != null && accountForPassbook.getPin() == pinForPassbook) {
                        accountForPassbook.printPassbook();
                    } else {
                        System.out.println("Account number or PIN validation failed.");
                    }
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    int accountNumberForPinChange = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Enter date of birth (DOB): ");
                    String dobForPinChange = scanner.nextLine();

                    System.out.print("Enter new PIN: ");
                    int newPin = scanner.nextInt();

                    bank.changePin(accountNumberForPinChange, dobForPinChange, newPin);
                    break;

                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }
    }
}