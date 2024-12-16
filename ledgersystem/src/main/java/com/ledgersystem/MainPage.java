package com.ledgersystem;
import java.util.Scanner;
import java.io.*;

public class MainPage {
    public static void showMainPage(String username, String userId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== Welcome, " + username + " ==");
        double balance = getCurrentBalance(userId);
        System.out.println("Balance: " + balance);
        System.out.println("Savings:");
        System.out.println("Loan:");

        while (true) {
            System.out.println("== Transactions ==");
            System.out.println("1. Debit");
            System.out.println("2. Credit");
            System.out.println("3. History");
            System.out.println("4. Savings");
            System.out.println("5. Credit Loan");
            System.out.println("6. Deposit Interest Predictor");
            System.out.println("7. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Debit.handleDebit(scanner, userId);
                    break;
                case 2:
                    Credit.handleCredit(scanner, userId);
                    break;
                case 3:
                    History.showHistory(scanner, userId);
                    break;
                case 4:
                    // Handle Savings
                    System.out.println("Savings selected");
                    break;
                case 5:
                    // Handle Credit Loan
                    System.out.println("Credit Loan selected");
                    break;
                case 6:
                    // Handle Deposit Interest Predictor
                    System.out.println("Deposit Interest Predictor selected");
                    break;
                case 7:
                    // Handle Logout
                    System.out.println("Thank you for using Ledger System,"+ username + "!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static double getCurrentBalance(String userId) {
        double balance = 0.0;
        try (BufferedReader br = new BufferedReader(new FileReader("ledgersystem\\src\\main\\resources\\Balance.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(userId)) {
                    balance = Double.parseDouble(values[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the balance data: " + e.getMessage());
        }
        return balance;
    }
}